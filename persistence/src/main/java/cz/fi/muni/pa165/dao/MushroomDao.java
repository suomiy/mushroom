package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.enums.MushroomType;

import java.time.Month;
import java.util.List;

/**
 * Created by "Michal Kysilko" on 26.10.16.
 */
public interface MushroomDao {

    /**
     * Creates entity mushroom
     * @param mushroom
     */
    void create(Mushroom mushroom);

    /**
     * Edit entity mushroom
     * @param mushroom
     */
    Mushroom update(Mushroom mushroom);

    /**
     * Finds entity mushroom by given id
     * @param id
     * @return Searched entity or null if entity was not found
     */
    Mushroom findById(Long id);

    /**
     * Remove entity from databbase
     * @param mushroom
     * @throws IllegalArgumentException - if the mushroom is not an entity or is a detached entity
     */
    void delete(Mushroom mushroom) throws IllegalArgumentException;

    /**
     * Finds all entities
     * @return list of all saved entities
     */
    List<Mushroom> findAll();

    /**
     * Finds mushroom by given name
     * @param name
     * @return mushroom or null if mushroom with given name doesn't exists
     */
    Mushroom findByName(String name);

    /**
     * Finds all mushrooms of given type
     * @param type
     * @return list of all mushrooms of given type
     */
    List<Mushroom> findByType(MushroomType type);

    /**
     * Finds all mushrooms that can be found within the date criteria
     * @param month
     * @return list of found mushrooms
     */
    List<Mushroom> findByDate(Month month);

}
