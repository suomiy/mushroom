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
    HunterMapper mapper = Selma.builder(HunterMapper.class).build();

    @Inject
    private VisitMapperService visitMapperService;

    @SuppressWarnings("unchecked")
    @Override
    public HunterMapper getMapper() {
        return mapper;
    }

    @Override
    public HunterDTO asDto(Hunter hunter) {
        HunterDTO result = mapper.asDto(hunter);
        if (result != null) {
            result.setVisits(visitMapperService.asDtos(hunter.getVisits()));
        }

        return result;
    }

    @Override
    public Hunter asEntity(HunterDTO hunterDTO) {
        Hunter result = mapper.asEntity(hunterDTO);
        if (result != null) {
            result.setVisits(visitMapperService.asEntities(hunterDTO.getVisits()));
        }

        return result;
    }
}
