package com.patel.akif.meat_o;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.patel.akif.meat_o.controls.ValidatorTextInput;
import com.patel.akif.meat_o.controls.WaitDialog;
import com.patel.akif.meat_o.dto.AppUser;
import com.patel.akif.meat_o.utils.CommonUtils;
import com.patel.akif.meat_o.utils.FontsOverride;
import com.patel.akif.meat_o.utils.PreferenceHelper;

public class ActivityProfile extends AppCompatActivity {
    private WaitDialog waitDialog;
    private ValidatorTextInput textFirstName, textLastName, textEmail, textPhone;

    private AppUser appUser;
    private boolean isVerifiedMobile = true;
    private String verifiedMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        waitDialog = new WaitDialog(this);
        CommonUtils.setActivityTitle(toolbar,
                getString(R.string.title_activity_profile),
                getString(R.string.title_activity_profile_slogan));

        textFirstName = (ValidatorTextInput) findViewById(R.id.profile_text_first_name);
        textLastName = (ValidatorTextInput) findViewById(R.id.profile_text_last_name);
        textEmail = (ValidatorTextInput) findViewById(R.id.profile_text_email_address);
        textPhone = (ValidatorTextInput) findViewById(R.id.profile_text_phone);

        handleEvents();
        FontsOverride.overrideFont(this, findViewById(R.id.content_activity_profile));
    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetUserTask().execute();
    }

    private void loadUserInfo() {
        textFirstName.setText(appUser.getFirstName());
        textLastName.setText(appUser.getLastName());
        textEmail.setText(appUser.getEmail());
        textPhone.setText(appUser.getPhoneNumber());

        textPhone.setValid(getString(R.string.message_login_verified_phone));
    }

    private void handleEvents() {
        findViewById(R.id.profile_button_verify_phone)
            .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = ((TextView) v).getText().toString();
                if(buttonText.equals(getString(R.string.button_profile_change))) {
                    verifiedMobileNumber = textPhone.getText();
                    ((TextView) v).setText(R.string.button_login_verify);

                    textPhone.setReadOnly(false);
                    textPhone.setFocus();
                    textPhone.selectAll();

                    CommonUtils.showKeyBoard(getBaseContext());
                }
                else if(buttonText.equals(getString(R.string.button_login_verify))) {
                    verifiedMobileNumber = textPhone.getText();
                    ((TextView) v).setText(R.string.button_profile_change);

                    textPhone.setReadOnly(true);

                    CommonUtils.hideKeyBoard(getBaseContext());
                }
            }
        });

        findViewById(R.id.profile_button_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityProfile.super.onBackPressed();
                    }
                });
    }

    private class GetUserTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                appUser = AppUser.getUserByID(ActivityProfile.this,
                        PreferenceHelper.getLoggedInUserID());
            }
            catch (Exception e) {
                throw e;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            waitDialog.show(getString(R.string.message_wait_loading));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            loadUserInfo();
            waitDialog.dismiss();
        }
    }
}
