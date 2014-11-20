package com.bettycc.stackbutton;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bettycc.stackbutton.library.StackButton;


public class MainActivity extends ActionBarActivity {

    private StackButton mStackButtonLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStackButtonLayer = new StackButton(this, R.layout.stack_button);
        mStackButtonLayer.show();
        setStackButtonClickListener(mStackButtonLayer);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStackButtonLayer.hide();
    }

    private void setStackButtonClickListener(StackButton stackButtonLayer) {
        stackButtonLayer.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeIdToast(view.getId());
            }

            private void makeIdToast(int id) {
                String resourceName = getResources().getResourceName(id);
                Toast.makeText(MainActivity.this, resourceName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
