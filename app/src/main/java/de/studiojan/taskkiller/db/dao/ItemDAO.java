package de.studiojan.taskkiller.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.studiojan.taskkiller.db.models.Item;


@Dao
public interface ItemDAO {

    @Query("SELECT * FROM Item")
    List<Item> getAllItems();

    @Query("SELECT * FROM Item WHERE listId=:listId")
    public List<Item> findListsForItem(long listId);
    @Insert
    public void insert(Item... items);
    @Query("UPDATE Item SET task = :sTask WHERE ID = :sID")
    public void update(long sID, String sTask);
    @Delete
    public void delete(Item item);
}
