package yte.etkinlikyonetim.mapper;

import org.mapstruct.Mapper;
import yte.etkinlikyonetim.dto.EtkinlikDTO;
import yte.etkinlikyonetim.entity.Etkinlik;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EtkinlikMapper {
    EtkinlikDTO mapToDto(Etkinlik etkinlik);

    List<EtkinlikDTO> mapToDto(List<Etkinlik> etkinlikList);

    Etkinlik mapToEntity(EtkinlikDTO etkinlikDto);

    List<Etkinlik> mapToEntity(List<EtkinlikDTO> etkinlikDTOList);
}
