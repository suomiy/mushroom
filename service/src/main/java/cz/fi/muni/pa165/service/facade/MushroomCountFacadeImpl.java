package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.facade.MushroomCountFacade;
import cz.fi.muni.pa165.service.MushroomCountService;
import cz.fi.muni.pa165.service.MushroomService;
import cz.fi.muni.pa165.service.VisitService;
import cz.fi.muni.pa165.service.mappers.MushroomCountMapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/25/16
 */
@Service
@Transactional
public class MushroomCountFacadeImpl implements MushroomCountFacade {

    @Inject
    private MushroomCountService mushroomCountService;

    @Inject
    private MushroomService mushroomService;

    @Inject
    private VisitService visitService;

    @Inject
    private MushroomCountMapperService mapperService;

    @Override
    public void create(MushroomCountDTO mushroomCount) {
        MushroomCount count = mapperService.asEntity(mushroomCount);
        count.setMushroom(mushroomService.findById(mushroomCount.getMushroomId()));
        count.setVisit(visitService.findById(mushroomCount.getVisitId()));

        mushroomCountService.create(count);
        mushroomCount.setId(count.getId());
    }

    @Override
    public MushroomCountDTO update(MushroomCountDTO mushroomCount) {
        MushroomCount count = mapperService.asEntity(mushroomCount);
        count.setMushroom(mushroomService.findById(mushroomCount.getMushroomId()));
        count.setVisit(visitService.findById(mushroomCount.getVisitId()));

        return mapperService.asDto(mushroomCountService.update(count));
    }

    @Override
    public void delete(Long id) {
        MushroomCount entity = mushroomCountService.findById(id);
        mushroomCountService.delete(entity);
    }

    @Override
    public MushroomCountDTO findById(Long id) {
        MushroomCount entity = mushroomCountService.findById(id);
        return mapperService.asDto(entity);
    }

    @Override
    public List<MushroomCountDTO> findAll() {
        return mapperService.asDtos(mushroomCountService.findAll());
    }

    public List<MushroomCountDTO> findRecentlyFoundPickableMushrooms() {
        return mapperService.asDtos(mushroomCountService.findRecentlyFoundPickableMushrooms());
    }
}
