package com.ranisaurus.utilitylayer.view;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class CGRect {

    public CGSize size;
    public CGPoint point;

    public CGRect(CGSize size, CGPoint point) {
        this.size = size;
        this.point = point;
    }

    public CGRect(int x, int y,int WIDTH, int HEIGHT) {
        this.size = new CGSize(WIDTH,HEIGHT);
        this.point = new CGPoint(x,y);
    }
}
