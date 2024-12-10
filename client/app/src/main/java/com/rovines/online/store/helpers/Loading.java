package com.rovines.online.store.helpers;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rovines.online.store.R;

public class Loading {
    private Context context;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private AlertDialog alertDialog;

    public Loading(Context context) {
        this.context = context;
        initialize();
    }

    private void initialize() {
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setView(R.layout.loading);
        alertDialog = materialAlertDialogBuilder.create();
    }

    public void show() {
        alertDialog.show();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }
}
