package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Hunter;

import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 10/29/16
 */

public interface HunterDao {

    /**
     * Creates Hunter entity.
     *
     * @param hunter hunter
     */
    void create(Hunter hunter);

    /**
     * Updates Hunter entity.
     *
     * @param hunter hunter
     */
    void update(Hunter hunter);

    /**
     * Deletes hunter from Database.
     *
     * @param hunter hunter
     */
    void delete(Hunter hunter);

    /**
     * Finds Hunter entity by id.
     *
     * @param id identifier of Hunter
     * @return Hunter
     */
    Hunter findById(Long id);

    /**
     * Finds Hunter entity by email.
     *
     * @param email email of Hunter
     * @return Hunter
     */
    Hunter findByEmail(String email);

    /**
     * Finds all Hunters from Database
     *
     * @return list of Hunters
     */
    List<Hunter> findAll();
}
