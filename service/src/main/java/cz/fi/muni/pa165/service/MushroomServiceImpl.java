package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.dao.MushroomDao;
import cz.fi.muni.pa165.enums.MushroomType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
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

    public static final int BASE_YEAR = 1970;

    @Inject
    private MushroomDao MushroomDao;

    @Override
    public void create(Mushroom mushroom) {

        try {
            MushroomDao.create(mushroom);
        } catch (Exception e) {
            throw new DataIntegrityViolationException(mushroom + "\n" + e);
        }
    }

    @Override
    public Mushroom update(Mushroom mushroom) {

        try {
            return MushroomDao.update(mushroom);
        } catch (Exception e) {
            throw new DataIntegrityViolationException(mushroom + "\n" + e);
        }
    }

    @Override
    public Mushroom findById(Long id) {

        try {
            return MushroomDao.findById(id);
        } catch (Exception e) {
            throw new DataRetrievalFailureException(id + "\n" + e);
        }
    }

    @Override
    public void delete(Mushroom mushroom) {

        try {
            MushroomDao.delete(mushroom);
        } catch (Exception e) {
            throw new DataIntegrityViolationException(mushroom + "\n" + e);
        }
    }

    @Override
    public List<Mushroom> findAll() {

        try {
            return MushroomDao.findAll();
        } catch (Exception e) {
            throw new DataRetrievalFailureException("" + e);
        }
    }

    @Override
    public Mushroom findByName(String name) {

        try {
            return MushroomDao.findByName(name);
        } catch (Exception e) {
            throw new DataRetrievalFailureException(name + "\n" + e);
        }
    }

    @Override
    public List<Mushroom> findByType(MushroomType type) {

        try {
            return MushroomDao.findByType(type);
        } catch (Exception e) {
            throw new DataRetrievalFailureException(type + "\n" + e);
        }
    }

    @Override
    public List<Mushroom> findByDate(Date date) {

        try {
            return MushroomDao.findByDate(date);
        } catch (Exception e) {
            throw new DataRetrievalFailureException(date + "\n" + e);
        }
    }

    @Override
    public List<Mushroom> findByDate(Date from, Date to) {

        try {
            return MushroomDao.findByDate(from, to);
        } catch (Exception e) {
            throw new DataRetrievalFailureException(from + " - " + to + "\n" + e);
        }
    }


}
