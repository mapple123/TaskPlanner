package de.studiojan.taskkiller.db;

import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import de.studiojan.taskkiller.db.converter.DateTypeConverter;
import de.studiojan.taskkiller.db.converter.EPriorityTypeConverter;
import de.studiojan.taskkiller.db.dao.ItemDAO;
import de.studiojan.taskkiller.db.dao.ListDAO;
import de.studiojan.taskkiller.db.models.Item;
import de.studiojan.taskkiller.db.models.List_Entity;

@Database(entities = {Item.class, List_Entity.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class, EPriorityTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDAO getItemDAO();
    public abstract ListDAO getListDAO();
    private static AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "DB_Tasks";
    public static AppDatabase getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
