package cz.fi.muni.pa165.rest.controller;

import cz.fi.muni.pa165.dto.MushroomCountDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.facade.MushroomCountFacade;
import cz.fi.muni.pa165.rest.security.ResourceAccess;
import cz.fi.muni.pa165.rest.Uri;
import cz.fi.muni.pa165.service.MushroomCountService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Inject
    MushroomCountService mushroomCountService;

    @RequestMapping(path = Uri.Part.CREATE, method = RequestMethod.POST)
    public MushroomCountDTO create(@Valid @RequestBody MushroomCountDTO mushroomCount) {
        mushroomCountFacade.create(mushroomCount);
        return mushroomCount;
    }

    @RequestMapping(path = Uri.Part.UPDATE, method = RequestMethod.POST)
    public MushroomCountDTO update(@AuthenticationPrincipal Hunter loggedHunter, @Valid @RequestBody MushroomCountDTO mushroomCount) {
        ResourceAccess.verify(loggedHunter, mushroomCountService.findById(mushroomCount.getId()));
        return mushroomCountFacade.update(mushroomCount);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@AuthenticationPrincipal Hunter loggedHunter, @PathVariable("id") long id) {
        ResourceAccess.verify(loggedHunter, mushroomCountService.findById(id));
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
