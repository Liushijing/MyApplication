package com.keda.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.keda.mode.InfoData;

import android.os.AsyncTask;

public class JsonTask extends AsyncTask<String, Void, List<InfoData>> {

	private Callback callback;

	public JsonTask(Callback callback) {
		super();
		this.callback = callback;
	}

	@Override
	protected List<InfoData> doInBackground(String... params) {


		byte[] bytes = AUtils.download(params[0], null);


		try {
			String json = new String(bytes, "UTF-8");
			JSONObject obj = new JSONObject(json);
			JSONArray array = obj.getJSONArray("data");

			List<InfoData> list = JSON.parseArray(array.toString(),
					InfoData.class);

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<InfoData> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			callback.response(result);
		}
	}

	public interface Callback {
		public void response(List<InfoData> list);
	}

}
