package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.DateDTO;
import cz.fi.muni.pa165.dto.DateIntervalDTO;
import cz.fi.muni.pa165.dto.VisitDTO;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.facade.VisitFacade;
import cz.fi.muni.pa165.service.ForestService;
import cz.fi.muni.pa165.service.HunterService;
import cz.fi.muni.pa165.service.MushroomService;
import cz.fi.muni.pa165.service.VisitService;
import cz.fi.muni.pa165.service.mappers.VisitMapperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/23/16
 */
@Service
@Transactional
public class VisitFacadeImpl implements VisitFacade {

    @Inject
    private VisitService visitService;

    @Inject
    private HunterService hunterService;

    @Inject
    private MushroomService mushroomService;

    @Inject
    private ForestService forestService;

    @Inject
    private VisitMapperService mapperService;

    @Override
    public void create(VisitDTO visit) {
        Visit v = mapperService.asEntity(visit);
        v.setForest(forestService.findById(visit.getForestId()));
        v.setHunter(hunterService.findById(visit.getHunterId()));
        visitService.create(v);
        visit.setId(v.getId());
    }

    @Override
    public VisitDTO update(VisitDTO visit) {
        Visit v = mapperService.asEntity(visit);
        v.setForest(forestService.findById(visit.getForestId()));
        v.setHunter(hunterService.findById(visit.getHunterId()));
        return mapperService.asDto(visitService.update(v));
    }

    @Override
    public void delete(Long id) {
        visitService.delete(visitService.findById(id));
    }

    @Override
    public VisitDTO findById(Long id) {
        return mapperService.asDto(visitService.findById(id));
    }

    @Override
    public List<VisitDTO> findAll() {
        return mapperService.asDtos(visitService.findAll());
    }

    @Override
    public List<VisitDTO> findByForest(Long forestId) {
        Forest forest = forestService.findById(forestId);
        List<Visit> found = visitService.findByForest(forest);
        return mapperService.asDtos(found);
    }

    @Override
    public List<VisitDTO> findByHunter(Long hunterId) {
        Hunter hunter = hunterService.findById(hunterId);
        List<Visit> found = visitService.findByHunter(hunter);
        return mapperService.asDtos(found);
    }

    @Override
    public List<VisitDTO> findByMushroom(Long mushroomId) {
        Mushroom mushroom = mushroomService.findById(mushroomId);
        List<Visit> found = visitService.findByMushroom(mushroom);
        return mapperService.asDtos(found);
    }

    @Override
    public List<VisitDTO> findByDate(DateDTO date) {
        List<Visit> found = visitService.findByDate(date.getDate());
        return mapperService.asDtos(found);
    }

    @Override
    public List<VisitDTO> findByDate(DateIntervalDTO interval) {
        List<Visit> found = visitService.findByDate(interval.getFrom(), interval.getTo());
        return mapperService.asDtos(found);
    }
}
