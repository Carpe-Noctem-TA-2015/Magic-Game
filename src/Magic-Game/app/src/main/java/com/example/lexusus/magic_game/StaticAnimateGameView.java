package com.example.lexusus.magic_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;


public class StaticAnimateGameView extends SurfaceView implements Runnable {

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    Canvas canvas;
    Paint paint;

    // Resoruce
    Bitmap animatedImage;

    // Animation Cotronl
    volatile boolean playing = true;
    boolean isMoving = true;

    float startingPoitionOfCut = 10;

    // New for the sprite sheet animation

    // These next two values can be anything you like
    // As long as the ratio doesn't distort the sprite too much
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private int currentFrame;

    private long lastFrameChangeTime = 0;
    private int frameLengthInMilliseconds = 100;

    private Rect frameToDraw;
    private RectF whereToDraw;

    public StaticAnimateGameView(Context context, int resource, int frameWidth, int frameHeight, int frameCount) {
        super(context);

        //set drawers
        ourHolder = getHolder();
        paint = new Paint();

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.currentFrame = 0;

        this.setLayoutParams(new LinearLayout.LayoutParams(frameWidth,frameHeight));
        initDrawingArea();

        //set resource
        animatedImage = BitmapFactory.decodeResource(this.getResources(), resource);
        animatedImage = Bitmap.createScaledBitmap(animatedImage,
                frameWidth * frameCount,
                frameHeight,
                false);
    }

    private void initDrawingArea() {
        frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);


        whereToDraw = new RectF(
                startingPoitionOfCut, 0,
                startingPoitionOfCut + frameWidth,
                frameHeight);

    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            update();

            // Draw the frame
            draw();
        }

    }

    // Everything that needs to be updated goes in here
    // In later projects we will have dozens (arrays) of objects.
    // We will also do other things like collision detection.
    public void update() {
        // update vriables

    }

    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if(isMoving) {// Only animate if bob is moving
            if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= frameCount) {

                    currentFrame = 0;
                }
            }
        }
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

    }

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color, to delete past frames
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            whereToDraw.set((int)startingPoitionOfCut,
                    0,
                    (int)startingPoitionOfCut + frameWidth,
                    frameHeight);

            getCurrentFrame();

            canvas.drawBitmap(animatedImage,
                    frameToDraw,
                    whereToDraw, paint);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                // Set isMoving so Bob is moved in the update method
                isMoving = true;

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                // Set isMoving so Bob does not move
                isMoving = false;

                break;
        }
        return true;
    }

}
