package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
import cz.fi.muni.pa165.entity.Hunter;
import fr.xebia.extras.selma.Selma;
import org.springframework.stereotype.Service;

/**
 * Created by Erik Macej 433744 , on 10.12.16.
 *
 * @author Erik Macej 433744
 */

@Service
public class RegistrateHunterMapperService extends EntityDTOServiceImpl<Hunter, RegistrateHunterDTO> {

    private RegistrateHunterMapper mapper = Selma.builder(RegistrateHunterMapper.class).build();

    @SuppressWarnings("unchecked")
    @Override
    public RegistrateHunterMapper getMapper() {
        return mapper;
    }

}
