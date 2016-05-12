package com.keda.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;
import com.keda.fragment.InfoFragment;
import com.keda.fragment.LeftMenuFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

public class InfoActivity extends ActionBarActivity {

	private ActionBar actionBar;
	private List<Fragment> datas;
	private ViewPager infoView;

	private FragmentManager manager;
	private FragmentTransaction tra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		infoView = (ViewPager) findViewById(R.id.infoViewPager);
		// 初始化ActionBar
		initActionBar();

		// 初始化数据源
		initFragment();

		// ViewPager绑定适配器
		infoView.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));

		// viewPager的监听器
		infoView.setOnPageChangeListener(new MyPagerChangeLis());

		// 初始化侧滑菜单
		manager = getSupportFragmentManager();
		tra = manager.beginTransaction();
		tra.replace(R.id.left_menulayout, new LeftMenuFragment());
		tra.commit();
		
		infoView.setOffscreenPageLimit(5);
	}

	private void initFragment() {

		datas = new ArrayList<Fragment>();

		InfoFragment cf = null;
		Bundle args = null;

		for (int i = 0; i < 5; i++) {
			cf = new InfoFragment();
			args = new Bundle();
			args.putString("method", "news.getListByType");

			switch (i) {
			case 0:
				args.putInt("type", 0);
				args.putString("method", "news.getHeadlines");
				break;
			case 1:
				args.putInt("type", 16);
				break;
			case 2:
				args.putInt("type", 52);
				break;
			case 3:
				args.putInt("type", 53);
				break;
			case 4:
				args.putInt("type", 54);
				break;
			}
			cf.setArguments(args);

			datas.add(cf);
		}


	}

	private void initActionBar() {
		actionBar = getSupportActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("头条").setTag(0)
				.setTabListener(new MyTabListener()));

		actionBar.addTab(actionBar.newTab().setText("百科").setTag(1)
				.setTabListener(new MyTabListener()));
		actionBar.addTab(actionBar.newTab().setText("咨询").setTag(2)
				.setTabListener(new MyTabListener()));
		actionBar.addTab(actionBar.newTab().setText("经营").setTag(3)
				.setTabListener(new MyTabListener()));
		actionBar.addTab(actionBar.newTab().setText("数据").setTag(4)
				.setTabListener(new MyTabListener()));
	}

	class MyTabListener implements TabListener {

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			infoView.setCurrentItem(arg0.getPosition());
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

		}

	}

	// viewPager的适配器
	class MyFragmentAdapter extends FragmentStatePagerAdapter {

		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return datas.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

	}

	class MyPagerChangeLis implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			actionBar.selectTab(actionBar.getTabAt(arg0));
		}
	}
}
