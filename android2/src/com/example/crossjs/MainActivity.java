package com.example.crossjs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

	WebView webView;
	
	// @SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
				// TODO Auto-generated method stub

				String scheme = "asset://";

				if (url.startsWith(scheme)) {
					try {

						String scriptFile = url.replace("asset://", "");

						InputStream input = getAssets().open(scriptFile);

						return new WebResourceResponse("text/javascript", "utf-8", input);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}

				return super.shouldInterceptRequest(view, url);
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				
				String msg = String.format("CrossJS Error, Code: %d, Description: %s, FailingURL: %s", errorCode,description,failingUrl);
				Log.e("CrossJS",msg);
				
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			
			
			
		});
		
		webView.addJavascriptInterface(this, "MainActivity");
		webView.loadUrl("javascript:MainActivity.DoIt(JSON.stringify({hue: 'huehue'}));");
		
		injectScriptFile(webView, "js/doIt.js");

		
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				webView.loadUrl("javascript:forceError();");

			}
		});
		
	}

	private void injectScriptFile(WebView view, String scriptFile) {
		
		try {
			
			view.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var script = document.createElement('script');"
					+ "script.type = 'text/javascript';" +
					"script.src = 'asset://" + scriptFile + "';" 
					+ "parent.appendChild(script)" + "})()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @JavascriptInterface
	public void DoIt(String value) {
		
		try {
			JSONObject json = new JSONObject(value);
			
			String hue = json.get("hue").toString();
			
			 //json.getJSONArray("array").get(index)
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d(null, "DoIt");

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
