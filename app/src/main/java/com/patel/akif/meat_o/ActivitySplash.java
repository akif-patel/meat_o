package com.patel.akif.meat_o;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.patel.akif.meat_o.utils.FontsOverride;
import com.patel.akif.meat_o.utils.PreferenceHelper;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {
    private boolean isUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        PreferenceHelper.preparePreferences(this);
        isUserLoggedIn = PreferenceHelper.getUserLoggedIn();

        FontsOverride.overrideFont(this, findViewById(R.id.activity_splash));
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(isUserLoggedIn)
                    startActivity(new Intent(ActivitySplash.this, ActivityHome.class));
                else
                    startActivity(new Intent(ActivitySplash.this, ActivityLogin.class));

                finish();
            }
        }, 2000);
    }
}
