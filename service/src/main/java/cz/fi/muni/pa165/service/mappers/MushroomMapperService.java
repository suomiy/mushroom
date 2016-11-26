package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import fr.xebia.extras.selma.Selma;
import org.springframework.stereotype.Service;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Service
public class MushroomMapperService extends EntityDTOServiceImpl<Mushroom, MushroomDTO> {

    private MushroomMapper mapper = Selma.builder(MushroomMapper.class).build();

    @SuppressWarnings("unchecked")
    @Override
    public MushroomMapper getMapper() {
        return mapper;
    }
}
