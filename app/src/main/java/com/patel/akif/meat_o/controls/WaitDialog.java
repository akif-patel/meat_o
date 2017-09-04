package com.patel.akif.meat_o.controls;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.utils.FontsOverride;

/**
 * Created by akif_p on 19/06/2017.
 */

public class WaitDialog {
    private ProgressDialog progressDialog;
    private TextView textMessage;
    private Context context;

    public WaitDialog(Context context) {
        this.context = context;
    }

    public void show(String message) {
        if(progressDialog != null && progressDialog.isShowing()) {
            textMessage.setText(message);
            return;
        }

        progressDialog = ProgressDialog.show(context, message, message, false);
        progressDialog.setContentView(R.layout.view_dialog_wait);
        progressDialog.getWindow().setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        textMessage = (TextView) progressDialog.findViewById(R.id.wait_text_message);
        textMessage.setText(message);
        FontsOverride.overrideFont(context, textMessage);
    }

    public void dismiss() {
        progressDialog.dismiss();
    }
}
