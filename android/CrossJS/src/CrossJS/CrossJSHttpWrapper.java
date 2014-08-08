package CrossJS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;

import android.os.AsyncTask;
import android.util.Log;

public class CrossJSHttpWrapper {

	Context context;
	Scriptable scope;

	public CrossJSHttpWrapper(Context context, Scriptable scope) {
		this.context = context;
		this.scope = scope;
	}

	public void get(String url, NativeObject params, final Function successCallback, final Function errorCallback) {

		// Gerando a lista de parametros
		List<NameValuePair> lstParameters = new LinkedList<NameValuePair>();
		if (params != null) {

			for (Map.Entry<Object, Object> entry : params.entrySet()) {

				BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
				lstParameters.add(valuePair);
			}
		}

		NativeHttpGet httpGet = new NativeHttpGet();
		httpGet.setParams(lstParameters);
		httpGet.setHandler(new TaskComplete<String>(){
			@Override
			void onSuccess(String data) {
				
				successCallback.call(context, scope, scope, new Object[] {data});
			}
			
			@Override
			void onError(Exception ex) {
				String msg = ex.getMessage();
				errorCallback.call(context, scope, scope, new Object[] {msg});
			}
		});
		
		httpGet.execute(url);
	}
}

class TaskComplete<T> {
    void onSuccess(T data){}
	void onError(Exception ex){}
}

class NativeHttpGet extends AsyncTask<String, Void, String> {

	List<NameValuePair> params;
	TaskComplete<String> handler;

	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}

	public void setHandler(TaskComplete<String> handler) {
		this.handler = handler;
	}

	private String executeHttpGet(String URL) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "android");
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "text/plain; charset=utf-8");

			// Adicionando os parametros na URL
			if (params != null && !params.isEmpty()) {
				if (!URL.endsWith("?"))
					URL += "?";

				String paramString = URLEncodedUtils.format(params, "utf-8");
				URL += paramString;
			}

			request.setURI(new URI(URL));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";

			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String page = sb.toString();
			// System.out.println(page);
			return page;

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.d("BBB", e.toString());
				}
			}
		}
	}

	@Override
	protected String doInBackground(String... params) {

		try {
			return executeHttpGet(params[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (handler != null) {
				handler.onError(e);
			}
		}
		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		// The results of the above method
		// Processing the results here
		
		if (handler != null) {
			handler.onSuccess(result);
		}
	}
}
