package com.scottyab;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.scottyab.heartbeatview.R;

/**
 * simple custom view of a beating heart which is achieved by a scaling animation
 */
public class HeartBeatView extends AppCompatImageView {

    private static final String TAG = "HeartBeatView";

    private static final float DEFAULT_SCALE_FACTOR = 0.2f;
    private static final int DEFAULT_DURATION = 50;
    private Drawable heartDrawable;

    private boolean heartBeating = false;

    float scaleFactor = DEFAULT_SCALE_FACTOR;
    float reductionScaleFactor = -scaleFactor;
    int duration = DEFAULT_DURATION;

    public HeartBeatView(Context context) {
        super(context);
        init();
    }

    public HeartBeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        populateFromAttributes(context, attrs);
        init();
    }

    public HeartBeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        populateFromAttributes(context, attrs);
        init();
    }

    private void init() {
        heartDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_heart_red_24dp);
        setImageDrawable(heartDrawable);
    }

    private void populateFromAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.HeartBeatView,
                0, 0
        );
        try {
            scaleFactor = a.getFloat(R.styleable.HeartBeatView_scaleFactor, DEFAULT_SCALE_FACTOR);
            reductionScaleFactor = -scaleFactor;
            duration = a.getInteger(R.styleable.HeartBeatView_duration, DEFAULT_DURATION);
        } finally {
            a.recycle();
        }

    }


    /**
     * toggles current heat beat state
     */
    public void toggle() {
        if (heartBeating) {
            stop();
        } else {
            start();
        }
    }

    /**
     * Starts the heat beat/pump animation
     */
    public void start() {
        heartBeating = true;
        animate().scaleXBy(scaleFactor).scaleYBy(scaleFactor).setDuration(duration).setListener(scaleUpListener);
    }

    /**
     * Stops the heat beat/pump animation
     */
    public void stop() {
        heartBeating = false;
        clearAnimation();
    }

    /**
     * is the heart currently beating
     *
     * @return
     */
    public boolean isHeartBeating() {
        return heartBeating;
    }

    public int getDuration() {
        return duration;
    }


    private static final int milliInMinute = 60000;

    /**
     * set the duration of the beat based on the beats per minute
     *
     * @param bpm (positive int above 0)
     */
    public void setDurationBasedOnBPM(int bpm) {
        if (bpm > 0) {
            duration = Math.round((milliInMinute / bpm) / 3f);
        }
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        reductionScaleFactor = -scaleFactor;
    }

    private final Animator.AnimatorListener scaleUpListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            //we ignore heartBeating as we want to ensure the heart is reduced back to original size
            animate().scaleXBy(reductionScaleFactor).scaleYBy(reductionScaleFactor).setDuration(duration).setListener(scaleDownListener);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }
    };


    private final Animator.AnimatorListener scaleDownListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (heartBeating) {
                //duration twice as long for the upscale
                animate().scaleXBy(scaleFactor).scaleYBy(scaleFactor).setDuration(duration * 2).setListener(scaleUpListener);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }
    };

}
