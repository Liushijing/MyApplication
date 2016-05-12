package com.keda.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {

	private String url = null;
	private Callback callback;

	public ImageTask(Callback callback) {
		super();
		this.callback = callback;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		url = params[0];

		byte[] bytes = AUtils.download(url, callback);
		if (bytes != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
					bytes.length);

			FileUtils.saveImage(url, bitmap);

			return bitmap;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			callback.response(url, result);

		}
	}

	public interface Callback {
		public void response(String url, Bitmap result);

		public boolean isCallback(String url);// 是否取消图片下载
	}

}
