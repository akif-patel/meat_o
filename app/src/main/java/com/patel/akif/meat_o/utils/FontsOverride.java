package com.patel.akif.meat_o.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.patel.akif.meat_o.R;
import java.lang.reflect.Field;

public final class FontsOverride {

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);

            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void overrideFont(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    FontsOverride.overrideFont(context, child);
                }

                if (v instanceof TextInputLayout)
                    ((TextInputLayout) v).setTypeface(
                            Typeface.createFromAsset(
                                    context.getAssets(),
                                    context.getString(R.string.app_font)));
            }
            else if (v instanceof RadioButton)
                ((RadioButton) v).setTypeface(
                        Typeface.createFromAsset(
                                context.getAssets(),
                                context.getString(R.string.app_font)));
            else if (v instanceof Button)
                ((Button) v).setTypeface(
                        Typeface.createFromAsset(
                                context.getAssets(),
                                context.getString(R.string.app_font)));
            else if (v instanceof TextView || v instanceof EditText || v instanceof AutoCompleteTextView) {
                if (v.getTag() == context.getString(R.string.tag_title))
                    ((TextView) v).setTypeface(
                            Typeface.createFromAsset(
                                context.getAssets(),
                                context.getString(R.string.app_font_header)));
                else
                    ((TextView) v).setTypeface(
                            Typeface.createFromAsset(
                                    context.getAssets(),
                                    context.getString(R.string.app_font)));
            }
        }
        catch (Exception e) { }
    }

    public static void overrideFont(final Context context, final View v, Typeface tf) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    FontsOverride.overrideFont(context, child, tf);
                }
            }
            else if (v instanceof RadioButton)
                ((RadioButton) v).setTypeface(tf);
            else if (v instanceof Button)
                ((Button) v).setTypeface(tf);
            else if (v instanceof TextView || v instanceof EditText || v instanceof AutoCompleteTextView) {
                if (v.getTag() == context.getString(R.string.tag_title))
                    ((TextView) v).setTypeface(tf, Typeface.BOLD);
                else
                    ((TextView) v).setTypeface(tf);
            }
        }
        catch (Exception e) { }
    }

    public static void applyTitleFont(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    FontsOverride.applyTitleFont(context, child);
                }

                /*if (v instanceof TextInputLayout)
                    ((TextInputLayout) v).setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.app_font)));*/
            }
            else if (v instanceof RadioButton)
                ((RadioButton) v).setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.app_font_title)), Typeface.BOLD);
            else if (v instanceof Button)
                ((Button) v).setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.app_font_title)), Typeface.BOLD);
            else if (v instanceof TextView || v instanceof EditText || v instanceof AutoCompleteTextView)
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.app_font_title)), Typeface.BOLD);
        }
        catch (Exception e) { }
    }
}
