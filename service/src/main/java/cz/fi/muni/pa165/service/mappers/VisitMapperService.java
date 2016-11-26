package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Visit;
import fr.xebia.extras.selma.Selma;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.TreeSet;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Service
public class VisitMapperService extends EntityDTOServiceImpl<Visit, VisitDTO> {
    private VisitMapper mapper = Selma.builder(VisitMapper.class).build();

    @Inject
    private MushroomCountMapperService mushroomCountMapperService;

    @SuppressWarnings("unchecked")
    @Override
    public VisitMapper getMapper() {
        return mapper;
    }

    @Override
    public VisitDTO asDto(Visit visit) {
        VisitDTO result = mapper.asDto(visit);
        if (result != null) {
            if (visit.getHunter() != null) {
                result.setHunterId(visit.getHunter().getId());
            }
            result.setMushroomsCount(mushroomCountMapperService.asDtos(visit.getMushroomsCount()));
        }

        return result;
    }

    @Override
    public Visit asEntity(VisitDTO visitDTO) {
        Visit result = mapper.asEntity(visitDTO);
        if (result != null) {
            if (visitDTO.getHunterId() != null) {
                Hunter hunter = new Hunter();
                hunter.setId(visitDTO.getHunterId());
                result.setHunter(hunter);
            }
            result.setMushroomsCount(new TreeSet<>(
                    mushroomCountMapperService.asEntities(visitDTO.getMushroomsCount())));
        }

        return result;
    }
}
