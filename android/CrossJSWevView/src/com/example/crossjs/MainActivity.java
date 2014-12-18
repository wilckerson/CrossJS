package com.example.crossjs;

import com.crossjs.CrossJS;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {

			CrossJS.getInstance(this).setJSVariable("MainActivity", this);
			CrossJS.getInstance(this).executeJS("MainActivity.doIt(JSON.stringify({hue: 'huehue'}));");

			CrossJS.getInstance(this).loadJSFile("js/doIt.js");

			btn = (Button) findViewById(R.id.button1);
			btn.setOnClickListener(this);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @JavascriptInterface
	public void doIt(String value) {

		Log.d(null, "DoIt");
	}
	
	@Override
	public void onClick(View arg0) {

		if (arg0 == btn) {

			CrossJS.getInstance(this).executeJS("JsDoIt();");
		}
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}

