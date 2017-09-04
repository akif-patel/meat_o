package com.patel.akif.meat_o;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.patel.akif.meat_o.utils.Constants;
import com.patel.akif.meat_o.utils.FontsOverride;
import com.patel.akif.meat_o.utils.PreferenceHelper;

public class ActivityHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View buttonEditUser;
    int step = 0;
    TextView tvStep1, tvStep2, tvStep3;
    ImageView img1, img2, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FontsOverride.overrideFont(this, drawer);
        FontsOverride.overrideFont(this, navigationView.getHeaderView(0));
        FontsOverride.overrideFont(this, findViewById(R.id.activity_view_home));
        FontsOverride.setDefaultFont(this,
                getString(R.string.app_font_mono),
                getString(R.string.app_font_title));

/*        try {
            fonts = new ArrayList<String>(Arrays.asList(getAssets().list("")));
        } catch (Exception ex) {   }*/

        tvStep1 = (TextView) findViewById(R.id.home_text_order_stage_1);
        tvStep2 = (TextView) findViewById(R.id.home_text_order_stage_2);
        tvStep3 = (TextView) findViewById(R.id.home_text_order_stage_3);

        img1 = (ImageView) findViewById(R.id.home_image_order_stage_1);
        img2 = (ImageView) findViewById(R.id.home_image_order_stage_2);
        img3 = (ImageView) findViewById(R.id.home_image_order_stage_3);

        findViewById(R.id.home_button_place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProducts(Constants.PRODUCT_CODE_MTN);
                /*step += 1;

                switch (step) {
                    case 1: {
                        img1.setColorFilter(ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary));
                        tvStep1.setTextColor(ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary));
                        tvStep1.setText(getString(R.string.order_status_confirming));
                        break;
                    }
                    case 2: {
                        img2.setColorFilter(ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary));
                        tvStep2.setTextColor(ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary));
                        tvStep2.setText(getString(R.string.order_status_preparing));
                        tvStep1.setText(getString(R.string.order_status_confirmed));
                        findViewById(R.id.home_view_order_stage_1).
                            setBackgroundColor((ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary)));
                        break;
                    }
                    case 3: {
                        img3.setColorFilter(ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary));
                        tvStep3.setTextColor(ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary));
                        tvStep2.setText(getString(R.string.order_status_prepared));
                        tvStep3.setText(getString(R.string.order_status_delivering));
                        findViewById(R.id.home_view_order_stage_2).
                                setBackgroundColor((ContextCompat.getColor(ActivityHome.this, R.color.colorPrimary)));
                        break;
                    }
                    case 4: {
                        tvStep3.setText(getString(R.string.order_status_delivered));
                        break;
                    }
                }*/
            }
        });

        buttonEditUser = navigationView.getHeaderView(0)
                            .findViewById(R.id.nav_header_button_edit_user);
        loadPreferences(navigationView.getHeaderView(0));
        productClickHandler();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
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
        int id = item.getItemId();

        if (id == R.id.nav_action_basket) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void productClickHandler() {
        findViewById(R.id.home_view_products_goat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProducts(Constants.PRODUCT_CODE_MTN);
            }
        });

        findViewById(R.id.home_view_products_chicken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProducts(Constants.PRODUCT_CODE_CKN);
            }
        });

        findViewById(R.id.home_view_products_ingredients).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProducts(Constants.PRODUCT_CODE_ING);
            }
        });

        findViewById(R.id.home_view_products_spices).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProducts(Constants.PRODUCT_CODE_SPC);
            }
        });

        findViewById(R.id.home_view_products_desserts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProducts(Constants.PRODUCT_CODE_DST);
            }
        });

        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityProfile.class));
            }
        });
    }

    private void navigateToProducts(String productCode) {
        Intent intent = new Intent(ActivityHome.this, ActivityProducts.class);
        intent.putExtra(Constants.INTENT_PRODUCT_CODE, productCode);
        startActivity(intent);
    }

    private void loadPreferences(View userInfoView) {
        if(PreferenceHelper.getUserLoggedOff())
            return;

        ((TextView) userInfoView.findViewById(R.id.nav_header_text_user_name)).setText(
                PreferenceHelper.getLoggedInUserName()
        );
        ((TextView) userInfoView.findViewById(R.id.nav_header_text_location)).setText(
                PreferenceHelper.getDefaultLocationName() + ", " +
                PreferenceHelper.getDefaultLocationCity()
        );
        ((TextView) userInfoView.findViewById(R.id.nav_header_text_zip)).setText(
                Constants.STRING_PIN + PreferenceHelper.getDefaultLocationPin()
        );
    }
}
