package de.studiojan.taskkiller.db.item_helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.studiojan.taskkiller.R;
import de.studiojan.taskkiller.db.AppDatabase;
import de.studiojan.taskkiller.db.models.Item;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    private Context context;
    private AppDatabase database;
    private List<Item> itemList;

    public ItemListAdapter(Context context, List<Item> itemList){
        this.context = context;
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        database = AppDatabase.getDbInstance(context);
        holder.txtViewTask.setText(item.getTask());
        holder.btMenu.setOnClickListener(view -> {
            if(holder.btEdit.getVisibility() == View.GONE && holder.btDelete.getVisibility() ==
                View.GONE){
                showButtonWithAnimation(holder.btEdit);
                showButtonWithAnimation(holder.btDelete);
            }else{
                hideButtonWithAnimation(holder.btEdit);
                hideButtonWithAnimation(holder.btDelete);
            }
        });
        holder.btEdit.setOnClickListener(view -> {
            Item i = itemList.get(holder.getAdapterPosition());
            long sID = i.getId();
            String sTask = i.getTask();
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_update);
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
            dialog.show();
            EditText editText = dialog.findViewById(R.id.editTextUpdateTask);
            Button buttonUpdate = dialog.findViewById(R.id.buttonUpdate);
            buttonUpdate.setOnClickListener(view1 -> {
                dialog.dismiss();
                String updateText = editText.getText().toString().trim();
                database.getItemDAO().update(sID,updateText);
                itemList.clear();
                itemList.addAll(database.getItemDAO().getAllItems());
                notifyDataSetChanged();
            });
        });
        holder.btDelete.setOnClickListener(view -> {
            Item i = itemList.get(holder.getAdapterPosition());
            database.getItemDAO().delete(i);
            int position1 = holder.getAdapterPosition();
            itemList.remove(position1);
            notifyItemRemoved(position1);
            notifyItemRangeRemoved(position1,itemList.size());
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    private void showButtonWithAnimation(View component) {
        if (component.getVisibility() == View.GONE) {
            component.setVisibility(View.VISIBLE);
            component.setTranslationX(component.getWidth()+100);
            component.animate()
                    .translationX(0)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }
                    });
        }
    }
    private void hideButtonWithAnimation(View component) {
        if (component.getVisibility() == View.VISIBLE) {
            component.animate()
                    .translationX(component.getWidth())
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            component.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtViewTask;
        public ImageView btEdit, btDelete, btMenu;
        public MyViewHolder(View view){
            super(view);
             txtViewTask = view.findViewById(R.id.textViewTask);
             btEdit = view.findViewById(R.id.buttonEdit);
             btDelete = view.findViewById(R.id.buttonDelete);
             btMenu = view.findViewById(R.id.buttonMenu);
        }
    }
}
