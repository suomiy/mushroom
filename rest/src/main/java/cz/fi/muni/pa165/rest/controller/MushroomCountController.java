package cz.fi.muni.pa165.rest.controller;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.facade.MushroomCountFacade;
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
@RequestMapping(path = Uri.ROOT_URI_MUSCHROOM_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
public class MushroomCountController {

    @Inject
    MushroomCountFacade mushroomCountFacade;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public MushroomCountDTO create(@Valid @RequestBody MushroomCountDTO mushroomCount) {
        mushroomCountFacade.create(mushroomCount);
        return mushroomCount;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public MushroomCountDTO update(@Valid @RequestBody MushroomCountDTO mushroomCount) {
        return mushroomCountFacade.update(mushroomCount);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        mushroomCountFacade.delete(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public MushroomCountDTO findById(@PathVariable("id") long id) {
        return mushroomCountFacade.findById(id);
    }

    @RequestMapping(path = "/findall", method = RequestMethod.GET)
    public List<MushroomCountDTO> findAll() {
        return mushroomCountFacade.findAll();
    }

    @RequestMapping(path = "/findrecentlypickable", method = RequestMethod.GET)
    public List<MushroomCountDTO> findRecentlyPickable() {
        return mushroomCountFacade.findRecentlyFoundPickableMushrooms();
    }
}
