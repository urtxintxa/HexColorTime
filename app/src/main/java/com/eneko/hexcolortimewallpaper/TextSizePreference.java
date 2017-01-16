package com.eneko.hexcolortimewallpaper;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class TextSizePreference extends DialogPreference{

    protected static int seekBarValue;

    public TextSizePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setDialogLayoutResource(R.layout.text_size);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if(restoreValue) {
            this.seekBarValue = getPersistedInt(seekBarValue);
        }
        else {
            this.seekBarValue = (Integer) defaultValue;
        }
    }

    @Override
    protected View onCreateDialogView() {

        final View view = super.onCreateDialogView();

        SeekBar seekBar = (SeekBar) view.findViewById(R.id.textSizeSeekBar);
        seekBar.setProgress(seekBarValue);
        //seekBar.setMax(120);
        changeText(view);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {
                    TextSizePreference.this.seekBarValue = i;
                    changeText(view);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }

    protected static int getValue() {
        return seekBarValue + 100;
    }

    protected void changeText(View view) {
        TextView tV = (TextView)view.findViewById(R.id.textSizeText);
        tV.setText(Integer.toString(seekBarValue));
    }
}
