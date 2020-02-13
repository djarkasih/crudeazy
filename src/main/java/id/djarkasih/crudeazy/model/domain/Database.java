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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author ahmad
 */
@Entity
@Table(name="dbs")
public class Database {
    
    @Id
    @GeneratedValue
    private Long databaseId;
    
    @JsonIgnore
    private String userId = "crudeazyadm";
    
    @Column(unique=true, nullable=false)
    @Size(min=4,message="Database name should be at least 4 characters long")
    private String name;
    
    private String driverName;
    
    private String url;
    
    @Size(min=8,max=16,message="Username length should be between 8 to 16 characters")
    private String username;
    
    @Size(min=8,message="Password length should be at least 8 characters")
    private String password;
    
    protected Database() {
    }

    public Database(String name, String driverName, String url, String username, String password) {
        this.name = name;
        this.driverName = driverName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Database{databaseId=").append(databaseId);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", driverName=").append(driverName);
        sb.append(", url=").append(url);
        sb.append(", username=").append(username);
        sb.append('}');
        return sb.toString();
    }

}
