/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import id.djarkasih.crudeazy.util.SearchCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahmad
 * @param <T>
 * @param <ID>
 */
@Service
public class GenericCrudService<T,ID> implements CrudService<T,ID> {

    @Autowired
    private CrudRepository<T,ID> crudRepo;
    @Autowired
    private JpaSpecificationExecutor<T> executor;

//    public void setup(Object repo) {
//        crudRepo = (CrudRepository<T, ID>) repo;
//        executor = (JpaSpecificationExecutor<T>) repo;
//    }
    
    @Override
    public long count() {
        return crudRepo.count();
    }
    
    @Override
    public T save(T obj) {
        return crudRepo.save(obj);
    }

    @Override
    public T find(ID id) {
        T obj = null;
        
        Optional<T> opt = crudRepo.findById(id);
        if (opt.isPresent())
            obj = opt.get();
        
        return obj;
    }

    @Override
    public List<T> readAll() {

        List<T> data = new ArrayList();
        crudRepo.findAll().forEach(data::add);
        
        return data;

    }

    @Override
    public List<T> findAll(Specification spec) {
        return executor.findAll(spec);
    }

    @Override
    public boolean delete(ID id) {
        
        boolean bOk = crudRepo.findById(id).isPresent();
        
        if (bOk) crudRepo.deleteById(id);
        
        return bOk;
        
    }
    
    public Specification<T> with(SearchCriteria criteria) {
        
        switch(criteria.getOperation()) {
            case "=" :
                return (root, cq, cb) -> cb.equal(root.get(criteria.getField()), criteria.getValue());
            case ">=" :
                if (criteria.getValue() instanceof Number)
                    return (root, cq, cb) -> cb.ge(root.get(criteria.getField()), (Number)criteria.getValue());                    
                else
                    return null;
            case ">" :
                if (criteria.getValue() instanceof Number)
                    return (root, cq, cb) -> cb.gt(root.get(criteria.getField()), (Number)criteria.getValue());
                else
                    return null;
            case "<=" :
                if (criteria.getValue() instanceof Number)
                    return (root, cq, cb) -> cb.le(root.get(criteria.getField()), (Number)criteria.getValue());
                else
                    return null;
            case "<" :
                if (criteria.getValue() instanceof Number)
                    return (root, cq, cb) -> cb.lt(root.get(criteria.getField()), (Number)criteria.getValue());
                else
                    return null;
            case "like" :
                return (root, cq, cb) -> cb.like(root.get(criteria.getField()), "%" + criteria.getValue() + "%");
            default :
                return null;
        }

    }

}
