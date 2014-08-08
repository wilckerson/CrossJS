package CrossJS;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import android.util.Log;

public class CrossJSErrorReporter implements ErrorReporter {

	@Override
	public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
		String msg = String.format("Message: %s source: %s code: %s line: %d lineOffset %d", message, sourceName, lineSource, line, lineOffset);
		Log.v("Warning", msg);
	}

	@Override
	public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
		String msg = String.format("Message: %s source: %s code: %s line: %d lineOffset %d", message, sourceName, lineSource, line, lineOffset);
		Log.v("Error", msg);
	}

	@Override
	public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
		String msg = String.format("Message: %s source: %s code: %s line: %d lineOffset %d", message, sourceName, lineSource, line, lineOffset);
		Log.v("RuntimeError", msg);
		return null;
	}
}