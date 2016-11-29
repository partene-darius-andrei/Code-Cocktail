package com.compilation.demos;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
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

public class DoubleTapHeart extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    private Context context;
    private RelativeLayout container;
    private Size size = new Size(200,200);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_tap_heart);
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

            //init heart and animation
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.move);
            final ImageView heart = new ImageView(context);
            heart.setImageResource(R.drawable.heart);
            heart.setLayoutParams(new LinearLayout.LayoutParams(size.getWidth(), size.getHeight()));
            heart.setX((int) event.getX() - size.getWidth()/2);
            heart.setY((int) event.getY() - size.getHeight()/2);

            container.addView(heart);

            heart.startAnimation(anim);

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
                    container.removeView(heart);
                }
            }, 1000);

            return true;
        }
    }
}
