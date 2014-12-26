package CrossJS.examples;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import CrossJS.CrossJS;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements MainView {

	Button buttonExSum;
	Button buttonExGetRemote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			CrossJS.getInstance().loadExecuteJSFile("js/MainController.js", getAssets());
			CrossJS.getInstance().setJSVariable("MainController.view", this);

		} catch (Exception e) {

			Log.e("exception",e.getMessage());
		}

		// Navegando para a activity correspondente
		buttonExSum = (Button) findViewById(R.id.buttonExSum);
		buttonExSum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CrossJS.getInstance().executeJS("MainController.onClickExSum();");

			}
		});

		buttonExGetRemote = (Button) findViewById(R.id.buttonExGetRemote);
		buttonExGetRemote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				CrossJS.getInstance().executeJS("MainController.onClickExGetRemote();");

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
	public void navigateToExSum() {
		Intent i = new Intent(getApplicationContext(), SumActivity.class);
		startActivity(i);
	}

	@Override
	public void navigateToExGetRemote() {
		Intent i = new Intent(getApplicationContext(), GetRemoteActivity.class);
		startActivity(i);
	}

}
