package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.entity.Hunter;
import fr.xebia.extras.selma.Mapper;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Mapper(withIgnoreFields = {"passwordHash", "visits"})
interface HunterMapper extends EntityDTOMapper<Hunter, HunterDTO> {
}
