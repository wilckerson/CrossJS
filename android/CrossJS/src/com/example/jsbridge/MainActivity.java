package com.example.jsbridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import CrossJS.CrossJS;
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

import org.mozilla.javascript.*;

public class MainActivity extends ActionBarActivity implements JsBridgeView {

	//Context ctx;
	//Scriptable scope;

	EditText editText1;
	EditText editText2;
	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// doit("var widgets = Packages.android.widget;\n"
		// + "var view = new widgets.TextView(TheActivity);\n"
		// + "TheActivity.setContentView(view);\n"
		// + "var text = 'Hello Android!\\nThis is JavaScript in action!';\n"
		// + "view.append(text);");

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		btn1 = (Button) findViewById(R.id.button1);

		// Create an execution environment.
		//ctx = Context.enter();

		// Turn compilation off.
		//ctx.setOptimizationLevel(-1);

		// Initialize a variable scope with bindnings for
		// standard objects (Object, Function, etc.)
		//scope = ctx.initStandardObjects();

//		try {
//			InputStream fileStream = getAssets().open("Controller.js");
//			String fileContent = convertStreamToString(fileStream);
//
//			ctx.evaluateString(scope, fileContent, "Controller", 1, null);
//			ScriptableObject controller = (ScriptableObject) scope.get(
//					"Controller", null);
//			ScriptableObject.putProperty(controller, "view",
//					ctx.javaToJS(this, scope));
//
//			// ctx.evaluateString(scope, "Controller.onClickSoma()", "onClick",
//			// 1,null);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// String fileContent = readFromFile();
//		catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// doit(null);
		try {
			
			//Lendo e executando o arquivo javascript
			CrossJS.getInstance().loadExecuteJSFile("js/Controller.js", getAssets());
			
			 //Conectando o javascript com a interface nativa
			CrossJS.getInstance().setJSVariable("Controller.view", this);
			
			Object result = CrossJS.getInstance().getContext_RhinoScope().get("Controller", null);
			Log.v(null, CrossJS.getInstance().getContext_Rhino().toString(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				CrossJS.getInstance().executeJS("Controller.onClickSoma()");

			}
		});

	}

	void doit(String code) {
		// Create an execution environment.
		Context cx = Context.enter();

		// Turn compilation off.
		cx.setOptimizationLevel(-1);

		try {
			// Initialize a variable scope with bindnings for
			// standard objects (Object, Function, etc.)
			Scriptable scope = cx.initStandardObjects();

			// Set a global variable that holds the activity instance.
			ScriptableObject.putProperty(scope, "View",
					Context.javaToJS(this, scope));

			// Evaluate the script.
			cx.evaluateString(scope, "var a = View.getValue();", "doit:", 1,
					null);
			Object result = scope.get("a", null);
			Log.v(null, cx.toString(result));

		} finally {
			Context.exit();
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
