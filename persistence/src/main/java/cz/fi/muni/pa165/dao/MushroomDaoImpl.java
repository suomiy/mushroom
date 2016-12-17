package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.utils.DateIntervalUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by "Michal Kysilko" on 26.10.16.
 *
 * @author Michal Kysilko 436339
 */

@Repository
public class MushroomDaoImpl implements MushroomDao {

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
    public void delete(Mushroom mushroom) {
        em.remove(mushroom);
    }

    @Override
    public List<Mushroom> findAll() {
        return em.createQuery("select m from Mushroom m"
                , Mushroom.class).getResultList();
    }

    @Override
    public Mushroom findByName(String name) {
        List<Mushroom> mushrooms = em.createQuery("select m from Mushroom m WHERE lower(name) like CONCAT('%', lower(:name), '%')",
                Mushroom.class)
                .setMaxResults(1)
                .setParameter("name", name)
                .getResultList();
        return mushrooms.isEmpty() ? null : mushrooms.get(0);
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
        Assert.notNull(from, "from date can't be null");
        Assert.notNull(from, "to date can't be null");

        DateIntervalUtils.FromToQueryDates params = DateIntervalUtils.getDateQueryParameters(from, to);

        return em.createQuery("select m from Mushroom m where (fromDate <= :searchFrom and toDate >= :searchTo) " +
                        "or (fromDate <= :searchNewYearFrom and toDate >= :searchNewYearTo)",
                Mushroom.class)
                .setParameter("searchFrom", params.getSearchFrom1970())
                .setParameter("searchTo", params.getSearchTo1970())
                .setParameter("searchNewYearFrom", params.getSearchFrom1971())
                .setParameter("searchNewYearTo", params.getSearchTo1971())
                .getResultList();
    }
}
