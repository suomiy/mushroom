package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;

import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public interface HunterFacade {

    /**
     * Register given hunter with his password
     *
     * @param registrateHunterDTO - hunter to register
     */
    void registerHunter(RegistrateHunterDTO registrateHunterDTO) throws HunterAuthenticationException;

    /**
     * Updates hunter
     *
     * @param hunter
     * @return - updated hunter
     */
    HunterDTO update(HunterDTO hunter);

    /**
     * Deletes hunter from Database.
     *
     * @param id id
     */
    void delete(Long id);

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
    List<HunterDTO> findAll();

    /**
     * Changes user password
     */
    void changePassword(Long hunterId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws HunterAuthenticationException;
}
