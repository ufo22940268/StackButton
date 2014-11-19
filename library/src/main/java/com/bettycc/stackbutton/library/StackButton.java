package com.bettycc.stackbutton.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


public class StackButton extends FrameLayout implements View.OnClickListener {

    private View mStackView;
    private boolean stackOn;
    private List<View> mItemViews = new ArrayList<View>();
    private float mViewSize;
    private OnClickListener mItemClickListener;

    public StackButton(Context context) {
        super(context);
        init(null, 0);
    }

    public StackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StackButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mViewSize = getResources().getDimension(R.dimen.item_size);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mStackView = findViewById(R.id.stack);
        mStackView.setOnClickListener(this);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getId() != R.id.stack) {
                view.setVisibility(View.GONE);
                mItemViews.add(view);
            }

            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.stack) {
            toggle();
        }
    }

    public void toggle() {
        RotateAnimation rotateAnimation = getRotateAnimation(isStackOn());
        mStackView.startAnimation(rotateAnimation);

        setStackOn(isStackOn() ? false : true);

        if (isStackOn()) {
            showStack();
        } else {
            hideStack();
        }
    }

    private void hideStack() {

        if (mItemViews.size() == 0) {
            return;
        }

        View view = mItemViews.get(0);
        for (int i = 0; i < mItemViews.size(); i++) {
            View itemView = mItemViews.get(i);
            hideItemAnim(itemView, i);
        }
    }

    private void hideItemAnim(final View view, int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -mViewSize * (i + 1), 0);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);

        view.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showStack() {
        if (mItemViews.size() == 0) {
            return;
        }

        View view = mItemViews.get(0);
        for (int i = 0; i < mItemViews.size(); i++) {
            View itemView = mItemViews.get(i);
            showItemAnim(itemView, i);
        }
    }

    private void showItemAnim(final View view, int i) {
        view.setVisibility(VISIBLE);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -mViewSize * (i + 1));
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        translateAnimation.setInterpolator(new OvershootInterpolator());

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setItemsListener();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    private void setItemsListener() {
        for (int i = 0; i < getChildCount(); i++) {
            View itemView = getChildAt(i);
            if (itemView.getId() != R.id.stack) {
                itemView.setOnClickListener(mItemClickListener);
            }
        }
    }

    private RotateAnimation getRotateAnimation(boolean stackOn) {
        int fromDegrees = stackOn ? 125 : 0;
        int toDegrees = stackOn ? 0 : 125;
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, mStackView.getWidth() / 2, mStackView.getHeight() / 2);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
        rotateAnimation.setInterpolator(new OvershootInterpolator());
        return rotateAnimation;
    }

    public boolean isStackOn() {
        return stackOn;
    }

    public void setStackOn(boolean stackOn) {
        this.stackOn = stackOn;
    }

    public void setItemClickListener(OnClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
