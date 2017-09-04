package com.patel.akif.meat_o.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.controls.AddItemToBasketDialog;
import com.patel.akif.meat_o.dto.Item;
import com.patel.akif.meat_o.utils.Constants;
import com.patel.akif.meat_o.utils.FontsOverride;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akif_p on 19/08/2017.
 */

public class AdapterProductItems extends ArrayAdapter<Item> {
    private List<Item> items;
    private Item item;
    private String price, imageURL;

    private Activity context;
    private TextView textItemName, textItemDesc, textItemPrice, textItemRate, textItemBrand;
    private ImageView imageProduct;

    public AdapterProductItems(Activity context, List<Item> items) {
        super(context, R.layout.view_product_item, items);

        this.context = context;
        this.items = items;
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.view_product_item, null, true);

        imageProduct = (ImageView) rowView.findViewById(R.id.product_item_image);
        textItemName = (TextView) rowView.findViewById(R.id.product_item_text_name);
        textItemDesc = (TextView) rowView.findViewById(R.id.product_item_text_desc);
        textItemPrice = (TextView) rowView.findViewById(R.id.product_item_text_price);
        textItemRate = (TextView) rowView.findViewById(R.id.product_item_text_rate);
        textItemBrand = (TextView) rowView.findViewById(R.id.product_item_text_brand);

        item = items.get(position);
        price = String.valueOf(item.getRate());
        imageURL = Constants.S3_OBJECT_URL + item.getCode() + ".jpg";

        if(item.getProductCode().equals(Constants.PRODUCT_CODE_SPC)
                || item.getProductCode().equals(Constants.PRODUCT_CODE_DST))
            textItemBrand.setText(item.getBrand());
        textItemName.setText(item.getName());
        textItemDesc.setText(item.getDescription());
        textItemPrice.setText("Rs. " + price + "/-");
        textItemRate.setText("Rate (" +
                String.valueOf(item.getRateQuantity()) + " " +
                item.getSaleUnit().toString() + ")");
        Picasso.with(context)
                .load(imageURL)
                .into(imageProduct);

        rowView.findViewById(R.id.product_item_button_add)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddToBasket(position);
                }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToBasket(position);
            }
        });
        FontsOverride.overrideFont(context, rowView);

        return rowView;
    }

    private void showAddToBasket(int position) {
        item = getItem(position);
        AddItemToBasketDialog addToBasket = new AddItemToBasketDialog(context);
        addToBasket.bindDialog(item);
        addToBasket.show();
    }
}
