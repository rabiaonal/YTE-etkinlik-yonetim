package yte.etkinlikyonetim.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import yte.etkinlikyonetim.dto.EtkinlikDTO;
import yte.etkinlikyonetim.entity.Etkinlik;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-08-02T14:04:00+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class EtkinlikMapperImpl implements EtkinlikMapper {

    @Override
    public EtkinlikDTO mapToDto(Etkinlik etkinlik) {
        if ( etkinlik == null ) {
            return null;
        }

        EtkinlikDTO etkinlikDTO = new EtkinlikDTO();

        etkinlikDTO.setIsim( etkinlik.getIsim() );
        etkinlikDTO.setBaslangicTarihi( etkinlik.getBaslangicTarihi() );
        etkinlikDTO.setBitisTarihi( etkinlik.getBitisTarihi() );
        etkinlikDTO.setEklenmeTarihi( etkinlik.getEklenmeTarihi() );
        etkinlikDTO.setEtkinlikTipi( etkinlik.getEtkinlikTipi() );
        etkinlikDTO.setKontenjan( etkinlik.getKontenjan() );

        return etkinlikDTO;
    }

    @Override
    public List<EtkinlikDTO> mapToDto(List<Etkinlik> etkinlikList) {
        if ( etkinlikList == null ) {
            return null;
        }

        List<EtkinlikDTO> list = new ArrayList<EtkinlikDTO>( etkinlikList.size() );
        for ( Etkinlik etkinlik : etkinlikList ) {
            list.add( mapToDto( etkinlik ) );
        }

        return list;
    }

    @Override
    public Etkinlik mapToEntity(EtkinlikDTO etkinlikDto) {
        if ( etkinlikDto == null ) {
            return null;
        }

        Etkinlik etkinlik = new Etkinlik();

        etkinlik.setIsim( etkinlikDto.getIsim() );
        etkinlik.setBaslangicTarihi( etkinlikDto.getBaslangicTarihi() );
        etkinlik.setBitisTarihi( etkinlikDto.getBitisTarihi() );
        etkinlik.setEklenmeTarihi( etkinlikDto.getEklenmeTarihi() );
        etkinlik.setEtkinlikTipi( etkinlikDto.getEtkinlikTipi() );
        etkinlik.setKontenjan( etkinlikDto.getKontenjan() );

        return etkinlik;
    }

    @Override
    public List<Etkinlik> mapToEntity(List<EtkinlikDTO> etkinlikDTOList) {
        if ( etkinlikDTOList == null ) {
            return null;
        }

        List<Etkinlik> list = new ArrayList<Etkinlik>( etkinlikDTOList.size() );
        for ( EtkinlikDTO etkinlikDTO : etkinlikDTOList ) {
            list.add( mapToEntity( etkinlikDTO ) );
        }

        return list;
    }
}
