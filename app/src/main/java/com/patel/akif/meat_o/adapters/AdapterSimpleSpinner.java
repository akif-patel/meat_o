package com.patel.akif.meat_o.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.utils.FontsOverride;

import java.util.List;

/**
 * Created by akif_p on 23/08/2017.
 */

public class AdapterSimpleSpinner extends BaseAdapter {
    private Context context;
    private List<String> items;

    public AdapterSimpleSpinner(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView itemText;
        LayoutInflater inflater = (LayoutInflater)
                                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.view_spinner_item_quantity, null, true);

        itemText = (TextView) rowView.findViewById(R.id.simple_spinner_text_quantity);
        itemText.setText(items.get(position));

        FontsOverride.overrideFont(context, rowView);

        return rowView;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public String getItem(int ind) {
        return this.items.get(ind);
    }

    @Override
    public long getItemId(int ind) {
        return ind;
    }
}
