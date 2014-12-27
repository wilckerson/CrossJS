package com.example.todoapp;

import com.crossjs.CrossJS;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	boolean jsLoaded = false;
	void loadJSFilesIfNeed()
	{
		if(jsLoaded == false){
		try {
			CrossJS.getInstance(this).loadJSFile("js/todoListPresenter.js");
			CrossJS.getInstance(this).loadJSFile("js/todoItemPresenter.js");
			jsLoaded = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loadJSFilesIfNeed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_new) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
