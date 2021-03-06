package cz.fi.muni.pa165.rest.controller;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.facade.VisitFacade;
import cz.fi.muni.pa165.rest.Uri;
import cz.fi.muni.pa165.rest.security.ResourceAccess;
import cz.fi.muni.pa165.service.VisitService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by michal on 14.12.16.
 *
 * @author Michal Kysilko, 436339
 */

@RestController
@RequestMapping(path = Uri.ROOT_URI_VISIT, produces = MediaType.APPLICATION_JSON_VALUE)
public class VisitController {

    @Inject
    VisitFacade visitFacade;

    @Inject
    VisitService visitService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public VisitDTO create(@Valid @RequestBody VisitDTO visit) {
        visitFacade.create(visit);
        return visit;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public VisitDTO update(@AuthenticationPrincipal Hunter loggedHunter, @Valid @RequestBody VisitDTO visit) {
        ResourceAccess.verify(loggedHunter, visit);
        return visitFacade.update(visit);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@AuthenticationPrincipal Hunter loggedHunter, @PathVariable("id") long id) {
        ResourceAccess.verify(loggedHunter, visitService.findById(id));
        visitFacade.delete(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public VisitDTO findById(@PathVariable("id") long id) {
        return visitFacade.findById(id);
    }

    @RequestMapping(path = "/findall", method = RequestMethod.GET)
    public List<VisitDTO> findAll() {
        return visitFacade.findAll();
    }

    @RequestMapping(path = "/findbyhunter", method = RequestMethod.GET)
    public List<VisitDTO> findByHunter(@RequestParam("id") Long hunterId) {
        return visitFacade.findByHunter(hunterId);
    }

    @RequestMapping(path = "/findbyforest", method = RequestMethod.GET)
    public List<VisitDTO> findByForest(@RequestParam("id") Long forestId) {
        return visitFacade.findByForest(forestId);
    }

    @RequestMapping(path = "/findbymushroom", method = RequestMethod.GET)
    public List<VisitDTO> findByMushroom(@RequestParam("id") Long mushroomId) {
        return visitFacade.findByMushroom(mushroomId);
    }

    @RequestMapping(path = "/findbydate", method = RequestMethod.POST)
    public List<VisitDTO> findByDate(@Valid @RequestBody DateDTO date) {
        return visitFacade.findByDate(date);
    }
}
