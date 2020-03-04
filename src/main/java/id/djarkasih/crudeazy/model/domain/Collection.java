/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ahmad
 */
@Entity
@Table(name="colls",
       uniqueConstraints=
       @UniqueConstraint(columnNames={"databaseId", "alias"})
) 
public class Collection {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Long databaseId;
    
    @JsonIgnore
    private String kind = "TABLE";
    
    @Column(nullable=false)
    private String name;
    
    @Column(nullable=false)
    private String alias;

    protected Collection() {
    }

    public Collection(String name) {
        this.name = name;
    }
    
    public Collection(Long dbId, String name) {
        this.databaseId = dbId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Collection{" + "id=" + id + ", databaseId=" + databaseId + ", kind=" + kind + ", alias=" + alias + '}';
    }

}
