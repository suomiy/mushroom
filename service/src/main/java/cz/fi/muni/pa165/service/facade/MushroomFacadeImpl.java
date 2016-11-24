package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.facade.MushroomFacade;
import cz.fi.muni.pa165.service.BeanMapperService;
import cz.fi.muni.pa165.service.MushroomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
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
        mushroomService.create(beanMapperService.mapTo(mushroom,Mushroom.class));
    }

    @Override
    public MushroomDTO update(MushroomDTO mushroom) {
        MushroomDTO result = null;
        Mushroom m = mushroomService.update(beanMapperService.mapTo(mushroom, Mushroom.class));

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

    @Override
    public MushroomDTO findById(Long id) {
        MushroomDTO result = null;
        Mushroom m = mushroomService.findById(id);

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

    @Override
    public void delete(Long id) {
        mushroomService.delete(mushroomService.findById(id));
    }

    @Override
    public List<MushroomDTO> findAll() {
        List<MushroomDTO> result = null;
        List<Mushroom> m = mushroomService.findAll();

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

    @Override
    public MushroomDTO findByName(String name) {
        MushroomDTO result = null;
        Mushroom m = mushroomService.findByName(name);

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

    @Override
    public List<MushroomDTO> findByType(MushroomType type) {
        List<MushroomDTO> result = null;
        List<Mushroom> m = mushroomService.findByType(type);

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

    @Override
    public List<MushroomDTO> findByDate(Date date) {
        List<MushroomDTO> result = null;
        List<Mushroom> m = mushroomService.findByDate(date);

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

    @Override
    public List<MushroomDTO> findByDate(Date from, Date to) {
        List<MushroomDTO> result = null;
        List<Mushroom> m = mushroomService.findByDate(from, to);

        if(m != null){
            result = beanMapperService.mapTo(m, MushroomDTO.class);
        }

        return result;
    }

}
