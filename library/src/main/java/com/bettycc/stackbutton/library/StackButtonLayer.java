package com.bettycc.stackbutton.library;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by ccheng on 11/19/14.
 */
public class StackButtonLayer {

    private final float mPadding;
    private Activity mActivity;
    private int mLayout;
    private PopupWindow mPopupWindow;
    private final StackButton mContainer;
    private View.OnClickListener mItemClickListener;

    public StackButtonLayer(Activity activity, int layout) {
        mActivity = activity;
        mLayout = layout;
        mPadding = mActivity.getResources().getDimension(R.dimen.stack_button_padding);
        mContainer = (StackButton) LayoutInflater.from(mActivity).inflate(mLayout, null);
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
        mContainer.setItemClickListener(itemClickListener);
    }
}
