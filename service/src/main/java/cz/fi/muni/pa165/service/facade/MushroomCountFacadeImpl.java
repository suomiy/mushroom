package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.facade.MushroomCountFacade;
import cz.fi.muni.pa165.service.BeanMapperService;
import cz.fi.muni.pa165.service.MushroomCountService;
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
    BeanMapperService beanMapperService;

    @Override
    public void create(MushroomCountDTO mushroomCount) {
        mushroomCountService.create(beanMapperService.mapTo(mushroomCount, MushroomCount.class));
    }

    @Override
    public MushroomCountDTO update(MushroomCountDTO mushroomCount) {
        MushroomCount updated = mushroomCountService.update(beanMapperService.mapTo(mushroomCount, MushroomCount.class));
        return beanMapperService.mapTo(updated, MushroomCountDTO.class);
    }

    @Override
    public void delete(Long id) {
        MushroomCount entity = beanMapperService.mapTo(mushroomCountService.findById(id), MushroomCount.class);
        mushroomCountService.delete(entity);
    }

    @Override
    public MushroomCountDTO findById(Long id) {
        MushroomCount entity = beanMapperService.mapTo(mushroomCountService.findById(id), MushroomCount.class);
        return beanMapperService.mapTo(entity, MushroomCountDTO.class);
    }

    @Override
    public List<MushroomCountDTO> findAll() {
        return beanMapperService.mapTo(mushroomCountService.findAll(), MushroomCountDTO.class);
    }

    public List<MushroomCountDTO> findRecentlyFoundPickableMushrooms() {
        return beanMapperService.mapTo(mushroomCountService.findRecentlyFoundPickableMushrooms(), MushroomCountDTO.class);
    }
}
