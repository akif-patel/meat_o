package com.patel.akif.meat_o.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.utils.FontsOverride;

/**
 * Created by akif_p on 20/06/2017.
 */

public class AlertDialog {
    private TextView textTitle, textMessage;
    private View buttonClose;

    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog dialog;

    public AlertDialog(Context context) {
        if(builder != null && dialog != null)
            return;

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.view_dialog_alert, null);
        FontsOverride.overrideFont(context, dialogView);

        builder = new android.app.AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MeatO_Anim_Alert_UpRight;

        textTitle = (TextView) dialogView.findViewById(R.id.alert_text_title);
        textMessage = (TextView) dialogView.findViewById(R.id.alert_text_message);
        buttonClose = dialogView.findViewById(R.id.alert_button_close);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public AlertDialog(Context context, String title) {
        this(context);

        textTitle.setText(title);
    }

    public AlertDialog(Context context, String title, String message) {
        this(context);

        textTitle.setText(title);
        textMessage.setText(message);
    }

    public void setTitle(String title) {
        textTitle.setText(title);
    }

    public void setMessage(String message) {
        textMessage.setText(message);
    }

    public void show() {
        dialog.show();
    }

    public void show(String message) {
        this.setMessage(message);
        dialog.show();
    }

    public void show(String title, String message) {
        this.setMessage(message);
        this.setTitle(title);
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
