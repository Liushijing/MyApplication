package com.keda.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UserBackActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_back);
	}

	public void post(View view) {
	
			Toast.makeText(UserBackActivity.this, "感谢您的建议，我们会积极改进！！",
					Toast.LENGTH_SHORT).show();
			finish();
		
	}
}
