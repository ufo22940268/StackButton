package com.bettycc.stackbutton.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;


public class StackButtonWidget extends FrameLayout implements View.OnClickListener {

    private View mStackView;
    private boolean stackOn;
    private List<View> mItemViews = new ArrayList<View>();
    private float mViewSize;
    private OnClickListener mItemClickListener;

    public StackButtonWidget(Context context) {
        super(context);
        init(null, 0);
    }

    public StackButtonWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StackButtonWidget(Context context, AttributeSet attrs, int defStyle) {
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
        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY",  -mViewSize * (i + 1), 0);
        translateY.setInterpolator(new OvershootInterpolator());
        translateY.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        alpha.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime) - 200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateY).with(alpha);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

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

        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", 0,  -mViewSize * (i + 1) );
        translateY.setInterpolator(new OvershootInterpolator());
        translateY.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        alpha.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateY).with(alpha);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setItemsListener();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
        rotateAnimation.setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
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
