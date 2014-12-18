package com.crossjs;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CrossJS {

	// Singlethon Patter
	static CrossJS singleInstance;

	public static CrossJS getInstance(Context context) {

		if (singleInstance == null) {
			singleInstance = new CrossJS(context);
		}

		return singleInstance;
	}

	WebView webView;
	Context context;

	@SuppressLint("SetJavaScriptEnabled")
	private CrossJS(Context context) {

		this.context = context;

		// Iniciando o WebView
		webView = new WebView(context);

		// Permitindo javascript
		webView.getSettings().setJavaScriptEnabled(true);

		// Mapeando os erros e as requisições de scripts
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

				WebResourceResponse result = interceptWebViewRequest(url);

				return (result != null) ? result : super.shouldInterceptRequest(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

				String msg = String.format("CrossJS Error, Code: %d, Description: %s, FailingURL: %s", errorCode, description, failingUrl);
				Log.e("CrossJS", msg);

				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});

		// registerWrappers();
	}

	// private void registerWrappers() {
	//
	// // Mapeando a funcao console.log para o console Nativo
	// setJSVariable("console", new LogWrapper());
	//
	// // Mapeando o objeto http para a implementacao nativa
	// setJSVariable("$http", new HttpWrapper(context, scope));
	//
	// }}

	public WebView getContext_WebView() {
		return webView;
	}

	private void registerScriptInWebView(String scriptFilePath) {

		webView.loadUrl("javascript:(function() {" + "var parent = document.getElementsByTagName('head').item(0);" + "var script = document.createElement('script');"
				+ "script.type = 'text/javascript';" + "script.src = 'asset://" + scriptFilePath + "';" + "parent.appendChild(script)" + "})()");

	}

	private WebResourceResponse interceptWebViewRequest(String url) {
		// Como a webView nao consegue ter acesso diretamente para arquivos
		// locais, esse codigo intercepta a requisição para fazer a leitura
		// do arquivo javascript da pasta assets e retorna o conteudo do arquivo
		// como conteudo da requisição HTTP
		String scheme = "asset://";

		if (url.startsWith(scheme)) {
			try {

				String scriptFile = url.replace("asset://", "");

				InputStream input = context.getAssets().open(scriptFile);

				return new WebResourceResponse("text/javascript", "utf-8", input);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public void loadJSFile(String filePath) throws Exception {

		// Separando o nome da extensao
		String extension = "";

		int i = filePath.lastIndexOf('.');
		if (i > 0) {
			extension = filePath.substring(i);
		} else {
			String msg = "Invalid File.";
			Log.e("loadJSFile", msg);
			throw new Exception(msg);
		}

		// Verificando se a extensão é .js
		if (!extension.equals(".js")) {
			String msg = "This is not a JavaScript file.";
			Log.e("loadJSFile", msg);
			throw new Exception(msg);
		}

		// Registrando na WebView
		registerScriptInWebView(filePath);
	}

	public void setJSVariable(String variablePath, Object nativeValue) {

		// Verificando se esta chamando variaveis encadeadas. Ex:
		// obj.prop1.prop2
		// String[] arrayVariables = variablePath.split("\\.");

		// if (arrayVariables.length <= 1) {

		// Chamada simples
		webView.addJavascriptInterface(nativeValue, variablePath);
		// } else {
		//
		// // Chamada encadeada
		// ScriptableObject currentValue;
		//
		// for (int i = 0; i < arrayVariables.length; i++) {
		//
		// String variable = arrayVariables[i];
		// currentValue = (ScriptableObject) scope.get(variable, null);
		//
		// if (currentValue.isEmpty()) {
		//
		// ScriptableObject.putProperty(currentValue, variable,
		// context.javaToJS(new Object(), scope));
		// currentValue = (ScriptableObject) scope.get(variable, null);
		// }
		//
		// // Se chegou na ultima variavel, executa
		// if (i + 1 == arrayVariables.length - 1) {
		// String finalVariable = arrayVariables[i + 1];
		//
		// currentValue.put(finalVariable, currentValue,
		// context.javaToJS(nativeValue, scope));
		// break;
		// }
		// }
		// }
	}

	public Object executeJS(String jsCode) {
		try {
			webView.loadUrl("javascript:" + jsCode);
			// TODO: Retornar o valor do código executado

		} catch (Exception ex) {

			Log.e("executeJS", ex.getMessage());
		}
		return null;
	}
}
