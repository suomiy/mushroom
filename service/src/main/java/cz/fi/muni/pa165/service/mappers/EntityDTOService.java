package cz.fi.muni.pa165.service.mappers;

import java.util.Collection;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
public interface EntityDTOService<Entity, DTO> extends EntityDTOMapper<Entity, DTO> {

    <Mapper extends EntityDTOMapper<Entity, DTO>> Mapper getMapper();

    DTO asDto(Entity entity);

    Entity asEntity(DTO dto);

    List<DTO> asDtos(Collection<Entity> entities);

    List<Entity> asEntities(Collection<DTO> dtos);
}
