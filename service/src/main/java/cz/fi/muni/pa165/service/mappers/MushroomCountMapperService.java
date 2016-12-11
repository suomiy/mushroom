package cz.fi.muni.pa165.service.mappers;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.entity.Visit;
import fr.xebia.extras.selma.Selma;
import org.springframework.stereotype.Service;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Service
public class MushroomCountMapperService extends EntityDTOServiceImpl<MushroomCount, MushroomCountDTO> {
    private MushroomCountMapper mapper = Selma.builder(MushroomCountMapper.class).build();

    @SuppressWarnings("unchecked")
    @Override
    public MushroomCountMapper getMapper() {
        return mapper;
    }

    @Override
    public MushroomCountDTO asDto(MushroomCount mushroomCount) {
        MushroomCountDTO result = mapper.asDto(mushroomCount);
        if (result != null) {
            if (mushroomCount.getVisit() != null) {
                result.setVisitId(mushroomCount.getVisit().getId());
            }
            if (mushroomCount.getMushroom() != null) {
                result.setMushroomId(mushroomCount.getMushroom().getId());
            }
        }

        return result;
    }

    @Override
    public MushroomCount asEntity(MushroomCountDTO mushroomCountDTO) {
        MushroomCount result = mapper.asEntity(mushroomCountDTO);
        if (result != null) {
            if (mushroomCountDTO.getVisitId() != null) {
                Visit visit = new Visit();
                visit.setId(mushroomCountDTO.getVisitId());
                result.setVisit(visit);
            }
            if (mushroomCountDTO.getMushroomId() != null) {
                Mushroom mushroom = new Mushroom();
                mushroom.setId(mushroomCountDTO.getMushroomId());
                result.setMushroom(mushroom);
            }
        }

        return result;
    }
}
