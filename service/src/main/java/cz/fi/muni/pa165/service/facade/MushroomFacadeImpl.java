package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.DateIntervalDTO;
import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.facade.MushroomFacade;
import cz.fi.muni.pa165.service.BeanMapperService;
import cz.fi.muni.pa165.service.MushroomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Michal Kysilko 436339 on 11/23/16.
 *
 * @author Michal Kysilko 436339
 */
@Service
@Transactional
public class MushroomFacadeImpl implements MushroomFacade {

    @Inject
    private MushroomService mushroomService;

    @Inject
    private BeanMapperService beanMapperService;

    @Override
    public void create(MushroomDTO mushroom) {
        mushroomService.create(beanMapperService.mapTo(mushroom, Mushroom.class));
    }

    @Override
    public MushroomDTO update(MushroomDTO mushroom) {
        Mushroom m = mushroomService.update(beanMapperService.mapTo(mushroom, Mushroom.class));
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }

    @Override
    public MushroomDTO findById(Long id) {
        Mushroom m = mushroomService.findById(id);
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }

    @Override
    public void delete(Long id) {
        mushroomService.delete(mushroomService.findById(id));
    }

    @Override
    public List<MushroomDTO> findAll() {
        List<Mushroom> m = mushroomService.findAll();
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }

    @Override
    public MushroomDTO findByName(String name) {
        Mushroom m = mushroomService.findByName(name);
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }

    @Override
    public List<MushroomDTO> findByType(MushroomType type) {
        List<Mushroom> m = mushroomService.findByType(type);
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }

    @Override
    public List<MushroomDTO> findByDate(DateDTO date) {
        List<Mushroom> m = mushroomService.findByDate(date.getDate());
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }

    @Override
    public List<MushroomDTO> findByDate(DateIntervalDTO interval) {
        List<Mushroom> m = mushroomService.findByDate(interval.getFrom(), interval.getTo());
        return beanMapperService.mapTo(m, MushroomDTO.class);
    }
}
