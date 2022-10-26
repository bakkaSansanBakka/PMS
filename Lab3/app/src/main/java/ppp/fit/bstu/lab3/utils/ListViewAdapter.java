package ppp.fit.bstu.lab3.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ppp.fit.bstu.lab3.*;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Comics> comics;
    private ItemClickListener clickListener;

    public ListViewAdapter(Context context, ArrayList<Comics> comics) {
        this.comics = comics;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {
        Comics comicsItem = comics.get(position);
        holder.nameTextView.setText(comicsItem.Name);
        holder.priceTextView.setText("$" + Double.toString(comicsItem.Price));
    }

    @Override
    public int getItemCount() {
        return comics == null ? 0 : comics.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameTextView, priceTextView;
        ViewHolder(View view){
            super(view);
            nameTextView = view.findViewById(R.id.nameTextFieldCard);
            priceTextView = view.findViewById(R.id.priceTextFieldCard);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}
