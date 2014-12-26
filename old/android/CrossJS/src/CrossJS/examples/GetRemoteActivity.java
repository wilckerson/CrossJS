package CrossJS.examples;

import CrossJS.CrossJS;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GetRemoteActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_remote);
		
		CrossJS.getInstance().executeJS("$http.get('http://jsonip.com',{ p1 : 'p1Value'},function(response){ console.log(response); Controller.view.showMessage(response); },function(){});");
		CrossJS.getInstance().executeJS("$http.post('http://posttestserver.com/post.php',{ p1 : 'p1Value'},function(response){ console.log(response); Controller.view.showMessage(response); },function(){});");
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_remote, menu);
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
