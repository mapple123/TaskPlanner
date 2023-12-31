package de.studiojan.taskkiller.db.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

@Entity
public class List_Entity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;

    private String listName;


    public List_Entity(String listName){
        this.listName = listName;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
