package com.patel.akif.meat_o.utils;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.dto.SaleUnit;

/**
 * Created by akif_p on 18/08/2017.
 */

public class CommonUtils {
    public static void setActivityTitle(Toolbar toolbar, String title, String slogan) {
        toolbar.setContentInsetStartWithNavigation(0);

        ((TextView) toolbar.findViewById(R.id.toolbar_text_app_name)).setText(title);

        if(slogan != null)
            ((TextView) toolbar.findViewById(R.id.toolbar_text_app_slogan)).setText(slogan);
    }

    public static float calculateAmountByQuantity(
            float rate,
            float rateQuantity,
            float quantity) {
        float amount = 0;

        amount = (quantity / rateQuantity) * rate;

        return amount;
    }

    public static void showKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager)
                                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
    }
}
