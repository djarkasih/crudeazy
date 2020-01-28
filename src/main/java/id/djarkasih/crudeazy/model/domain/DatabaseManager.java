/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

/**
 *
 * @author ahmad
 */
@Entity
public class DatabaseManager {
    
    @Id
    @GeneratedValue
    private Long managerId;
    
    @Column(unique=true, nullable=false)
    private String name;
    private final Date createdAt;
    
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @MapKey(name="name")
    private final Map<String,Database> databases;

    public void addDatabase(Database db) {
        databases.put(db.getName(), db);
        db.setManager(this);
    }
 
    public void removeDatabase(Database db) {
        databases.remove(db.getName());
        db.setManager(null);
    }
    
    protected DatabaseManager() {
        this.databases = new HashMap();
        this.name = "anonymous";
        this.createdAt = Date.valueOf(LocalDate.now());
    }

    public DatabaseManager(String name) {
        this.databases = new HashMap();
        this.name = name;
        this.createdAt = Date.valueOf(LocalDate.now());
    }

    public Long getManagerId() {
        return managerId;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Map<String, Database> getDatabases() {
        return databases;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DatabaseManager{managerId=").append(managerId);
        sb.append(", name=").append(name);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", databases=").append(databases);
        sb.append('}');
        return sb.toString();
    }
    
}
