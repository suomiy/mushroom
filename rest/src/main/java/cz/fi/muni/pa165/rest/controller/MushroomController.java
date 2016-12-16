package cz.fi.muni.pa165.rest.controller;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.DateIntervalDTO;
import cz.fi.muni.pa165.dto.MushroomDTO;
import cz.fi.muni.pa165.enums.MushroomType;
import cz.fi.muni.pa165.facade.MushroomFacade;
import cz.fi.muni.pa165.rest.Uri;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 12/4/16
 */

@RestController
@RequestMapping(path = Uri.ROOT_URI_MUSHROOM, produces = MediaType.APPLICATION_JSON_VALUE)
public class MushroomController {

    @Inject
    MushroomFacade mushroomFacade;

    @RequestMapping(path = Uri.Part.CREATE, method = RequestMethod.POST)
    public MushroomDTO create(@Valid @RequestBody MushroomDTO mushroom) {
        mushroomFacade.create(mushroom);
        return mushroom;
    }

    @RequestMapping(path = Uri.Part.UPDATE, method = RequestMethod.POST)
    public MushroomDTO update(@Valid @RequestBody MushroomDTO mushroom) {
        return mushroomFacade.update(mushroom);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        mushroomFacade.delete(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public MushroomDTO findById(@PathVariable("id") long id) {
        return mushroomFacade.findById(id);
    }

    @RequestMapping(path = "/findall", method = RequestMethod.GET)
    public List<MushroomDTO> findAll() {
        return mushroomFacade.findAll();
    }

    @RequestMapping(path = "/find", method = RequestMethod.GET)
    public MushroomDTO findByName(@RequestParam("name") String name) {
        return mushroomFacade.findByName(name);
    }

    @RequestMapping(path = "/findbytype", method = RequestMethod.GET)
    public List<MushroomDTO> findByType(@RequestParam("type") MushroomType type) {
        return mushroomFacade.findByType(type);
    }

    @RequestMapping(path = "/findbydate", method = RequestMethod.POST)
    public List<MushroomDTO> findByDate(@Valid @RequestBody DateDTO date) {
        return mushroomFacade.findByDate(date);
    }

    @RequestMapping(path = "/findbydateinterval", method = RequestMethod.POST)
    public List<MushroomDTO> findByDate(@Valid @RequestBody DateIntervalDTO interval) {
        return mushroomFacade.findByDate(interval);
    }
}
