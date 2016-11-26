package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.VisitDao;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.Visit;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/23/16
 */
@Service
public class VisitServiceImpl implements VisitService {

    @Inject
    private VisitDao visitDao;

    @Override
    public void create(Visit visit) {
        visitDao.create(visit);
    }

    @Override
    public Visit update(Visit visit) {
        return visitDao.update(visit);
    }

    @Override
    public void delete(Visit visit) {
        visitDao.delete(visit);
    }

    @Override
    public Visit findById(Long id) {
        return visitDao.findById(id);
    }

    @Override
    public List<Visit> findAll() {
        return visitDao.findAll();
    }

    public List<Visit> findByForest(Forest forest) {
        return visitDao.findByForest(forest);
    }

    public List<Visit> findByHunter(Hunter hunter) {
        return visitDao.findByHunter(hunter);
    }

    public List<Visit> findByMushroom(Mushroom mushroom) {
        return visitDao.findByMushroom(mushroom);
    }

    public List<Visit> findByDate(Date date) {
        return visitDao.findByDate(date);
    }

    @Override
    public List<Visit> findByDate(Date fromDate, Date toDate) {
        return visitDao.findByDate(fromDate, toDate);
    }
}
