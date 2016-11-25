package cz.fi.muni.pa165.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public interface BeanMapperService {

    /**
     * Map Collection of objects to List of objects of target class
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> targetClass);

    /**
     * Map object to targetClass
     */
    <T> T mapTo(Object u, Class<T> targetClass);

    Mapper getMapper();
}
