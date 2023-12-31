package de.studiojan.taskkiller;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.studiojan.taskkiller.db.AppDatabase;
import de.studiojan.taskkiller.db.item_helper.ItemListAdapter;
import de.studiojan.taskkiller.db.models.Item;
import de.studiojan.taskkiller.db.models.List_Entity;
import de.studiojan.taskkiller.listener.TextChangeListenerButtonNewTask;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etNewTask;
    private RecyclerView recyclerViewItemList;
    private List<Item> itemList = new ArrayList<>();
    private AppDatabase database;
    private ItemListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private LinearLayout linearLayoutNewTaskView;
    private FloatingActionButton flbAddNewTask, fabNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        etNewTask = findViewById(R.id.editTextNewTask);
        flbAddNewTask = findViewById(R.id.floatingActionButton);
        recyclerViewItemList = findViewById(R.id.recyclerviewAllTasks);
        fabNewTask = findViewById(R.id.floatingActionButtonNewTask);
        linearLayoutNewTaskView = findViewById(R.id.linearLayoutAddNewTaskView);
        setSupportActionBar(toolbar);
        database = AppDatabase.getDbInstance(this);
        itemList = database.getItemDAO().getAllItems();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewItemList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewItemList.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerViewItemList.addItemDecoration(dividerItemDecoration);
        adapter = new ItemListAdapter(MainActivity.this, itemList);
        recyclerViewItemList.setAdapter(adapter);
        etNewTask.addTextChangedListener(new TextChangeListenerButtonNewTask(flbAddNewTask));
        etNewTask.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    linearLayoutNewTaskView.setVisibility(View.GONE);
                    fabNewTask.setVisibility(View.VISIBLE);
                }
            }
        });

        fabNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutNewTaskView.getVisibility() == View.GONE){
                    linearLayoutNewTaskView.setVisibility(View.VISIBLE);
                    fabNewTask.setVisibility(View.GONE);
                    etNewTask.requestFocus();
                }
            }
        });

        flbAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = etNewTask.getText().toString().trim();
                String note = "";
                String list = "List1";
                Item.EPriority priority = Item.EPriority.HIGH;
                long listId ;
                if(database.getListDAO().getAllItems().size() == 0) {
                    database.getListDAO().insert(new List_Entity(list));
                    listId = 0;
                }
                else
                    listId = database.getListDAO().getAllItems().get(0).getId();
                if(!sText.equals("")){

                    Item item = new Item(sText, note, list, System.currentTimeMillis(), priority,
                            listId);
                    database.getItemDAO().insert(item);
                    etNewTask.setText("");
                    itemList.clear();
                    itemList.addAll(database.getItemDAO().getAllItems());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        View transparentOverlay = findViewById(R.id.container);
        transparentOverlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (etNewTask.isFocused()) {
                        Rect outRect = new Rect();
                        linearLayoutNewTaskView.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            etNewTask.clearFocus();
                        }
                    }
                }
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }
}