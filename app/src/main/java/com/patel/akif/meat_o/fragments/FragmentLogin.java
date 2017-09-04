package com.patel.akif.meat_o.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.patel.akif.meat_o.ActivityLogin;
import com.patel.akif.meat_o.R;
import com.patel.akif.meat_o.controls.AlertDialog;
import com.patel.akif.meat_o.controls.ValidatorTextInput;
import com.patel.akif.meat_o.dto.AppUser;
import com.patel.akif.meat_o.dto.DeliveryAddress;
import com.patel.akif.meat_o.dto.Status;
import com.patel.akif.meat_o.utils.FontsOverride;
import com.patel.akif.meat_o.utils.InputValidator;

public class FragmentLogin extends Fragment {
    public static final String ARG_PAGE = "page";

    private ValidatorTextInput textFirstName, textLastName, textEmail, textPhone;
    private ValidatorTextInput textAddressLine1, textAddressLine2, textLandmark,
            textPinCode, textState, textCity;
    private View buttonAction;

    private int pageNumber;
    private boolean verifiedMobileNumber;

    private ActivityLogin activityLogin;
    private AlertDialog alertDialog;

    public static FragmentLogin create(int pageNumber) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public FragmentLogin() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            pageNumber = getArguments().getInt(ARG_PAGE);

        activityLogin = (ActivityLogin) getActivity();

        alertDialog = new AlertDialog(
                getContext(),
                getString(R.string.alert_login_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View pageView = getViewForPage(inflater, container);

        InputMethodManager imm = (InputMethodManager)
                activityLogin.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(pageView.getWindowToken(), 0);

        return pageView;
    }

    private View getViewForPage(LayoutInflater inflater,
                                 ViewGroup container) {
        ViewGroup rootView = null;

        switch (pageNumber) {
            case 0: {
                rootView = (ViewGroup) inflater
                        .inflate(R.layout.fragment_login, container, false);

                buttonAction = rootView.findViewById(R.id.login_button_login);
                buttonAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activityLogin.signIn();
                            }
                });
                break;
            }

            case 1: {
                rootView = (ViewGroup) inflater
                        .inflate(R.layout.fragment_login_verify, container, false);

                rootView.findViewById(R.id.login_verify_button_verify_phone)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                verifyUserMobileNumber();
                            }
                });

                buttonAction = rootView.findViewById(R.id.login_verify_button_add_address);
                buttonAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (verifiedMobileNumber) {
                                    activityLogin.mAppUser.setPhoneNumber(textPhone.getText());
                                    activityLogin.mAppUser.setStatus(Status.ACTIVE);
                                    activityLogin.navigateToFragment(2);
                                }
                                else
                                    alertDialog.show(
                                            getString(R.string.alert_login_phone_not_verified));
                            }
                        });

                textFirstName = (ValidatorTextInput) rootView
                        .findViewById(R.id.login_verify_text_first_name);
                textLastName = (ValidatorTextInput) rootView
                        .findViewById(R.id.login_verify_text_last_name);
                textEmail = (ValidatorTextInput) rootView
                        .findViewById(R.id.login_verify_text_email_address);
                textPhone = (ValidatorTextInput) rootView
                        .findViewById(R.id.login_verify_text_phone);
                break;
            }

            case 2: {
                rootView = (ViewGroup) inflater
                        .inflate(R.layout.fragment_add_address, container, false);

                buttonAction = rootView.findViewById(R.id.address_button_finish);
                buttonAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            if(addAddress())
                                activityLogin.saveNewUser();
                            }
                        });

                textAddressLine1 = (ValidatorTextInput) rootView
                        .findViewById(R.id.add_address_text_line_1);
                textAddressLine2 = (ValidatorTextInput) rootView
                        .findViewById(R.id.add_address_text_line_2);
                textLandmark = (ValidatorTextInput) rootView
                        .findViewById(R.id.add_address_text_landmark);
                textPinCode = (ValidatorTextInput) rootView
                        .findViewById(R.id.add_address_text_pincode);
                textState = (ValidatorTextInput) rootView
                        .findViewById(R.id.add_address_text_state);
                textCity = (ValidatorTextInput) rootView
                        .findViewById(R.id.add_address_text_city);

                textState.setText(getString(R.string.default_state));
                textCity.setText(getString(R.string.default_city));

                break;
            }
        }

        FontsOverride.overrideFont(this.getContext(), rootView);

        return rootView;
    }

    public void setUserData(AppUser appUser) {
        textFirstName.setText(appUser.getFirstName());
        textLastName.setText(appUser.getLastName());
        textEmail.setText(appUser.getEmail());
        textPhone.setText(appUser.getPhoneNumber());
    }

    private void verifyUserMobileNumber() {
        if(textPhone.getText().equals("") || !verifyMobileNumber()) {
            textPhone.setInvalid(getString(R.string.message_login_invalid_phone));
            verifiedMobileNumber = false;

            alertDialog.show(getString(R.string.alert_login_invalid_phone));
        }
        else {
            verifiedMobileNumber = true;
            //TO-DO OTP Verification
        }
    }

    private boolean verifyMobileNumber() {
        return InputValidator.isValidIndiaMobileNumber(textPhone.getText());
    }

    private boolean addAddress() {
        DeliveryAddress address = new DeliveryAddress();
        String add = textAddressLine1.getText().concat(textAddressLine2.getText()).trim();
        String pin = textPinCode.getText().trim();

        if (add.length() < 15 ||
                (textAddressLine1.isEmpty() || textAddressLine2.isEmpty())) {
            alertDialog.show(
                    getString(R.string.alert_address_short_address));
            return false;
        }
        else if (!InputValidator.isValidPinCode(pin)) {
            alertDialog.show(
                    getString(R.string.alert_address_invalid_pin));
            return false;
        }

        address.setAddressLine1(textAddressLine1.getText());
        address.setAddressLine2(textAddressLine2.getText());
        address.setLandmark(textLandmark.getText());
        address.setPincode(textPinCode.getText());

        activityLogin.mAppUser.addAddress(address);

        return true;
    }
}
