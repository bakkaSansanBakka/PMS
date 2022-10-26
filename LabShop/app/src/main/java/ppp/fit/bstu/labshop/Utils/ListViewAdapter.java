package ppp.fit.bstu.labshop.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import ppp.fit.bstu.labshop.Models.Appliances;
import ppp.fit.bstu.labshop.*;
import ppp.fit.bstu.labshop.Utils.FileManager;
import ppp.fit.bstu.labshop.Utils.ItemClickListener;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> implements DeleteConfirmDialog.Listener {

    private final LayoutInflater inflater;
    private final ArrayList<Appliances> appliances;
    private ItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private Appliances appliancesItem;
    private Context _context;
    private ListViewAdapter entity;
    String fileName = "appliancesList.json";
    ListViewAdapter.ViewHolder holder;

    @Override
    public void onDelete() {
        removeItem();
    }

    public void removeItem() {
        String fileName = "appliancesList.json";

        appliances.remove(appliancesItem);

        String comicsArrayListJSONString = new Gson().toJson(appliances);
        FileManager.writeToFile(_context, comicsArrayListJSONString, fileName);
        notifyDataSetChanged();
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    public ListViewAdapter(Context context, ArrayList<Appliances> appliances) {
        _context = context;
        this.appliances = appliances;
        this.inflater = LayoutInflater.from(context);
        entity = this;
    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {
        this.holder = holder;
        appliancesItem = appliances.get(position);
        holder.nameTextView.setText(appliancesItem.Name);
        holder.priceTextView.setText("$" + Double.toString(appliancesItem.Price));
        loadImageFromStorage(appliancesItem.Image, holder);
    }

    private void loadImageFromStorage(String path, @NonNull ListViewAdapter.ViewHolder holder)
    {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            holder.imageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return appliances == null ? 0 : appliances.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        public final TextView nameTextView, priceTextView;
        public final ImageView imageView;

        ViewHolder(View view){
            super(view);
            nameTextView = view.findViewById(R.id.nameTextFieldCard);
            priceTextView = view.findViewById(R.id.priceTextFieldCard);
            imageView = view.findViewById(R.id.imageViewCard);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "View");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1:
                        Intent intentForDetails = new Intent(_context, AppliancesDetailsActivity.class);
                        intentForDetails.putExtra("AppliancesItem", appliancesItem);
                        _context.startActivity(intentForDetails);
                        break;

                    case 2:
                        FragmentManager manager = ((AppCompatActivity) _context).getSupportFragmentManager();
                        DeleteConfirmDialog deleteConfirmDialog = new DeleteConfirmDialog();
                        deleteConfirmDialog.setListener(entity);
                        deleteConfirmDialog.appliancesItem = appliancesItem;
                        deleteConfirmDialog.show(manager, "deleteConfirmDialog");
                        break;
                }
                return true;
            }
        };
    }
}