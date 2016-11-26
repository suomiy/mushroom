package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.facade.ForestFacade;
import cz.fi.muni.pa165.service.ForestService;
import cz.fi.muni.pa165.service.mappers.ForestMapperService;
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
public class ForestFaceadeImpl implements ForestFacade {

    @Inject
    ForestService forestService;

    @Inject
    ForestMapperService mapperService;

    @Override
    public void create(ForestDTO forest) {
        forestService.create(mapperService.asEntity(forest));
    }

    @Override
    public ForestDTO update(ForestDTO forest) {
        Forest forestEntity = forestService.update(mapperService.asEntity(forest));
        return mapperService.asDto(forestEntity);
    }

    @Override
    public ForestDTO findById(Long id) {
        Forest forest = forestService.findById(id);
        return mapperService.asDto(forest);
    }

    @Override
    public void delete(Long id) {
        Forest forest = forestService.findById(id);
        forestService.delete(forest);
    }

    @Override
    public List<ForestDTO> findAll() {
        return mapperService.asDtos(forestService.findAll());
    }

    @Override
    public ForestDTO findByName(String name) {
        Forest forest = forestService.findByName(name);
        return mapperService.asDto(forest);
    }
}
