package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.dao.MushroomDao;
import cz.fi.muni.pa165.enums.MushroomType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by Michal Kysilko 436339 on 11/23/16.
 *
 * @author Michal Kysilko 436339
 */

@Service
public class MushroomServiceImpl implements MushroomService {

    @Inject
    private MushroomDao MushroomDao;

    @Override
    public void create(Mushroom mushroom) {
            MushroomDao.create(mushroom);
    }

    @Override
    public Mushroom update(Mushroom mushroom)  {
            return MushroomDao.update(mushroom);
    }

    @Override
    public Mushroom findById(Long id) {
            return MushroomDao.findById(id);
    }

    @Override
    public void delete(Mushroom mushroom) {
            MushroomDao.delete(mushroom);
    }

    @Override
    public List<Mushroom> findAll() {
            return MushroomDao.findAll();
    }

    @Override
    public Mushroom findByName(String name) {
            return MushroomDao.findByName(name);
    }

    @Override
    public List<Mushroom> findByType(MushroomType type) {
            return MushroomDao.findByType(type);
    }

    @Override
    public List<Mushroom> findByDate(Date date) {
            return MushroomDao.findByDate(date);

    }

    @Override
    public List<Mushroom> findByDate(Date from, Date to) {
            return MushroomDao.findByDate(from, to);
    }


}
