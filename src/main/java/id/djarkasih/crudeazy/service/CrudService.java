/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author ahmad
 * @param <T>
 * @param <ID>
 */
@Validated
public interface CrudService<T, ID> {
    
    public long count();
    public T save(@Valid T t);
    public T find(ID id);
    public T findMatch(Specification spec);
    public List<T> readAll();
    public List<T> findAll(Specification spec);
    public boolean delete(ID id);
    
}
