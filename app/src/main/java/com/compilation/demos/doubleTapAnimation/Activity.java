package com.compilation.demos.doubleTapAnimation;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.util.Size;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.compilation.R;
import com.compilation.mainApp.HolderActivity;

public class Activity extends HolderActivity {

    /**
     * Demo to show a simple animation on doubleTap with GestureDetector
     */

    private GestureDetectorCompat mDetector;
    private Context context;
    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_tap_animation);
        context = this;

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        container = (RelativeLayout) findViewById(R.id.container);

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent event) {

            container.setOnTouchListener(null);

            Size size = new Size(200,200);

            //load animation and image
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.up);
            final ImageView image = new ImageView(context);
            image.setImageResource(R.drawable.arrow);
            image.setLayoutParams(new LinearLayout.LayoutParams(size.getWidth(), size.getHeight()));

            //set the image coordinates from doubleTap location
            image.setX((int) event.getX() - size.getWidth()/2);
            image.setY((int) event.getY() - size.getHeight()/2);

            container.addView(image);

            image.startAnimation(anim);

            //reset the touch event listener
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            mDetector.onTouchEvent(motionEvent);
                            return true;
                        }
                    });
                }
            }, 100);

            //remove the heart after animation
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    container.removeView(image);
                }
            }, 500);

            return true;
        }
    }
}
