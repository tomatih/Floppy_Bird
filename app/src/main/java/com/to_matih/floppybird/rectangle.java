package com.to_matih.floppybird;

import android.graphics.Canvas;
import android.graphics.Paint;


class rectangle {
    // object variables
    Vector2 pos;
    private int height;
    private int width;
    // drawing variables
    private int width_offset;
    private int height_offset;
    private Paint paint;

    rectangle(int height, int width, Vector2 pos, int color) {
        this.paint = new Paint();
        this.paint.setColor(color);

        this.pos = pos;
        this.setHeight(height);
        this.setWidth(width);
    }

    void setHeight(int height) {
        this.height = height;
        this.height_offset = height / 2;
    }

    void setWidth(int width) {
        this.width = width;
        this.width_offset = width / 2;
    }

    void draw(Canvas canvas) {
        canvas.drawRect(pos.x - width_offset, pos.y - height_offset,
                pos.x + width_offset, pos.y + height_offset, paint);
    }

}
