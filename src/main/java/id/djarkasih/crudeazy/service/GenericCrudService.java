/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author ahmad
 * @param <T>
 * @param <ID>
 */
@Validated
public class GenericCrudService<T,ID> implements CrudService<T,ID> {

    //@Autowired
    private final CrudRepository<T,ID> crudRepo;
    //@Autowired
    private final JpaSpecificationExecutor<T> executor;

    public GenericCrudService(Repository<T,ID> repo) {
        crudRepo = (CrudRepository<T, ID>) repo;
        executor = (JpaSpecificationExecutor<T>) repo;
    }
    
    @Override
    public long count() {
        return crudRepo.count();
    }
    
    @Override
    public T save(@Valid T obj) {
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
    public T findMatch(Specification spec) {
        T obj = null;
        
        Optional<T> opt = executor.findOne(spec);
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
    
}
