package cz.fi.muni.pa165.service.mappers;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
interface EntityDTOMapper<Entity, DTO> {

    DTO asDto(Entity entity);

    Entity asEntity(DTO dto);
}
