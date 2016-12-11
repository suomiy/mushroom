package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.entity.Hunter;
import fr.xebia.extras.selma.Selma;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Service
public class HunterMapperService extends EntityDTOServiceImpl<Hunter, HunterDTO> {
    private HunterMapper mapper = Selma.builder(HunterMapper.class).build();

    @SuppressWarnings("unchecked")
    @Override
    public HunterMapper getMapper() {
        return mapper;
    }

}
