package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.entity.MushroomCount;

import java.util.List;

public interface MushroomCountService {

    /**
     * Finds mushroomCount by given id
     *
     * @param id
     */
    MushroomCount findById(Long id);

    /**
     * Finds all entities mushroomCount
     *
     * @return
     */
    List<MushroomCount> findAll();

}
