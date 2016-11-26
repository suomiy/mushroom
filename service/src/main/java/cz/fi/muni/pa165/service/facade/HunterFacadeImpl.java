package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.HunterDTO;
import cz.fi.muni.pa165.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import cz.fi.muni.pa165.facade.HunterFacade;
import cz.fi.muni.pa165.service.HunterService;
import cz.fi.muni.pa165.service.mappers.HunterMapperService;
import cz.fi.muni.pa165.service.mappers.VisitMapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    HunterMapperService mapperService;

    @Inject
    VisitMapperService visitMapperService;

    @Override
    public void registerHunter(HunterDTO hunterDTO, String unencryptedPassword) throws HunterAuthenticationException {
        Hunter hunterEntity = mapperService.asEntity(hunterDTO);
        hunterService.registerHunter(hunterEntity, unencryptedPassword);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO hunter) throws HunterAuthenticationException {
        Hunter hunterEntity = hunterService.findByEmail(hunter.getEmail());
        return hunterService.authenticate(hunterEntity, hunter.getPassword());
    }

    @Override
    public HunterDTO update(HunterDTO hunterDTO) {
        Hunter hunter = mapperService.asEntity(hunterDTO);

        Hunter hunterEntity = hunterService.findById(hunter.getId());
        hunterEntity.setEmail(hunter.getEmail());
        hunterEntity.setRank(hunter.getRank());
        hunterEntity.setType(hunter.getType());
        hunterEntity.setFirstName(hunter.getFirstName());
        hunterEntity.setNick(hunter.getNick());
        hunterEntity.setSurname(hunter.getSurname());
        hunterEntity = hunterService.update(hunterEntity);

        return mapperService.asDto(hunterEntity);
    }

    @Override
    public void delete(Long id) {
        Hunter hunter = hunterService.findById(id);
        hunterService.delete(hunter);
    }

    @Override
    public HunterDTO findById(Long id) {
        Hunter hunter = hunterService.findById(id);
        return mapperService.asDto(hunter);
    }

    @Override
    public HunterDTO findByEmail(String email) {
        Hunter hunter = hunterService.findByEmail(email);
        return mapperService.asDto(hunter);
    }

    @Override
    public List<HunterDTO> findAll() {
        return mapperService.asDtos(hunterService.findAll());
    }

    @Override
    public void changePassword(Long hunterId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws HunterAuthenticationException {

        hunterService.changePassword(hunterId, oldUnencryptedPassword, newUnencryptedPassword);
    }
}
