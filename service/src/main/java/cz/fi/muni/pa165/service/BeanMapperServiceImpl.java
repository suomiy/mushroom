package cz.fi.muni.pa165.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
@Service
public class BeanMapperServiceImpl implements BeanMapperService {

    @Inject
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> targetClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, targetClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object object, Class<T> targetClass) {
        return object == null ? null : dozer.map(object, targetClass);
    }

    @Override
    public Mapper getMapper() {
        return dozer;
    }
}
