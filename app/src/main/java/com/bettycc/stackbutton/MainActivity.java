package com.bettycc.stackbutton;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bettycc.stackbutton.library.StackButtonLayer;


public class MainActivity extends ActionBarActivity {

    private StackButtonLayer mStackButtonLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStackButtonLayer = new StackButtonLayer(this, R.layout.stack_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStackButtonLayer.show();
        setStackButtonClickListener(mStackButtonLayer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStackButtonLayer.hide();
    }

    private void setStackButtonClickListener(StackButtonLayer stackButtonLayer) {
        stackButtonLayer.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeIdToast(view.getId());
                System.out.println("MainActivity.onClick");
            }

            private void makeIdToast(int id) {
                String resourceName = getResources().getResourceName(id);
                Toast.makeText(MainActivity.this, resourceName, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
