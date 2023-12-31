package de.studiojan.taskkiller.db.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import de.studiojan.taskkiller.db.converter.DateTypeConverter;

//@Entity(tableName = "items")
@Entity(foreignKeys =  @ForeignKey(entity = List_Entity.class,
                                    parentColumns = "id",
                                    childColumns = "listId",
                                    onDelete = CASCADE))
public class Item implements Serializable {

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public enum EPriority{
        LOW,MIDDLE,HIGH;
    }
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String task, note, list;
    private long creationTimeStamp;
    private EPriority priority;
    private boolean status = false;

    private long listId;
    //private String description;
    //private Long quantity;

    public Item(String task, String note, String list, long creationTimeStamp, EPriority priority,
                long listId){
        this.task = task;
        this.note = note;
        this.list = list;
        this.creationTimeStamp = creationTimeStamp;
        this.priority = priority;
        this.listId = listId;
    }



   /* public Long getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }*/

    public String getTask() {
        return task;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(long creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public EPriority getPriority() {
        return priority;
    }

    public void setPriority(EPriority priority) {
        this.priority = priority;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


}
