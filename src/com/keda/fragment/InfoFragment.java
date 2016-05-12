package com.keda.fragment;

import java.util.ArrayList;
import java.util.List;

import com.keda.adapter.MyAdapter;
import com.keda.mode.InfoData;
import com.keda.utils.Connector;
import com.keda.utils.JsonTask;
import com.keda.view.TopView;
import com.keda.viewpagerdemo.MesInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class InfoFragment extends ListFragment {

	private List<InfoData> datas;
	private MyAdapter adapter;

	private int currPage = 1;
	private boolean isLoading = false;

	private String method;
	private int type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		method = getArguments().getString("method");
		type = getArguments().getInt("type");

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		datas = new ArrayList<InfoData>();

		Log.i("nice", "datas==" + datas);

		adapter = new MyAdapter(datas, getActivity());

		if (type == 0) {

			TopView topView = new TopView(getActivity());
			topView.loadUrl(Connector.CBK_TOP);

			// 将TopView增加到ListView的头部显示
			getListView().addHeaderView(topView);

		}
		setListAdapter(adapter);

		getListView().setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount == totalItemCount
						&& isLoading) {
					loadData();
				}
			}
		});

		loadData();
	}

	private void loadData() {

		isLoading = true;
		new JsonTask(new JsonTask.Callback() {

			@Override
			public void response(List<InfoData> list) {
				datas.addAll(list);
				adapter.notifyDataSetChanged();


				isLoading = false;
			}
		}).execute(String.format(Connector.DATA_URL, method, type, currPage++));

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getActivity(), MesInfoActivity.class);

		intent.putExtra("id", id);

		startActivity(intent);
	}
}
