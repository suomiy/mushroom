package cz.fi.muni.pa165.rest.controller;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.RegistrateHunterDTO;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import cz.fi.muni.pa165.facade.HunterFacade;
import cz.fi.muni.pa165.rest.Uri;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 10.12.16.
 *
 * @author Erik Macej 433744
 */

@RestController
@RequestMapping(path = Uri.ROOT_URI_HUNTER, produces = MediaType.APPLICATION_JSON_VALUE)
public class HunterController {

    @Inject
    HunterFacade hunterFacade;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void registerHunter(@Valid @RequestBody RegistrateHunterDTO registrateHunterDTO)
            throws HunterAuthenticationException {

        hunterFacade.registerHunter(registrateHunterDTO);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public HunterDTO update(@Valid @RequestBody HunterDTO hunterDTO) {
        return hunterFacade.update(hunterDTO);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) { hunterFacade.delete(id); }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public HunterDTO findById(@PathVariable("id") long id) { return hunterFacade.findById(id); }

    @RequestMapping(path = "/findall", method = RequestMethod.GET)
    public List<HunterDTO> findAll() { return hunterFacade.findAll(); }

    @RequestMapping(path = "/findbyemail", method = RequestMethod.GET)
    public HunterDTO findByEmail(@RequestParam("email") String email) { return hunterFacade.findByEmail(email); }

}
