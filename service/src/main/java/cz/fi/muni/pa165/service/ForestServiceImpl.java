package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.ForestDao;
import cz.fi.muni.pa165.entity.Forest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
@Service
public class ForestServiceImpl implements ForestService {

    @Inject
    ForestDao forestDao;

    @Override
    public void create(Forest forest) { forestDao.create(forest); }

    @Override
    public Forest update(Forest forest) { return forestDao.update(forest); }

    @Override
    public Forest findById(Long id) { return forestDao.findById(id); }

    @Override
    public void delete(Forest forest) { forestDao.delete(forest); }

    @Override
    public List<Forest> findAll() { return forestDao.findAll(); }

    @Override
    public Forest findByName(String name) { return forestDao.findByName(name); }
}
