package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.UserAuthenticateDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public interface HunterFacade {

    /**
     * Register given hunter with his password
     * @param hunter - hunter to register
     * @param unencryptedPassword - hunter's password
     */
    void registerHunter(HunterDTO hunter, String unencryptedPassword) throws InvalidKeySpecException,
            NoSuchAlgorithmException;

    /**
     * Authenticates Hunter
     * @return - true only if hashed password is equal with hunter passwordHash
     */
    boolean authenticate(UserAuthenticateDTO hunter) throws InvalidKeySpecException,
            NoSuchAlgorithmException;

    /**
     * @return - true if hunter is admin
     */
    boolean isAdmin(HunterDTO hunter);

    /**
     * Updates hunter
     * @param hunter
     * @return - updated hunter
     */
    HunterDTO update(HunterDTO hunter);

    /**
     * Deletes hunter from Database.
     *
     * @param hunter hunter
     */
    void delete(HunterDTO hunter);

    /**
     * Finds Hunter by id.
     *
     * @param id identifier of Hunter
     * @return Hunter
     */
    HunterDTO findById(Long id);

    /**
     * Finds Hunter by email.
     *
     * @param email email of Hunter
     * @return Hunter
     */
    HunterDTO findByEmail(String email);

    /**
     * Finds all Hunters from Database
     *
     * @return list of Hunters
     */
    Collection<HunterDTO> findAll();

    /**
     * Changes user password
     */
    void changePassword(HunterDTO hunter,String oldUnencryptedPassword, String newUnencryptedPassword)
            throws InvalidKeySpecException, NoSuchAlgorithmException;

}
