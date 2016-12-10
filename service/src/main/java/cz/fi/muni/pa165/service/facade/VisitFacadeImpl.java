package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.facade.VisitFacade;
import cz.fi.muni.pa165.service.HunterService;
import cz.fi.muni.pa165.service.VisitService;
import cz.fi.muni.pa165.service.mappers.ForestMapperService;
import cz.fi.muni.pa165.service.mappers.HunterMapperService;
import cz.fi.muni.pa165.service.mappers.MushroomMapperService;
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
    private VisitMapperService mapperService;

    @Inject
    private ForestMapperService forestMapperService;

    @Inject
    private MushroomMapperService mushroomMapperService;

    @Override
    public void create(VisitDTO visit) {
        Visit v = mapperService.asEntity(visit);
        visitService.create(v);
        visit.setId(v.getId());
    }

    @Override
    public VisitDTO update(VisitDTO visit) {
        Visit updated = visitService.update(mapperService.asEntity(visit));
        return mapperService.asDto(updated);
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
    public List<VisitDTO> findByForest(ForestDTO forest) {
        List<Visit> found = visitService.findByForest(forestMapperService.asEntity(forest));
        return mapperService.asDtos(found);
    }

    @Override
    public List<VisitDTO> findByHunter(Long hunterId) {
        Hunter hunter = hunterService.findById(hunterId);
        List<Visit> found = visitService.findByHunter(hunter);
        return mapperService.asDtos(found);
    }

    @Override
    public List<VisitDTO> findByMushroom(MushroomDTO mushroom) {
        List<Visit> found = visitService.findByMushroom(mushroomMapperService.asEntity(mushroom));
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
