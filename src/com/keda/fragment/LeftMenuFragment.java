package com.keda.fragment;

import com.keda.viewpagerdemo.LoginActivity;
import com.keda.viewpagerdemo.R;
import com.keda.viewpagerdemo.UserBackActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class LeftMenuFragment extends Fragment implements OnClickListener {

	private AlertDialog aboutDialog, dialog;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menu_left, null);
		view.findViewById(R.id.textView1).setOnClickListener(this);
		view.findViewById(R.id.textView2).setOnClickListener(this);
		view.findViewById(R.id.textView3).setOnClickListener(this);
		view.findViewById(R.id.textView4).setOnClickListener(this);
		view.findViewById(R.id.textView5).setOnClickListener(this);
		return view;

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initDialog();
	}

	private void initDialog() {

		aboutDialog = new AlertDialog.Builder(getActivity())
				.setTitle("关于")
				.setMessage(
						"  这个项目，使用了：ViewPager、Fragment、抽屉菜单、ActionBar，实现了欢迎界面、Fragment的左右滑动、侧滑菜单。")
				.create();

		dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")
				.setMessage("是否确定退出？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				}).setNegativeButton("取消", null).create();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.textView1:

			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);

			break;
		case R.id.textView2:

			Intent intent2 = new Intent(getActivity(), UserBackActivity.class);
			startActivity(intent2);
			break;

		case R.id.textView3:

			aboutDialog.show();
			break;
		case R.id.textView4:
			Toast.makeText(getActivity(), "当前已是最新版本！", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.textView5:
			dialog.show();
			break;

		}

		DrawerLayout dra = (DrawerLayout) getActivity().findViewById(
				R.id.drawerLayoutId);
		dra.closeDrawers();
	}

}
