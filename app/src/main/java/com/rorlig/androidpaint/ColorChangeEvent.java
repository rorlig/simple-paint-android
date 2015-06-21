package com.rorlig.androidpaint;

/**
 * Created by rorlig on 6/21/15.
 */
public class ColorChangeEvent {
    private final int color;

    public ColorChangeEvent(int color) {
        this.color = color;

    }

    public int getColor() {
        return color;
    }
}
