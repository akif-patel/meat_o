package com.patel.akif.meat_o;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.patel.akif.meat_o.adapters.AdapterProductItems;
import com.patel.akif.meat_o.controls.WaitDialog;
import com.patel.akif.meat_o.dto.Item;
import com.patel.akif.meat_o.fragments.FragmentLogin;
import com.patel.akif.meat_o.fragments.FragmentProductTab;
import com.patel.akif.meat_o.utils.CommonUtils;
import com.patel.akif.meat_o.utils.Constants;
import com.patel.akif.meat_o.utils.FontsOverride;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityProducts extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private FragmentStatePagerAdapter pagerAdapter;
    private WaitDialog waitDialog;
    private FragmentProductTab currentFragment;

    private String[] productCodes;
    private List<Item> productItems, goatItems, chickenItems, ingredientsItems, spicesItems, dessertItems;
    private int tabPosition = 0;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        waitDialog = new WaitDialog(this);
        CommonUtils.setActivityTitle(toolbar,
                getString(R.string.label_products),
                getString(R.string.label_products_slogan));

        productCodes = getResources().getStringArray(R.array.product_codes);
        pagerAdapter = new ProductsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.products_pager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.products_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        FontsOverride.overrideFont(this, findViewById(R.id.activity_products));
    }

    @Override
    protected void onStart() {
        super.onStart();

        switch (getIntent().getStringExtra(Constants.INTENT_PRODUCT_CODE)) {
            case Constants.PRODUCT_CODE_MTN: {
                tabPosition = 0;
                break;
            }
            case Constants.PRODUCT_CODE_CKN: {
                tabPosition = 1;
                break;
            }
            case Constants.PRODUCT_CODE_ING: {
                tabPosition = 2;
                break;
            }
            case Constants.PRODUCT_CODE_SPC: {
                tabPosition = 3;
                break;
            }
            case Constants.PRODUCT_CODE_DST: {
                tabPosition = 4;
                break;
            }
        }

        navigateToTab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        menu.getItem(0).setActionView(R.layout.view_action_basket);
        FontsOverride.overrideFont(this, menu.getItem(0).getActionView());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabPosition = tab.getPosition();
        navigateToTab();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void navigateToTab() {
        viewPager.setCurrentItem(tabPosition);

        currentFragment = (FragmentProductTab) pagerAdapter.instantiateItem
                (viewPager, tabPosition);

        if(currentFragment.isTabEmpty()) {
            switch (tabPosition) {
                case 0: {
                    productItems = goatItems;
                    break;
                }

                case 1: {
                    productItems = chickenItems;
                    break;
                }

                case 2: {
                    productItems = ingredientsItems;
                    break;
                }

                case 3: {
                    productItems = spicesItems;
                    break;
                }

                case 4: {
                    productItems = dessertItems;
                    break;
                }
            }

            if(productItems != null && productItems.size() > 0) {
                ArrayList<Item> itemList = new ArrayList<Item>(productItems);

                Collections.sort(itemList, new Comparator<Item>() {
                    public int compare(Item o1, Item o2) {
                        return Integer.valueOf(o1.getSortOrder()).compareTo(Integer.valueOf(o2.getSortOrder()));
                    }
                });
                currentFragment.loadProductItems(itemList);
            }
            else
                new GetProductItemsTask().execute(productCodes[tabPosition]);
        }
    }

    private class ProductsPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> productList;
        private FragmentProductTab tabFragment;

        public ProductsPagerAdapter(FragmentManager fm) {
            super(fm);

            productList = new ArrayList<>();
            productList.add(getString(R.string.label_products_goat));
            productList.add(getString(R.string.label_products_chicken));
            productList.add(getString(R.string.label_products_ingredients));
            productList.add(getString(R.string.label_products_spices));
            productList.add(getString(R.string.label_products_dessert));
        }

        @Override
        public FragmentProductTab getItem(int position) {
            tabFragment = new FragmentProductTab();

            return tabFragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return productList.get(position);
        }
    }

    private class GetProductItemsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                productItems = Item.getItemsByProduct(ActivityProducts.this, params[0]);
            }
            catch (Exception e) {
                throw e;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            waitDialog.show(getString(R.string.message_wait_fetching_products));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            switch (tabPosition) {
                case 0: {
                    goatItems = productItems;
                    break;
                }

                case 1: {
                    chickenItems = productItems;
                    break;
                }

                case 2: {
                    ingredientsItems = productItems;
                    break;
                }

                case 3: {
                    spicesItems = productItems;
                    break;
                }

                case 4: {
                    dessertItems = productItems;
                    break;
                }
            }

            ArrayList<Item> itemList = new ArrayList<Item>(productItems);

            Collections.sort(itemList, new Comparator<Item>() {
                public int compare(Item o1, Item o2) {
                    return Integer.valueOf(o1.getSortOrder()).compareTo(Integer.valueOf(o2.getSortOrder()));
                }
            });

            currentFragment.loadProductItems(itemList);
            waitDialog.dismiss();
        }
    }
}
