package cz.fi.muni.pa165.service.mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
abstract class EntityDTOServiceImpl<Entity, DTO> implements EntityDTOService<Entity, DTO> {

    public abstract <Mapper extends EntityDTOMapper<Entity, DTO>> Mapper getMapper();

    public DTO asDto(Entity entity) {
        return entity == null ? null : getMapper().asDto(entity);
    }

    public Entity asEntity(DTO dto) {
        return dto == null ? null : getMapper().asEntity(dto);
    }

    public List<DTO> asDtos(Collection<Entity> entities) {
        return entities == null ? Collections.emptyList() : entities.stream().map(this::asDto).collect(Collectors.toList());
    }

    public List<Entity> asEntities(Collection<DTO> dtos) {
        return dtos == null ? Collections.emptyList() : dtos.stream().map(this::asEntity).collect(Collectors.toList());
    }
}
