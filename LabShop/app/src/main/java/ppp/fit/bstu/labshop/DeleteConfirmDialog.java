package ppp.fit.bstu.labshop;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.util.ArrayList;

import ppp.fit.bstu.labshop.Models.Appliances;
import ppp.fit.bstu.labshop.Utils.FileManager;

public class DeleteConfirmDialog extends DialogFragment {
    public Listener listener;

    public interface Listener {
        void onDelete();
    }
    public Appliances appliancesItem;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDelete();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                });
        return builder.create();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}