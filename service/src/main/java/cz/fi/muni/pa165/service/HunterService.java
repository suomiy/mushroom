package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;

import java.util.List;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
public interface HunterService {

    /**
     * Register given hunter with his password
     * @param hunter - hunter to register
     * @param unencryptedPassword - hunter's password
     */
    void registerHunter(Hunter hunter, String unencryptedPassword) throws HunterAuthenticationException;

    /**
     * Authenticates Hunter
     * @return - true only if hashed password is equal with hunter passwordHash
     */
    boolean authenticate(Hunter hunter, String password) throws HunterAuthenticationException;

    /**
     * Updates hunter
     * @param hunter
     * @return - updated hunter
     */
    Hunter update(Hunter hunter);

    /**
     * Deletes hunter from Database.
     *
     * @param hunter hunter
     */
    void delete(Hunter hunter);

    /**
     * Finds Hunter by id.
     *
     * @param id identifier of Hunter
     * @return Hunter
     */
    Hunter findById(Long id);

    /**
     * Finds Hunter by email.
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

    /**
     * Changes user password
     */
    void changePassword(Long hunterId,String oldUnencryptedPassword, String newUnencryptedPassword)
            throws HunterAuthenticationException;

    /**
     * Add visit to hunter
     */
    void addVisit(Hunter hunter, Visit visit);

    /**
     * Remove visit from hunter
     */
    void removeVisit(Hunter hunter, Visit visit);


}
