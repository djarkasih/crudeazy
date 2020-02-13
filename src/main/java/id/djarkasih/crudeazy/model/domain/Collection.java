/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ahmad
 */
@Entity
@Table(name="colls") 
public class Collection {
    
    @Id
    @GeneratedValue
    private Long collectionId;
    
    private Long databaseId;
    
    @JsonIgnore
    private int kind = 0;
    
    private String name;

    protected Collection() {
    }

    public Collection(String name) {
        this.name = name;
    }
    
    public Collection(Long dbId, String name) {
        this.databaseId = dbId;
        this.name = name;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
