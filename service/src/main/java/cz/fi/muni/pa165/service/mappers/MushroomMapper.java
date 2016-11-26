package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import fr.xebia.extras.selma.Mapper;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Mapper
interface MushroomMapper extends EntityDTOMapper<Mushroom, MushroomDTO> {
}
