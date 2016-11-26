package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.entity.Forest;
import fr.xebia.extras.selma.Selma;
import org.springframework.stereotype.Service;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Service
public class ForestMapperService extends EntityDTOServiceImpl<Forest, ForestDTO> {
    ForestMapper mapper = Selma.builder(ForestMapper.class).build();

    @SuppressWarnings("unchecked")
    @Override
    public ForestMapper getMapper() {
        return mapper;
    }
}
