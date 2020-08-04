package yte.etkinlikyonetim.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.etkinlikyonetim.BasvuruPK;
import yte.etkinlikyonetim.entity.*;
import yte.etkinlikyonetim.repository.*;
import yte.etkinlikyonetim.security.PasswordConfig;

import javax.mail.*;
import javax.mail.internet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManageEtkinlikService {
    private final EtkinlikRepository etkinlikRepository;
    private final AdresRepository adresRepository;
    private final KatilimciRepository katilimciRepository;
    private final BasvuruRepository basvuruRepository;
    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;

    public List<Etkinlik> listAllEtkinlik(){
        return etkinlikRepository.findAll();
    }

    public List<Etkinlik> listAllEtkinlikToMisafir(){
        return etkinlikRepository.findByBaslangicTarihiAfter(LocalDate.now());
    }

    public boolean addEtkinlik(Etkinlik etkinlik, Long konum_id){
        if(!etkinlikRepository.existsByIsim(etkinlik.getIsim())){
            Optional<Adres> adres = adresRepository.findById(konum_id);
            etkinlik.setAdres(adres.get());
            etkinlikRepository.save(etkinlik);
            return true;
        }
        return false;
    }

    public boolean deleteEtkinlik(Long id){
        if(etkinlikRepository.existsById(id)){
            basvuruRepository.deleteAllByEtkinlikid(id);
            etkinlikRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateEtkinlik(Etkinlik oldEtkinlik, Etkinlik newEtkinlik){
        Optional<Etkinlik> etkinlikOpt = etkinlikRepository.findById(oldEtkinlik.getId());
        if(etkinlikOpt.isEmpty())
            return false;
        Etkinlik etkinlik = etkinlikOpt.get();
        etkinlik.setIsim(newEtkinlik.getIsim());
        etkinlik.setBaslangicTarihi(newEtkinlik.getBaslangicTarihi());
        etkinlik.setBitisTarihi(newEtkinlik.getBitisTarihi());
        etkinlik.setEtkinlikTipi(newEtkinlik.getEtkinlikTipi());
        etkinlik.setAdres(newEtkinlik.getAdres());
        etkinlik.setKontenjan(newEtkinlik.getKontenjan());
        etkinlikRepository.save(etkinlik);
        return true;
    }

    public String getAdresUrl(Long id){
        Optional<Etkinlik> etkinlik = etkinlikRepository.findById(id);
        if(etkinlik.isEmpty())
            return null;
        return etkinlik.get().getAdres().getEnlem() + "," + etkinlik.get().getAdres().getBoylam();
    }

    public Etkinlik getEtkinlik(Long id){
        return etkinlikRepository.findById(id).get();
    }

    public List<Adres> listAllAdres(){
        return adresRepository.findAll();
    }

    public Adres getAdres(Long id){
        Optional<Adres> adres = adresRepository.findById(id);
        return adres.orElse(null);
    }

    public String etkinlikKaydol(Katilimci katilimci, Long id) throws Exception{
        Optional<Etkinlik> etkinlikOpt = etkinlikRepository.findById(id);
        if(etkinlikOpt.isPresent()){
            Etkinlik etkinlik = etkinlikOpt.get();
            if(etkinlik.yerVar()){
                if(!katilimciRepository.existsByTcKimlikNo(katilimci.getTcKimlikNo())) { //yokmus yenisini olustur
                    katilimci.setBasvurular(new HashSet<>());
                    katilimciRepository.save(katilimci);
                }
                katilimci.setId(katilimciRepository.findByTcKimlikNo(katilimci.getTcKimlikNo()).get().getId());
                BasvuruPK basvuruPK = new BasvuruPK(id, katilimci.getId());
                if(basvuruRepository.findById(basvuruPK).isEmpty()){
                    Basvuru basvuru = new Basvuru(basvuruPK, etkinlik, katilimci, LocalDate.now());
                    etkinlik.addBasvuru();
                    basvuruRepository.save(basvuru);
                    qrCodeGenerator(etkinlik, katilimci);
                    return "Kaydınız başarıyla tamamlanmıştır. Detaylar için e posta adresinizi kontrol ediniz.";
                }
                else
                    return "Bu etkinliğe daha önce başvurdunuz.";
            }
            else
                return "Etkinlikte yer kalmadı.";
        }
        else
            return "Etkinlik mevcut değil.";
    }

    public void qrCodeGenerator(Etkinlik etkinlik, Katilimci katilimci) throws Exception{
        String qrpath = "C:\\etkinlik-yonetim\\src\\main\\QRcodes\\";
        String name = qrpath + "QR" + etkinlik.getId() + "-" + katilimci.getId() + ".png";
        String content = "Etkinlik: \nIsim: " + etkinlik.getIsim() +
                        "\nBaslangiç Tarihi: " + etkinlik.getBaslangicTarihi() +
                        "\nBitis Tarihi: " + etkinlik.getBitisTarihi() +
                        "\nAdres: " + etkinlik.getAdres().getFullAdres() +
                        "\nKatilimci: \nAd: " + katilimci.getAd() +
                        "\nSoyad: " + katilimci.getSoyad();
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 200,200);
        Path path = FileSystems.getDefault().getPath(name);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        sendmail(name, katilimci.getEmail());
    }

    private void sendmail(String qrPath, String hedefAdres) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zanilenurrabia@gmail.com", "-*098mhmdrabia");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("zanilenurrabia@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(hedefAdres));
        msg.setSubject("Tübitak Etkinlik Kayıt Detaylar");
        msg.setContent("Tübitak etkinlik kaydınız başarıyla tamamlanmıştır. Detaylar için QR kodu okutunuz.", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Etkinlik kaydiniz basariyla tamamlanmistir. Detaylar icin QR kodu okutunuz.", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(qrPath);
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }

    public boolean auth(User user) {
        return userRepository.existsByUsernameAndPassword(user.getUsername(), passwordConfig.passwordEncoder().encode(user.getPassword()));

    }

    public String getQRpath(Katilimci katilimci, Long id) {
        return "QR"+ id + "-" + katilimciRepository.findIdByTcKimlikNo(katilimci.getTcKimlikNo());
    }

    public List<Katilimci> listAllKatilimci(Long id) {
        List<Basvuru> basvurular = basvuruRepository.findByEtkinlikId(id);
        List<Katilimci> katilimcilar = new ArrayList<>();
        for (Basvuru basvuru: basvurular) {
            katilimcilar.add(basvuru.getKatilimci());
        }
        return katilimcilar;
    }
}

