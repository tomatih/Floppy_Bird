package com.to_matih.floppybird;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class game_view extends SurfaceView implements SurfaceHolder.Callback {

    private static game_thread thread;

    // graphic constants
    private final int ScreenWidth;
    private final int ScreenHeight;
    private int BackgroundColour = ResourcesCompat.getColor(getResources(), R.color.Sky, null);

    // physics constants
    private static final int Gravity = 100;
    private static final int Bird_tap_velocity = 4 * Gravity;
    // physics variables
    private float bird_velocity = 0;

    // objects
    private rectangle grass;
    private circle bird;

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
        bird = new circle(75, new Vector2(ScreenWidth / 2, ScreenHeight / 2),
                ResourcesCompat.getColor(getResources(), R.color.Bird, null));
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

    public void update(long time_delta_raw) {
        // get delta in seconds
        float time_delta = (time_delta_raw * 1.0f) / 100000000;

        // gravity effect
        bird.pos.y += Gravity * time_delta;
        //velocity
        if (bird_velocity > 0) {
            bird.pos.y -= bird_velocity * time_delta;
            bird_velocity -= Gravity * time_delta;
        }

        // grass collision
        if (bird.pos.y > ScreenHeight - (bird.radius + grass.getHeight())) {
            bird.pos.y = (int) (ScreenHeight - (bird.radius + grass.getHeight()));
        }

        // top of the screen bouncing
        if (bird.pos.y < bird.radius) {
            bird.pos.y = bird.radius;
            bird_velocity = 0;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        bird_velocity = Bird_tap_velocity;
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // clear screen
        canvas.drawColor(BackgroundColour);
        // draw static
        grass.draw(canvas);
        //draw dynamic
        bird.draw(canvas);
    }
}
