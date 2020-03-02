package com.e.animationtest;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class CustomTransition extends Transition {
    private static final String PROPNAME_TEXT_COLOR = "com.e.animationtest:CustomTransition:textColor";
    private static final String[] TRANSITION_PROPERTIES = {PROPNAME_TEXT_COLOR};

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        final Integer startTextColor = (Integer)startValues.values.get(PROPNAME_TEXT_COLOR);
        final Integer endTextColor = (Integer)endValues.values.get(PROPNAME_TEXT_COLOR);
        final TextView textView = (TextView)endValues.view;
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (Integer)argbEvaluator.evaluate(animation.getAnimatedFraction(), startTextColor, endTextColor);
                textView.setTextColor(color);
            }
        });

        return animator;
    }


    private void captureValues(@NonNull TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            int color = ((TextView) transitionValues.view).getCurrentTextColor();
            transitionValues.values.put(PROPNAME_TEXT_COLOR, color);
        }
    }
}