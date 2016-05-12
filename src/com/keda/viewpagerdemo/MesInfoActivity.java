package com.keda.viewpagerdemo;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.keda.mode.MesInfoData;
import com.keda.utils.AUtils;
import com.keda.utils.Connector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MesInfoActivity extends Activity {

	private TextView title, time, source, author;
	private WebView webView;

	private long id;

	private MesInfoData data;

	private WebSettings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_info);

		title = (TextView) findViewById(R.id.titleInfoId);
		time = (TextView) findViewById(R.id.timeInfoId);
		source = (TextView) findViewById(R.id.sourceInfoId);
		author = (TextView) findViewById(R.id.authorId);

		webView = (WebView) findViewById(R.id.webView1);

		settings = webView.getSettings();

		id = getIntent().getLongExtra("id", 0);

		settings = webView.getSettings();

		new MyInfoAsyncTask().execute(String
				.format(Connector.DATA_INFO_URL, id));

	}

	class MyInfoAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			byte[] bytes = AUtils.download(params[0], null);

			if (bytes != null) {
				try {
					return new String(bytes, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				try {
					JSONObject obj = new JSONObject(result);
					JSONObject obj2 = obj.getJSONObject("data");

					data = JSON.parseObject(obj2.toString(), MesInfoData.class);

					title.setText(data.getTitle());
					time.setText(data.getCreate_time());
					source.setText(data.getSource());
					author.setText(data.getAuthor());

					webView.loadData(data.getWap_content(),
							"text/html;charset=utf-8", "UTF-8");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			super.onPostExecute(result);
		}

	}

	@Override
	//创建一个系统菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//把man里面内容填充到menu里
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	//给系统菜单创建一个监听器
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.shareId:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			intent.putExtra(Intent.EXTRA_TEXT, "分享给您一片文章《" + data.getTitle()
					+ "》" + data.getWeiboUrl());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, getTitle()));
			break;

		case R.id.gobackId:
			finish();
			break;

		case R.id.oneId:
			settings.setTextSize(WebSettings.TextSize.SMALLEST);
			break;
		case R.id.twoId:
			settings.setTextSize(WebSettings.TextSize.SMALLER);
			break;
		case R.id.threeId:
			settings.setTextSize(WebSettings.TextSize.NORMAL);
			break;
		case R.id.fourId:
			settings.setTextSize(WebSettings.TextSize.LARGER);
			break;
		case R.id.fiveId:
			settings.setTextSize(WebSettings.TextSize.LARGEST);
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
