package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
import cz.fi.muni.pa165.entity.Hunter;
import fr.xebia.extras.selma.Mapper;

/**
 * Created by Erik Macej 433744 , on 10.12.16.
 *
 * @author Erik Macej 433744
 */
@Mapper(withIgnoreFields = {"passwordHash", "visits","unencryptedPassword","id"})
public interface RegistrateHunterMapper extends EntityDTOMapper<Hunter, RegistrateHunterDTO> {
    
}
