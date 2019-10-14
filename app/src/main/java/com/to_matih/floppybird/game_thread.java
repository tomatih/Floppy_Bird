package com.to_matih.floppybird;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class game_thread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private game_view game_view;
    private boolean running;
    private static Canvas canvas;

    game_thread(SurfaceHolder surfaceHolder, game_view game_view) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.game_view = game_view;
    }

    void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                // unlock canvas for drawing
                canvas = this.surfaceHolder.lockCanvas();
                // draw and update
                synchronized (surfaceHolder) {
                    this.game_view.update();
                    this.game_view.draw(canvas);
                }
            } catch (Exception ignored) {
            }
            // post drawn content
            finally {
                if (canvas != null) {
                    try {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


}
