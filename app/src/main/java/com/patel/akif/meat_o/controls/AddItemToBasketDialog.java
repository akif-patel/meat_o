package com.patel.akif.meat_o.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.adapters.AdapterSimpleSpinner;
import com.patel.akif.meat_o.dto.Item;
import com.patel.akif.meat_o.utils.CommonUtils;
import com.patel.akif.meat_o.utils.Constants;
import com.patel.akif.meat_o.utils.FontsOverride;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by akif_p on 20/06/2017.
 */

public class AddItemToBasketDialog {
    private TextView textItemName, textItemPrice, textItemRate, textItemBrand, textItemSaleUnit, textAmount;
    private View buttonClose, buttonAdd, viewBrand;
    private Spinner spinnerQuantity;
    private Context context;

    private Item item;
    private List<String> quantityList;

    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog dialog;

    public AddItemToBasketDialog(Context context) {
        if(builder != null && dialog != null)
            return;

        this.context = context;
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.view_dialog_add_to_basket, null);

        builder = new android.app.AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MeatO_Anim_Alert_UpRight;

        textItemName = (TextView) dialogView.findViewById(R.id.basket_text_item_name);
        textItemPrice = (TextView) dialogView.findViewById(R.id.basket_text_item_price);
        textItemRate = (TextView) dialogView.findViewById(R.id.basket_text_item_rate);
        textItemBrand = (TextView) dialogView.findViewById(R.id.basket_text_item_brand);
        textItemSaleUnit = (TextView) dialogView.findViewById(R.id.basket_text_item_sale_unit);
        textAmount = (TextView) dialogView.findViewById(R.id.basket_text_total_amount);
        spinnerQuantity = (Spinner) dialogView.findViewById(R.id.basket_spinner_item_quantity);
        viewBrand = dialogView.findViewById(R.id.basket_view_item_brand);
        buttonClose = dialogView.findViewById(R.id.basket_button_cancel);
        buttonAdd = dialogView.findViewById(R.id.basket_button_add);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        FontsOverride.overrideFont(context, dialogView);
    }

    public void bindDialog(Item it) {
        this.item = it;
        final String price = String.valueOf(item.getRate());

        if(item.getProductCode().equals(Constants.PRODUCT_CODE_SPC)
                || item.getProductCode().equals(Constants.PRODUCT_CODE_DST)) {
            textItemBrand.setText(item.getBrand());
            viewBrand.setVisibility(View.VISIBLE);
        }

        textItemName.setText(item.getName());
        textItemPrice.setText("Rs. " + price + "/-");
        textItemRate.setText("Rate (" +
                String.valueOf(item.getRateQuantity()) + " " +
                item.getSaleUnit().toString() + ")");
        textItemSaleUnit.setText(item.getSaleUnit().toString());

        loadQuantityList();
        AdapterSimpleSpinner adapter = new AdapterSimpleSpinner(context, quantityList);
        spinnerQuantity.setAdapter(adapter);

        spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                float amount = CommonUtils.calculateAmountByQuantity(
                        item.getRate(),
                        item.getRateQuantity(),
                        Float.valueOf(quantityList.get(position))
                );
                textAmount.setText("Rs. " + String.valueOf(amount) + "/-");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerQuantity.setSelection(quantityList.indexOf("1"));
    }

    private void loadQuantityList() {
        quantityList = new ArrayList<>();
        if(item.getQuantityCode() == 0)
            quantityList = Arrays.asList(
                    context.getResources().getStringArray(R.array.numeric_order_quantities)
            );
        else if(item.getQuantityCode() == 1)
            quantityList = Arrays.asList(
                    context.getResources().getStringArray(R.array.mutton_order_quantities)
            );
        else if(item.getQuantityCode() == 2)
            quantityList = Arrays.asList(
                    context.getResources().getStringArray(R.array.chicken_order_quantities)
            );
        else if(item.getQuantityCode() == 3)
            quantityList = Arrays.asList(
                    context.getResources().getStringArray(R.array.gram_order_quantities)
            );
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
