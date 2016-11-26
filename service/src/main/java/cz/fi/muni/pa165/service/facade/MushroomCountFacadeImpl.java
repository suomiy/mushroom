package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.facade.MushroomCountFacade;
import cz.fi.muni.pa165.service.MushroomCountService;
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
    MushroomCountService mushroomCountService;

    @Inject
    MushroomCountMapperService mapperService;

    @Override
    public void create(MushroomCountDTO mushroomCount) {
        mushroomCountService.create(mapperService.asEntity(mushroomCount));
    }

    @Override
    public MushroomCountDTO update(MushroomCountDTO mushroomCount) {
        MushroomCount updated = mushroomCountService.update(mapperService.asEntity(mushroomCount));
        return mapperService.asDto(updated);
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
