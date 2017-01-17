package com.eneko.hexcolortimewallpaper;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class TextSizePreference extends DialogPreference{

    private static int seekBarValue;

    public TextSizePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setDialogLayoutResource(R.layout.text_size);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if(restoreValue) {
            seekBarValue = getPersistedInt(seekBarValue);
        }
        else {
            seekBarValue = (Integer) defaultValue;
        }
    }

    @Override
    protected View onCreateDialogView() {

        final View view = super.onCreateDialogView();

        SeekBar seekBar = (SeekBar) view.findViewById(R.id.textSizeSeekBar);
        seekBar.setProgress(seekBarValue);
        changeText(view);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {
                    seekBarValue = i;
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

    static int getValue() {
        return seekBarValue + 100;
    }

    private void changeText(View view) {
        TextView tV = (TextView)view.findViewById(R.id.textSizeText);
        tV.setText(Integer.toString(getValue()));
    }
}
