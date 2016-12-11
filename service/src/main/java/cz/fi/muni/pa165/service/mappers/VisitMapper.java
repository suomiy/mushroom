package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Visit;
import fr.xebia.extras.selma.Mapper;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Mapper(withIgnoreFields = {"mushroomsCount", "hunter", "hunterId", "forest", "forestId"})
interface VisitMapper extends EntityDTOMapper<Visit, VisitDTO> {
}
