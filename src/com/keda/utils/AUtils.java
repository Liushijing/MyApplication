package com.keda.utils;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;



public class AUtils {

	public static byte[] download(String url, ImageTask.Callback callback) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			HttpResponse resp = client.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(0);

				HttpEntity entity = resp.getEntity();

				InputStream is = entity.getContent();
				byte[] buffer = new byte[5 * 1024]; 
				int len = 0;

				
				while ((len = is.read(buffer)) != -1) {

					byteArrayBuffer.append(buffer, 0, len);

					if (callback != null && callback.isCallback(url)) {
						return null;
					}

					Thread.sleep(100);
				}

				return byteArrayBuffer.toByteArray();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
