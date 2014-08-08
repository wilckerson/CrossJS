package CrossJS.examples;

import CrossJS.CrossJS;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SumActivity extends ActionBarActivity implements SumView {

	EditText editText1;
	EditText editText2;
	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sum);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		btn1 = (Button) findViewById(R.id.button1);

		try {

			// Lendo e executando o arquivo javascript
			CrossJS.getInstance().loadExecuteJSFile("js/SumController.js", getAssets());
			
			//Conectando o javascript com a interface nativa
			CrossJS.getInstance().setJSVariable("SumController.view", this);
			
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				CrossJS.getInstance().executeJS("SumController.calculate();");

			}
		});

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
