package com.example.jsbridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import CrossJS.CrossJS;
import CrossJS.CrossJSHttpWrapper;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mozilla.javascript.*;

public class MainActivity extends ActionBarActivity implements JsBridgeView {

	EditText editText1;
	EditText editText2;
	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		btn1 = (Button) findViewById(R.id.button1);

		try {

			// Lendo e executando o arquivo javascript
			CrossJS.getInstance().loadExecuteJSFile("js/Controller.js", getAssets());
			
			//Conectando o javascript com a interface nativa
			CrossJS.getInstance().setJSVariable("Controller.view", this);
			
			CrossJS.getInstance().executeJS("http.get('http://jsonip.com',{ p1 : 'p1Value'},function(response){ console.log(response); Controller.view.showMessage(response); },function(){});");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				CrossJS.getInstance().executeJS("Controller.onClickSoma();");

			}
		});

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

	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		reader.close();
		return sb.toString();
	}

	@Override
	public float getNum1() {
		try {
			return Float.parseFloat(editText1.getText().toString());
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public float getNum2() {

		try {
			return Float.parseFloat(editText2.getText().toString());
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public void showMessage(String msg) {
		Log.v("showMessage", msg);
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage(msg);
		dlgAlert.setTitle("Resultado");
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(false);
		dlgAlert.create().show();
	}
}
