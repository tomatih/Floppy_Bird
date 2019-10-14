package com.to_matih.floppybird;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class game_view extends SurfaceView implements SurfaceHolder.Callback {

    private static game_thread thread;

    // graphic constants
    private final int ScreenWidth;
    private final int ScreenHeight;
    private int BackgroundColour = ResourcesCompat.getColor(getResources(), R.color.Sky, null);

    // objects
    private rectangle grass;

    public game_view(Context context) {
        super(context);

        getHolder().addCallback(this);

        ScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        ScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        thread = new game_thread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // create an start the game thread
        thread.setRunning(true);
        thread.start();

        // actor initialisation
        grass = new rectangle(75, ScreenWidth, new Vector2(ScreenWidth / 2, ScreenHeight - (75 / 2)),
                ResourcesCompat.getColor(getResources(), R.color.Pipe, null));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // safely join the game thread
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // clear screen
        canvas.drawColor(BackgroundColour);
        // draw static
        grass.draw(canvas);
    }
}
