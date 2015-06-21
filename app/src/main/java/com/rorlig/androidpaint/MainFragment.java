package com.rorlig.androidpaint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.capricorn.ArcMenu;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment implements ColorPicker.OnColorChangedListener{

    private static final int[] ITEM_DRAWABLES = { R.drawable.ic_action_paint_palette, R.drawable.ic_action_pen, R.drawable.ic_action_eraser };




    @InjectView(R.id.arc_menu)
    ArcMenu arcMenu;

    @InjectView(R.id.paint_view)
    PaintBoardView paintView;

    @InjectView(R.id.current_tool)
    ImageView currentToolImageView;


    Bus bus;

    private final String TAG = "MainFragment";
    private ColorSelectFragment colorSelectFragment;
    private int currentColor;
    private int currentTool = 1;

    public MainFragment() {
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        initArcMenu(arcMenu, ITEM_DRAWABLES);

        bus = BusProvider.getInstance();




//        initPaint();
    }

    private void initPaint() {

    }

    /*
    * Register to events...
    */
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
        bus.register(this);
    }

    /*
     * Unregister from events ...
     */
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
        bus.unregister(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(getActivity());
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                    if (position!=0) {
                        currentTool = position;
                    }

                    switch (position) {
                        case 0:
                            //todo show the color picker
                            colorSelectFragment = new ColorSelectFragment();
                            colorSelectFragment.show(getActivity().getFragmentManager(), "color_fragment");

                            break;
                        case 1:
                            currentToolImageView.setImageDrawable(
                                    ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_pen, null));
                            currentToolImageView.setColorFilter(currentColor, PorterDuff.Mode.SRC_ATOP);

                            paintView.setMode(Mode.NORMAL);
                            break;
                        case 2:
                            currentToolImageView.setImageDrawable(
                                    ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_eraser, null));
                                    paintView.setMode(Mode.ERASE);
                            break;

                    }
                }
            });
        }
    }


    @Override
    public void onColorChanged(int color) {
        Log.d(TAG, "color " + color);

        paintView.setPaintColor(color);
    }

    @Subscribe
    public void onColorChange(ColorChangeEvent colorChangeEvent) {
        Log.d(TAG, "onColorChange " + colorChangeEvent.getColor() + " currentTool "  + currentTool);
        this.currentColor = colorChangeEvent.getColor();
        paintView.setPaintColor(colorChangeEvent.getColor());
        if (currentTool==1) {
            currentToolImageView.setColorFilter(currentColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Subscribe
    public void onClearEvent(ClearEvent event) {
        paintView.clear();
        currentTool=1;
        currentToolImageView.setColorFilter(null);
    }
}
