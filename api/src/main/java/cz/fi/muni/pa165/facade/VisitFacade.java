package cz.fi.muni.pa165.facade;

import cz.fi.muni.pa165.dto.*;

import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/23/16
 */
public interface VisitFacade {

    /**
     * Create new VisitDTO entity.
     *
     * @param visitDTO the visitDTO to be created
     */
    void create(VisitDTO visitDTO);

    /**
     * Update VisitDTO entity.
     *
     * @param visitDTO VisitDTO to be updated
     * @return updated VisitDTO
     */
    VisitDTO update(VisitDTO visitDTO);

    /**
     * Deletes visitDTO
     *
     * @param id of the visit to be deleted
     */
    void delete(Long id);

    VisitDTO findById(Long id);

    /**
     * Find all visitDTOs
     *
     * @return the list of all visitDTOs
     */
    List<VisitDTO> findAll();

    /**
     * Find all visitDTOs for given forest.
     *
     * @param forest ForestDTO entity
     * @return list of all visitDTOs for given forest
     */
    List<VisitDTO> findByForest(ForestDTO forest);

    /**
     * Find all visitDTOs for given hunter.
     *
     * @param hunterId id of hunter entity
     * @return list of all visitDTOs for given hunter
     */
    List<VisitDTO> findByHunter(Long hunterId);

    /**
     * Find all visitDTOs for given mushroom.
     *
     * @param mushroom MushroomDTO entity
     * @return list of all visitDTOs for given mushroom
     */
    List<VisitDTO> findByMushroom(MushroomDTO mushroom);

    /**
     * Finds all visits that can be found in this Date (year and time are ignored)
     *
     * @param date date
     * @return list of all visitDTOs for given date
     */
    List<VisitDTO> findByDate(DateDTO date);

    /**
     * Finds all visits that can be found within the date range (year and time are ignored)
     *
     * @param interval
     * @return list of found visitDTOs
     */
    List<VisitDTO> findByDate(DateIntervalDTO interval);
}

