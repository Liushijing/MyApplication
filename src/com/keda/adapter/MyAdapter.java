package com.keda.adapter;

import java.util.List;

import com.keda.mode.InfoData;
import com.keda.utils.FileUtils;
import com.keda.utils.ImageTask;
import com.keda.viewpagerdemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private List<InfoData> datas;
	private Context context;

	public MyAdapter(List<InfoData> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return datas.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_adapter, null);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.titleId);
			viewHolder.time = (TextView) convertView.findViewById(R.id.timeId);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.nickNameId);
			viewHolder.source = (TextView) convertView
					.findViewById(R.id.sourceId);

			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.imageId);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.image.setImageResource(R.drawable.ic_launcher);
		}

		InfoData data = datas.get(position);


		viewHolder.title.setText(data.getTitle());
		viewHolder.source.setText("来源："+data.getSource());
		viewHolder.time.setText("时间："+data.getCreate_time());
		viewHolder.name.setText("作者："+data.getNickname());

		String imageUrl = data.getWap_thumb();
		viewHolder.image.setTag(imageUrl);
		Bitmap bitmap = FileUtils.getImage(imageUrl);
		if (bitmap != null) {
			viewHolder.image.setImageBitmap(bitmap);
		} else {
			new ImageTask(new ImageTask.Callback() {

				@Override
				public void response(String url, Bitmap result) {
					ImageView image = (ImageView) parent.findViewWithTag(url);

					if (image != null) {
						image.setImageBitmap(result);
					}
				}

				@Override
				public boolean isCallback(String url) {
					View view = parent.findViewWithTag(url);
					return view == null;
				}
			}).execute(imageUrl);
		}

		return convertView;
	}

	public class ViewHolder {
		TextView title, source, time, name;
		ImageView image;
	}

}
