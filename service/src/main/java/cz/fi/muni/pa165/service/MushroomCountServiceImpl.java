package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.MushroomCountDao;
import cz.fi.muni.pa165.entity.MushroomCount;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public class MushroomCountServiceImpl implements MushroomCountService {

    @Inject
    MushroomCountDao mushroomCountDao;

    @Override
    public MushroomCount findById(Long id) { return mushroomCountDao.findById(id); }

    @Override
    public List<MushroomCount> findAll() { return mushroomCountDao.findAll(); }
}
