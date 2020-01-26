/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ahmad
 */
@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"managerId", "name"})
}) 
public class Database {
    
    @Id
    @GeneratedValue
    private Long databaseId;
    @Column(unique=true, nullable=false)
    private String name;
    private String driverName;
    private String url;
    private String username;
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "managerId")
    private DatabaseManager manager;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @MapKey(name="name")
    private final Map<String,Collection> colls;

    public void addCollection(Collection coll) {
        colls.put(coll.getName(), coll);
        coll.setDatabase(this);
    }
 
    public void removeCollection(Collection coll) {
        colls.remove(coll.getName());
        coll.setDatabase(null);
    }

    protected Database() {
        this.colls = new HashMap();
    }

    public Database(String name, String driverName, String url, String username, String password) {
        this.colls = new HashMap();
        this.name = name;
        this.driverName = driverName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public DatabaseManager getManager() {
        return manager;
    }

    public void setManager(DatabaseManager manager) {
        this.manager = manager;
    }

    public Map<String, Collection> getRecords() {
        return colls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Database{databaseId=").append(databaseId);
        sb.append(", name=").append(name);
        sb.append(", driverName=").append(driverName);
        sb.append(", url=").append(url);
        sb.append(", username=").append(username);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.databaseId);
        hash = 47 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Database other = (Database) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.databaseId, other.databaseId)) {
            return false;
        }
        return true;
    }

}
