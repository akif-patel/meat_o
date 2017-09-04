package com.patel.akif.meat_o.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.patel.akif.meat_o.ActivityProducts;
import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.adapters.AdapterProductItems;
import com.patel.akif.meat_o.controls.WaitDialog;
import com.patel.akif.meat_o.dto.Item;
import com.patel.akif.meat_o.utils.FontsOverride;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akif_p on 18/08/2017.
 */

public class FragmentProductTab extends Fragment {
    private ListView lvProductItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tabView = inflater.inflate(R.layout.fragment_activity_products, container, false);

        lvProductItems = (ListView) tabView.findViewById(R.id.products_items_list);

        return tabView;
    }

    public void loadProductItems(List<Item> productItems) {
        AdapterProductItems adapter = new AdapterProductItems(
                FragmentProductTab.this.getActivity(), productItems);
        lvProductItems.setAdapter(adapter);
    }

    public boolean isTabEmpty() {
        if(lvProductItems == null)
            return true;
        else if (lvProductItems.getAdapter() == null)
            return true;

        return false;
    }
}
