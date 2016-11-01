package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by "Michal Kysilko" on 26.10.16.
 */

@Repository
public class MushroomDaoImpl implements MushroomDao {

    public static final int BASE_YEAR = 1970;// 1970 or 1971 are not leap years

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Mushroom mushroom) {
        em.persist(mushroom);
    }

    @Override
    public Mushroom update(Mushroom mushroom) {
        return em.merge(mushroom);
    }

    @Override
    public Mushroom findById(Long id) {
        return em.find(Mushroom.class, id);
    }

    @Override
    public void delete(Mushroom mushroom) throws IllegalArgumentException {
        em.remove(mushroom);
    }

    @Override
    public List<Mushroom> findAll() {
        return em.createQuery("select m from Mushroom m"
                , Mushroom.class).getResultList();
    }

    @Override
    public Mushroom findByName(String name) {
        try {
            return em.createQuery("select m from Mushroom m where name = :name",
                    Mushroom.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Mushroom> findByType(MushroomType type) {
        try {
            return em.createQuery("select m from Mushroom m where type = :type",
                    Mushroom.class).setParameter("type", type).getResultList();
        } catch (NoResultException nrf) {
            return null;
        }
    }

    @Override
    public List<Mushroom> findByDate(Date date) {
        return findByDate(date, date);
    }

    @Override
    public List<Mushroom> findByDate(Date from, Date to) {
        if (from == null) {
            throw new IllegalArgumentException("from date can't be null");
        }

        if (to == null) {
            throw new IllegalArgumentException("to date can't be null");
        }

        Calendar fromCalendar = Calendar.getInstance();

        fromCalendar.setTime(from);
        fromCalendar.set(Calendar.YEAR, MushroomDaoImpl.BASE_YEAR);
        Calendar newYearFromCalendar = (Calendar) fromCalendar.clone();

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        toCalendar.set(Calendar.YEAR, MushroomDaoImpl.BASE_YEAR);
        Calendar newYearToCalendar = (Calendar) toCalendar.clone();

        if (toCalendar.compareTo(fromCalendar) < 0) {
            toCalendar.add(Calendar.YEAR, 1);
        } else {
            newYearFromCalendar.add(Calendar.YEAR, 1);
        }
        newYearToCalendar.add(Calendar.YEAR, 1);

        return em.createQuery("select m from Mushroom m where (fromDate <= :searchFrom and toDate >= :searchTo) " + // for 1970 - 1970 or  1970 - 1971
                        "or (fromDate <= :searchNewYearFrom and toDate >= :searchNewYearTo)", // for 1971 - 1971
                Mushroom.class)
                .setParameter("searchFrom", fromCalendar.getTime())
                .setParameter("searchTo", toCalendar.getTime())
                .setParameter("searchNewYearFrom", newYearFromCalendar.getTime())
                .setParameter("searchNewYearTo", newYearToCalendar.getTime())
                .getResultList();
    }
}
