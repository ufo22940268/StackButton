package com.bettycc.stackbutton.library;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by ccheng on 11/19/14.
 */
public class StackButton {

    private final float mPadding;
    private Activity mActivity;
    private int mLayout;
    private PopupWindow mPopupWindow;
    private final StackButtonWidget mContainer;
    private View.OnClickListener mItemClickListener;

    public StackButton(Activity activity, int layout) {
        mActivity = activity;
        mLayout = layout;
        mPadding = mActivity.getResources().getDimension(R.dimen.stack_button_padding);
        mContainer = (StackButtonWidget) LayoutInflater.from(mActivity).inflate(mLayout, null);
    }

    public void show() {
        int height = (int) (mActivity.getResources().getDimension(R.dimen.item_size) * (mContainer.getChildCount() + 1));
        mPopupWindow = new PopupWindow(mContainer,
                (int) mActivity.getResources().getDimension(R.dimen.item_size), height);
        mContainer.post(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(),
                        Gravity.RIGHT | Gravity.BOTTOM, (int) mPadding, (int) mPadding);
            }
        });
    }

    public void hide() {
        mPopupWindow.dismiss();
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            View v = mContainer.getChildAt(i);
            if (v.getId() != R.id.stack && v.getId() != -1) {
                v.setOnClickListener(itemClickListener);
            }
        }
    }
}
