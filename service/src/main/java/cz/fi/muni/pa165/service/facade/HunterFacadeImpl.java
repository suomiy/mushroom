package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HunterCreateDTO;
import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import cz.fi.muni.pa165.facade.HunterFacade;
import cz.fi.muni.pa165.service.BeanMapperService;
import cz.fi.muni.pa165.service.HunterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
@Service
@Transactional
public class HunterFacadeImpl implements HunterFacade {

    @Inject
    HunterService hunterService;

    @Inject
    BeanMapperService beanMapperService;

    @Override
    public void registerHunter(HunterCreateDTO hunter, String unencryptedPassword) throws HunterAuthenticationException {
        Hunter hunterEntity = beanMapperService.mapTo(hunter, Hunter.class);
        hunterService.registerHunter(hunterEntity, unencryptedPassword);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO hunter) throws HunterAuthenticationException {
        Hunter hunterEntity = hunterService.findByEmail(hunter.getEmail());
        return hunterService.authenticate(hunterEntity, hunter.getPassword());
    }

    @Override
    public HunterDTO update(HunterCreateDTO hunter) {
        Hunter hunterEntity = hunterService.update(beanMapperService.mapTo(hunter, Hunter.class));
        return beanMapperService.mapTo(hunterEntity, HunterDTO.class);
    }

    @Override
    public void delete(Long id) {
        Hunter hunter = hunterService.findById(id);
        hunterService.delete(hunter);
    }

    @Override
    public HunterDTO findById(Long id) {
        Hunter hunter = hunterService.findById(id);
        return beanMapperService.mapTo(hunter, HunterDTO.class);
    }

    @Override
    public HunterDTO findByEmail(String email) {
        Hunter hunter = hunterService.findByEmail(email);
        return beanMapperService.mapTo(hunter, HunterDTO.class);
    }

    @Override
    public List<HunterDTO> findAll() {
        return beanMapperService.mapTo(hunterService.findAll(), HunterDTO.class);
    }

    @Override
    public void changePassword(Long hunterId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws HunterAuthenticationException {

        hunterService.changePassword(hunterId, oldUnencryptedPassword, newUnencryptedPassword);
    }

    @Override
    public void addVisit(Long hunterId, VisitDTO visitDto) {
        hunterService.addVisit(hunterService.findById(hunterId),
                beanMapperService.mapTo(visitDto,Visit.class));
    }

    @Override
    public void removeVisit(Long hunterId, VisitDTO visitDto) {
        hunterService.removeVisit(hunterService.findById(hunterId),
                beanMapperService.mapTo(visitDto,Visit.class));
    }
}
