package cz.fi.muni.pa165.rest.controller;

import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.facade.ForestFacade;
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
@RequestMapping(path = Uri.ROOT_URI_FOREST, produces = MediaType.APPLICATION_JSON_VALUE)
public class ForestController {

    @Inject
    ForestFacade forestFacade;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ForestDTO create(@Valid @RequestBody ForestDTO forest) {
        forestFacade.create(forest);
        return forest;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ForestDTO update(@Valid @RequestBody ForestDTO forest) {
        return forestFacade.update(forest);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        forestFacade.delete(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ForestDTO findById(@PathVariable("id") long id) {
        return forestFacade.findById(id);
    }

    @RequestMapping(path = "/findall", method = RequestMethod.GET)
    public List<ForestDTO> findAll() {
        return forestFacade.findAll();
    }

    @RequestMapping(path = "/find", method = RequestMethod.POST)
    public ForestDTO findByName(@RequestParam("name") String name) {
        return forestFacade.findByName(name);
    }
}
