package CrossJS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.*;

import android.content.ContextWrapper;
import android.content.res.AssetManager;

public class CrossJS {

	// Singlethon Patter
	static CrossJS singleInstance;

	public static CrossJS getInstance() {

		if (singleInstance == null) {
			singleInstance = new CrossJS();
		}

		return singleInstance;
	}

	Context context;
	Scriptable scope;

	private CrossJS() {

		// Iniciando o contexto do JavaScriptCore
		context = Context.enter();

		// Turn compilation off.
		context.setOptimizationLevel(-1);

		// Initialize a variable scope with bindnings for
		// standard objects (Object, Function, etc.)
		scope = context.initStandardObjects();

		// Mapeando a funcao console.log para o console Nativo

	}

	public Context getContext_Rhino() {
		return context;
	}
	public Scriptable getContext_RhinoScope() {
		return scope;
	}

	public String loadJSFile(String fileName, AssetManager assetManager)
			throws Exception {

		String jsCode;

		// Separando o nome da extensao
		String extension = "";
		String name = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i);
		} else {
			throw new Exception("Informe um arquivo v�lido.");
		}

		// Verificando se a extensao � .js
		if (!extension.equals(".js")) {
			throw new Exception("N�o � um arquivo JS.");
		}

		// Lendo o arquivo JS
		try {
			InputStream fileStream = assetManager.open(fileName);
			jsCode = convertStreamToString(fileStream);
		} catch (Exception ex) {
			throw new Exception(String.format(
					"N�o foi possivel ler o arquivo %s", fileName), ex);
		}

		return jsCode;

	}

	public void loadExecuteJSFile(String fileName, AssetManager assetManager)
			throws Exception {

		String jsCode = this.loadJSFile(fileName, assetManager);
		context.evaluateString(scope, jsCode, "loadExecuteJSFile", 1, null);
	}

	private static String convertStreamToString(InputStream is)
			throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		reader.close();
		return sb.toString();
	}

	public void setJSVariable(String variablePath, Object nativeValue) {

		// Verificando se esta chamando variaveis encadeadas. Ex:
		// obj.prop1.prop2
		String[] arrayVariables = variablePath.split("\\.");

		if (arrayVariables.length <= 1) {

			// Chamada simples
			scope.put(variablePath, scope, context.javaToJS(nativeValue, scope));
		} else {

			// Chamada encadeada
			ScriptableObject currentValue;

			for (int i = 0; i < arrayVariables.length; i++) {

				String variable = arrayVariables[i];
				currentValue = (ScriptableObject) scope.get(variable, null);

				if (currentValue.isEmpty()) {

					ScriptableObject.putProperty(currentValue, variable, context.javaToJS(new Object(), scope));
					currentValue = (ScriptableObject) scope.get(variable, null);
				}

				// Se chegou na ultima variavel, executa
				if (i + 1 == arrayVariables.length - 1) {
					String finalVariable = arrayVariables[i + 1];
					ScriptableObject.putProperty(currentValue, finalVariable, context.javaToJS(nativeValue, scope));
					break;
				}
			}
		}
	}

	public Object executeJS(String jsCode) {
		Object result = context.evaluateString(scope, jsCode, "executeJS", 1,
				null);
		return result;
	}
}