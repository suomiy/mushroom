package cz.fi.muni.pa165.service.facade;

import cz.fi.muni.pa165.dto.ForestDTO;
import cz.fi.muni.pa165.entity.Forest;
import cz.fi.muni.pa165.facade.ForestFacade;
import cz.fi.muni.pa165.service.BeanMapperService;
import cz.fi.muni.pa165.service.ForestService;
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
    BeanMapperService beanMapperService;

    @Override
    public void create(ForestDTO forest) {
        forestService.create(beanMapperService.mapTo(forest, Forest.class));
    }

    @Override
    public ForestDTO update(ForestDTO forest) {
        Forest forestEntity = forestService.update(beanMapperService.mapTo(forest, Forest.class));
        return beanMapperService.mapTo(forestEntity, ForestDTO.class);
    }

    @Override
    public ForestDTO findById(Long id) {
        Forest forest = forestService.findById(id);
        return beanMapperService.mapTo(forest, ForestDTO.class);
    }

    @Override
    public void delete(Long id) {
        Forest forest = forestService.findById(id);
        forestService.delete(forest);
    }

    @Override
    public List<ForestDTO> findAll() {
        return beanMapperService.mapTo(forestService.findAll(), ForestDTO.class);
    }

    @Override
    public ForestDTO findByName(String name) {
        Forest forest = forestService.findByName(name);
        return beanMapperService.mapTo(forest, ForestDTO.class);
    }
}
