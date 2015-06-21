package com.rorlig.androidpaint;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 *@author gaurav gupta
 * Selects the color for the paint...
 */
public class ColorSelectFragment extends DialogFragment {

    private String TAG = "ColorSelectFragment";

    @InjectView(R.id.picker)
    ColorPicker colorPicker;

    @InjectView(R.id.svbar)
    SVBar svBar;

    @InjectView(R.id.opacitybar)
    OpacityBar opacityBar;

    @InjectView(R.id.button1)
    Button button;

    @InjectView(R.id.textView1)
    TextView textView;

    Bus bus = BusProvider.getInstance();

    public ColorSelectFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_color_select, container);
        ButterKnife.inject(this, view);
        return view;
    }



    @OnClick(R.id.button1)
    public void onButtonClick() {
        Log.d(TAG, "color " + colorPicker.getColor());
        textView.setTextColor(colorPicker.getColor());
        colorPicker.setOldCenterColor(colorPicker.getColor());
        bus.post(new ColorChangeEvent(colorPicker.getColor()));
        dismiss();
    }
}
