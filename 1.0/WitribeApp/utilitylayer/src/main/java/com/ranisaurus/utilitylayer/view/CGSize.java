package com.ranisaurus.utilitylayer.view;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class CGSize {

    public int WIDTH;
    public int HEIGHT;

    public CGSize(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public static CGSize make(int width, int height) {
        return new CGSize(width, height);
    }
}