package com.patel.akif.meat_o;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.patel.akif.meat_o.anim.DelayedPageScroller;
import com.patel.akif.meat_o.anim.InterpolatorEnum;
import com.patel.akif.meat_o.anim.ZoomOutPageTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.patel.akif.meat_o.aws.AwsDBHelper;
import com.patel.akif.meat_o.aws.AwsIDHelper;
import com.patel.akif.meat_o.controls.AlertDialog;
import com.patel.akif.meat_o.controls.WaitDialog;
import com.patel.akif.meat_o.dto.AppUser;
import com.patel.akif.meat_o.fragments.FragmentLogin;

public class ActivityLogin extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 1572;

    private GoogleApiClient googleApiClient;
    private GoogleSignInAccount gAccount;
    private ViewPager pager;
    private LoginScreenSlidePagerAdapter pagerAdapter;
    private WaitDialog waitDialog;

    public AppUser mAppUser;
    private int currentPage;
    private boolean userAlreadyRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        waitDialog = new WaitDialog(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.app_google_auth_code))
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        pager = (ViewPager) findViewById(R.id.login_fragment_pager);
        pagerAdapter = new LoginScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        pager.setCurrentItem(currentPage);

        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(pager, new DelayedPageScroller(
                    this,
                    InterpolatorEnum.values()[0].getInterpolator(),
                    700));
        }
        catch (Exception ex) {}

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            gAccount = result.getSignInAccount();
            try {
                new SearchUserTask().execute();
            }
            catch (Exception ex) {
                updateUI(false);
            }
        } else {
            updateUI(false);
        }
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            if(userAlreadyRegistered && mAppUser.getStatus().equals(com.patel.akif.meat_o.dto.Status.ACTIVE)) {
                startActivity(new Intent(this, ActivityHome.class));
                finish();
            }
            else
                new AddNewUserToIdentityPoolTask().execute();
        } else {
            waitDialog.dismiss();
        }
    }

    public void navigateToFragment(int page) {
        pager.setCurrentItem(page, true);
        currentPage = page;
    }

    public void saveNewUser() {
        new SaveNewUserTask().execute();
    }

    @Override
    public void onBackPressed() {
        currentPage -= 1;
        if(currentPage >= 0)
            navigateToFragment(currentPage);
        else
            super.onBackPressed();
    }

    private class LoginScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<FragmentLogin> mFragmentsList;
        public LoginScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);

            mFragmentsList = new ArrayList<>();
            mFragmentsList.add(FragmentLogin.create(0));
            mFragmentsList.add(FragmentLogin.create(1));
            mFragmentsList.add(FragmentLogin.create(2));
        }

        @Override
        public FragmentLogin getItem(int position) {
            return mFragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private class AddNewUserToIdentityPoolTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                AwsIDHelper.addNewUserToIdentityPool(getApplicationContext(), mAppUser);
            }
            catch (Exception e) {
                throw e;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            waitDialog.show(getString(R.string.message_wait_auth));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            waitDialog.dismiss();

            if(result) {
                navigateToFragment(1);
                pagerAdapter.getItem(1).setUserData(mAppUser);
            }
        }
    }

    private class SaveNewUserTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mAppUser.addUserToDB(getApplicationContext());
            }
            catch (Exception e) {
                throw e;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            waitDialog.show(getString(R.string.message_wait_register));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            waitDialog.dismiss();

            if(result){
                startActivity(
                        new Intent(ActivityLogin.this,
                                ActivityHome.class));
                finish();
            }
        }
    }

    private class SearchUserTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mAppUser = AppUser.getUserByID(getApplicationContext(), gAccount.getId());
            }
            catch (Exception e) {
                throw e;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            waitDialog.show(getString(R.string.message_wait_auth));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            waitDialog.dismiss();

            if(mAppUser != null)
                userAlreadyRegistered = true;
            else {
                mAppUser = new AppUser();
                mAppUser.setId(gAccount.getId());
                mAppUser.setEmail(gAccount.getEmail());
                mAppUser.setFirstName(gAccount.getGivenName());
                mAppUser.setLastName(gAccount.getFamilyName());
                mAppUser.setStatus(com.patel.akif.meat_o.dto.Status.PENDING_VERIFICATION);
            }
            updateUI(true);
        }
    }
}

