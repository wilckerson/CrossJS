package CrossJS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

enum NativeHttpMethod {
	GET, POST
}

public class NativeHttp extends AsyncTask<String, Void, String> {

	List<NameValuePair> params;
	TaskComplete<String> handler;
	NativeHttpMethod method = NativeHttpMethod.GET;

	public void setMethod(NativeHttpMethod method) {
		this.method = method;
	}

	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}

	public void setHandler(TaskComplete<String> handler) {
		this.handler = handler;
	}

	private String responseToString(HttpResponse response) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer sb = new StringBuffer("");
		String line = "";
		
		String NL = System.getProperty("line.separator");
		while ((line = in.readLine()) != null) {
			sb.append(line + NL);
		}
		in.close();

		String result = sb.toString();
		return result;
	}

	private String executeHttpGet(String URL) throws Exception {

		try {
			// Adicionando os parametros na URL
			if (params != null && !params.isEmpty()) {
				if (!URL.endsWith("?"))
					URL += "?";

				String paramString = URLEncodedUtils.format(params, "utf-8");
				URL += paramString;
			}

			HttpClient client = new DefaultHttpClient();
			// client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"android");
			HttpGet request = new HttpGet(URL);
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			// request.setURI(new URI(URL));
			HttpResponse response = client.execute(request);

			String result = responseToString(response);
			return result;
		} catch (Exception e) {
			Log.d("executeHttpGet Exception", e.toString());
			return null;
		}
	}

	private String executeHttpPost(String URL) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL);

		try {
			// Adicionando os parametros
			httppost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			String result = responseToString(response);
			return result;

		} catch (Exception e) {
			Log.d("executeHttpPost Exception", e.toString());
			return null;
		}
	}

	@Override
	protected String doInBackground(String... params) {

		try {
			String response = null;

			if (method == NativeHttpMethod.GET) {
				
				response = executeHttpGet(params[0]);
				
			} else if (method == NativeHttpMethod.POST) {
				
				response = executeHttpPost(params[0]);
			}
			return response;

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