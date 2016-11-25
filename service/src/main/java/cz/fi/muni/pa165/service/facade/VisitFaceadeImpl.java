package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.entity.Mushroom;
import cz.fi.muni.pa165.entity.Visit;
import cz.fi.muni.pa165.facade.VisitFacade;
import cz.fi.muni.pa165.service.BeanMapperService;
import cz.fi.muni.pa165.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Filip Krepinsky (410022) on 11/23/16
 */
@Service
@Transactional
public class VisitFaceadeImpl implements VisitFacade {

    @Inject
    VisitService visitService;

    @Inject
    BeanMapperService beanMapperService;

    @Override
    public void create(VisitDTO visit) {
        visitService.create(beanMapperService.mapTo(visit, Visit.class));
    }

    @Override
    public VisitDTO update(VisitDTO visit) {
        Visit updated = visitService.update(beanMapperService.mapTo(visit, Visit.class));
        return beanMapperService.mapTo(updated, VisitDTO.class);
    }

    @Override
    public void delete(Long id) {
        visitService.delete(beanMapperService.mapTo(visitService.findById(id), Visit.class));
    }

    @Override
    public VisitDTO findById(Long id) {
        return beanMapperService.mapTo(visitService.findById(id), VisitDTO.class);
    }

    @Override
    public List<VisitDTO> findAll() {
        return beanMapperService.mapTo(visitService.findAll(), VisitDTO.class);
    }

    @Override
    public List<VisitDTO> findByForest(ForestDTO forest) {
        List<Visit> found = visitService.findByForest(beanMapperService.mapTo(forest, Forest.class));
        return beanMapperService.mapTo(found, VisitDTO.class);
    }

    @Override
    public List<VisitDTO> findByHunter(HunterDTO hunter) {
        List<Visit> found = visitService.findByHunter(beanMapperService.mapTo(hunter, Hunter.class));
        return beanMapperService.mapTo(found, VisitDTO.class);
    }

    @Override
    public List<VisitDTO> findByMushroom(MushroomDTO mushroom) {
        List<Visit> found = visitService.findByMushroom(beanMapperService.mapTo(mushroom, Mushroom.class));
        return beanMapperService.mapTo(found, VisitDTO.class);
    }

    @Override
    public List<VisitDTO> findByDate(DateDTO date) {
        List<Visit> found = visitService.findByDate(date.getDate());
        return beanMapperService.mapTo(found, VisitDTO.class);
    }

    @Override
    public List<VisitDTO> findByDate(DateIntervalDTO interval) {
        List<Visit> found = visitService.findByDate(interval.getFrom(), interval.getTo());
        return beanMapperService.mapTo(found, VisitDTO.class);
    }
}
