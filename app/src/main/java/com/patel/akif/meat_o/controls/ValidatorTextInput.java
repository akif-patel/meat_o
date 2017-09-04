package com.patel.akif.meat_o.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patel.akif.meat_o.R;

/**
 * Created by akif_p on 24/05/2017.
 */

public class ValidatorTextInput extends LinearLayout {
    private EditText mEditText;
    private TextView mErrorText;
    private ImageView mErrorImage;
    private TextInputLayout mInputLayout;

    private TypedArray mAttrArray;
    private int mColorWhite;

    private int validTint;
    private int invalidTint;
    private int maxLines;
    private int inputType;
    private Boolean useLightTheme;
    private Boolean showValidator;
    private Boolean readOnly;
    private String hint;
    private String defaultErrorMsg;

    public ValidatorTextInput(Context context) {
        super(context);
        initializeViews(context);
    }

    public ValidatorTextInput(Context context,
                                AttributeSet attrs) {
        super(context, attrs);

        mAttrArray = context.obtainStyledAttributes(
                attrs, R.styleable.ValidatorTextInput);
        setAttributes();

        initializeViews(context);
    }

    public ValidatorTextInput(Context context,
                                AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);

        mAttrArray = context.obtainStyledAttributes(
                attrs, R.styleable.ValidatorTextInput);
        setAttributes();

        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(useLightTheme)
            inflater.inflate(R.layout.view_text_input_light, this);
        else
            inflater.inflate(R.layout.view_text_input, this);

        this.setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(useLightTheme)
            mColorWhite = ContextCompat.getColor(this.getContext(), R.color.colorWhite);

        mInputLayout = (TextInputLayout) this.findViewById(R.id.input_view_text_layout);
        mEditText = (EditText) this.findViewById(R.id.input_view_edit_text);
        mErrorText = (TextView) this.findViewById(R.id.input_view_text_error);
        mErrorImage = (ImageView) this.findViewById(R.id.input_view_image_error);
        mErrorImage.setVisibility(View.INVISIBLE);

        if(hint != null && !hint.isEmpty())
            mInputLayout.setHint(hint);

        if(!showValidator)
            this.findViewById(R.id.input_view_validator).setVisibility(GONE);

        if(defaultErrorMsg != null)
            this.setInvalid(defaultErrorMsg);

        if(readOnly)
            mInputLayout.setEnabled(false);

        mEditText.setLines(maxLines);
        mEditText.setInputType(inputType);
    }

    private void setAttributes() {
        this.validTint = mAttrArray.getInt(
                R.styleable.ValidatorTextInput_validTint,
                ContextCompat.getColor(this.getContext(), R.color.colorValidText));
        this.invalidTint = mAttrArray.getInt(
                R.styleable.ValidatorTextInput_invalidTint,
                ContextCompat.getColor(this.getContext(), R.color.colorInvalidText));
        this.maxLines = mAttrArray.getInt(
                R.styleable.ValidatorTextInput_maxLines, 1);
        this.useLightTheme = mAttrArray.getBoolean(
                R.styleable.ValidatorTextInput_useLightTheme, false);
        this.readOnly = mAttrArray.getBoolean(
                R.styleable.ValidatorTextInput_readOnly, false);
        this.showValidator = mAttrArray.getBoolean(
                R.styleable.ValidatorTextInput_showValidator, true);
        this.hint = mAttrArray.getString(
                R.styleable.ValidatorTextInput_hint);
        this.defaultErrorMsg = mAttrArray.getString(
                R.styleable.ValidatorTextInput_defaultErrorMsg);
        this.inputType = mAttrArray.getInt(
                R.styleable.ValidatorTextInput_inputType, 1);

        mAttrArray.recycle();
    }

    public void setValidTint(int mValidTint) {
        this.validTint = mValidTint;
    }

    public void setInvalidTint(int mInvalidTint) {
        this.invalidTint = mInvalidTint;
    }

    public void setHint(String mHint) {
        if(mHint == null)
            mHint = "";

        this.hint = mHint;
    }

    public void setUseLightTheme(Boolean mUseLightTheme) {
        this.useLightTheme = mUseLightTheme;
    }

    public void setShowValidator(Boolean mShowValidator) {
        this.showValidator = mShowValidator;
    }

    public void setDefaultErrorMsg(String defaultErrorMsg) {
        this.defaultErrorMsg = defaultErrorMsg;
    }

    public void setValid() {
        this.setValid("");
    }

    public void setValid(String msg) {
        mErrorText.setText(msg);
        mErrorImage.setVisibility(View.VISIBLE);
        mErrorImage.setImageResource(R.mipmap.ic_check_circle_black_48dp);

        if(useLightTheme)
            mErrorImage.setColorFilter(mColorWhite);
        else {
            mErrorImage.setColorFilter(validTint);
            mErrorText.setTextColor(validTint);
        }
    }

    public void setInvalid(String msg) {
        mErrorText.setText(msg);
        mErrorImage.setVisibility(View.VISIBLE);
        mErrorImage.setImageResource(R.mipmap.ic_error_black_48dp);

        if(useLightTheme)
            mErrorImage.setColorFilter(mColorWhite);
        else {
            mErrorImage.setColorFilter(invalidTint);
            mErrorText.setTextColor(invalidTint);
        }
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        mInputLayout.setEnabled(!readOnly);
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getText() {
        if (mEditText.getText() == null)
            return "";
        else
            return mEditText.getText().toString();
    }

    public void setText(String text) {
        mEditText.setText(text);
    }

    public boolean isEmpty() {
        if(this.getText().isEmpty())
            return true;
        else
            return false;
    }

    public void selectAll() {
        mEditText.requestFocus();
        mEditText.selectAll();
    }

    public void setFocus() {
        mEditText.requestFocus();
    }
}
