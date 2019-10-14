package com.to_matih.floppybird;

import android.graphics.Canvas;
import android.graphics.Paint;

public class circle {
    public Vector2 pos;
    public float radius;
    private Paint paint;

    circle(float radius, Vector2 pos, int colour) {
        this.radius = radius;
        this.pos = pos;

        this.paint = new Paint();
        this.paint.setColor(colour);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x, pos.y, radius, paint);
    }
}
