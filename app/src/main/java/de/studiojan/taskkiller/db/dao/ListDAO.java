package de.studiojan.taskkiller.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.studiojan.taskkiller.db.models.Item;
import de.studiojan.taskkiller.db.models.List_Entity;


@Dao
public interface ListDAO {

    @Query("SELECT * FROM List_Entity")
    List<List_Entity> getAllItems();

    @Insert
    public void insert(List_Entity... items);
    @Query("UPDATE List_Entity SET listName = :listName WHERE ID = :sID")
    public void update(long sID, String listName);
    @Delete
    public void delete(List_Entity item);
}

