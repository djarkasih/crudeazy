/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.util;

import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author ahmad
 */
public class GenericSpecification<T> {
    
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
