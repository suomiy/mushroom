package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.MushroomCountDao;
import cz.fi.muni.pa165.dao.VisitDao;
import cz.fi.muni.pa165.entity.MushroomCount;
import cz.fi.muni.pa165.entity.Visit;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744, Filip Krepinsky (410022)
 */
@Service
public class MushroomCountServiceImpl implements MushroomCountService {

    @Inject
    MushroomCountDao mushroomCountDao;

    @Inject
    VisitDao visitDao;

    @Inject
    TimeService timeService;

    @Override
    public void create(MushroomCount mushroomCount) {
        mushroomCountDao.create(mushroomCount);
    }

    @Override
    public MushroomCount update(MushroomCount mushroomCount) {
        return mushroomCountDao.update(mushroomCount);
    }

    @Override
    public void delete(MushroomCount mushroomCount) {
        mushroomCountDao.delete(mushroomCount);
    }

    @Override
    public MushroomCount findById(Long id) {
        return mushroomCountDao.findById(id);
    }

    @Override
    public List<MushroomCount> findAll() {
        return mushroomCountDao.findAll();
    }

    @Override
    public List<MushroomCount> findRecentlyFoundPickableMushrooms() {
        Date from = timeService.getOneWeekBeforeTime();
        Date to = timeService.getCurrentTime();

        Set<MushroomCount> result = new HashSet<>();

        for (Visit visit : visitDao.findByDate(from, to)) {
            result.addAll(visit.getMushroomsCount());
        }

        return new ArrayList<>(result);
    }
}
