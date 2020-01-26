/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ahmad
 */
@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"databaseId", "name"})
}) 
public class Record {
    
    @Id
    @GeneratedValue
    private Long recordId;
    @ManyToOne
    @JoinColumn(name = "databaseId")
    private Database database;
    private int kind;
    private String name;

    protected Record() {
        this.kind = 0;
    }

    public Record(String name) {
        this.kind = 0;
        this.name = name;
    }

    public Long getRecordId() {
        return recordId;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Record{recordId=").append(recordId);
        sb.append(", kind=").append(kind);
        sb.append(", name=").append(name);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.recordId);
        hash = 67 * hash + Objects.hashCode(this.name);
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
        final Record other = (Record) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.recordId, other.recordId)) {
            return false;
        }
        return true;
    }
    
    
    
}
