package CrossJS;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;


public class HttpWrapper {

	Context context;
	Scriptable scope;

	public HttpWrapper(Context context, Scriptable scope) {
		this.context = context;
		this.scope = scope;
	}

	public void get(String url, NativeObject params, final Function successCallback, final Function errorCallback) {
		http(NativeHttpMethod.GET,url,params, successCallback,errorCallback);
	}
	
	public void post(String url, NativeObject params, final Function successCallback, final Function errorCallback) {
		http(NativeHttpMethod.POST,url,params, successCallback,errorCallback);
	}

	private void http(NativeHttpMethod method, String url, NativeObject params, final Function successCallback, final Function errorCallback) {
		
		// Gerando a lista de parametros
		List<NameValuePair> lstParameters = new LinkedList<NameValuePair>();
		if (params != null) {

			for (Map.Entry<Object, Object> entry : params.entrySet()) {

				BasicNameValuePair valuePair = new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString());
				lstParameters.add(valuePair);
			}
		}

		NativeHttp nativeHttp = new NativeHttp();
		nativeHttp.setMethod(method);
		nativeHttp.setParams(lstParameters);
		nativeHttp.setHandler(new TaskComplete<String>() {
			@Override
			void onSuccess(String data) {

				successCallback.call(context, scope, scope, new Object[] { data });
			}

			@Override
			void onError(Exception ex) {
				String msg = ex.getMessage();
				errorCallback.call(context, scope, scope, new Object[] { msg });
			}
		});

		nativeHttp.execute(url);
	}
}
