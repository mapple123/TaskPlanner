package de.studiojan.taskkiller.db.converter;

import androidx.room.TypeConverter;

import de.studiojan.taskkiller.db.models.Item;

public class EPriorityTypeConverter {

    @TypeConverter
    public static Item.EPriority toEPriority(String value) {
        return value == null ? null : Item.EPriority.valueOf(value);
    }

    @TypeConverter
    public static String fromEPriority(Item.EPriority priority) {
        return priority == null ? null : priority.name();
    }
}