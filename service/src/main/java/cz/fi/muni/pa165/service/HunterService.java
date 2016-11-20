package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.Hunter;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    void registerHunter(Hunter hunter, String unencryptedPassword) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /**
     * Authenticates Hunter
     * @return - true only if hashed password is equal with hunter passwordHash
     */
    boolean authenticate(Hunter hunter, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /**
     * @return - true if hunter is admin
     */
    boolean isAdmin(Hunter hunter);

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
    void changePassword(Hunter hunter,String oldUnencryptedPassword, String newUnencryptedPassword)
            throws InvalidKeySpecException, NoSuchAlgorithmException;
}
