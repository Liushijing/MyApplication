package com.keda.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userName, pasword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		userName = (EditText) findViewById(R.id.userNameId);
		pasword = (EditText) findViewById(R.id.userPaswId);
	}

	public void login(View view) {

		String name = userName.getText().toString();
		String pas = pasword.getText().toString();

		if (name.equals("") || pas.equals("")) {
			Toast.makeText(LoginActivity.this, "用户名或密码不能为空！",
					Toast.LENGTH_SHORT).show();
		} else {
			if (name.equals("zhangsan") && pas.equals("123456")) {
				Toast.makeText(LoginActivity.this, "登录成功。", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else {
				Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新输入。",
						Toast.LENGTH_SHORT).show();
			}
		}

		userName.setText("");
		pasword.setText("");

	}

}
