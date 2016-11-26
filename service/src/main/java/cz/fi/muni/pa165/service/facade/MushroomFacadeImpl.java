package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.DateIntervalDTO;
import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.facade.MushroomFacade;
import cz.fi.muni.pa165.service.MushroomService;
import cz.fi.muni.pa165.service.mappers.MushroomMapperService;
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
    private MushroomMapperService mapperService;

    @Override
    public void create(MushroomDTO mushroom) {
        mushroomService.create(mapperService.asEntity(mushroom));
    }

    @Override
    public MushroomDTO update(MushroomDTO mushroom) {
        Mushroom m = mushroomService.update(mapperService.asEntity(mushroom));
        return mapperService.asDto(m);
    }

    @Override
    public MushroomDTO findById(Long id) {
        Mushroom m = mushroomService.findById(id);
        return mapperService.asDto(m);
    }

    @Override
    public void delete(Long id) {
        mushroomService.delete(mushroomService.findById(id));
    }

    @Override
    public List<MushroomDTO> findAll() {
        List<Mushroom> m = mushroomService.findAll();
        return mapperService.asDtos(m);
    }

    @Override
    public MushroomDTO findByName(String name) {
        Mushroom m = mushroomService.findByName(name);
        return mapperService.asDto(m);
    }

    @Override
    public List<MushroomDTO> findByType(MushroomType type) {
        List<Mushroom> m = mushroomService.findByType(type);
        return mapperService.asDtos(m);
    }

    @Override
    public List<MushroomDTO> findByDate(DateDTO date) {
        List<Mushroom> m = mushroomService.findByDate(date.getDate());
        return mapperService.asDtos(m);
    }

    @Override
    public List<MushroomDTO> findByDate(DateIntervalDTO interval) {
        List<Mushroom> m = mushroomService.findByDate(interval.getFrom(), interval.getTo());
        return mapperService.asDtos(m);
    }
}
