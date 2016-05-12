package com.keda.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import com.keda.utils.SPUtils;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private List<ImageView> data;

	private LinearLayout linear;

	private ViewPager viewPager;

	private int[] resId = { R.drawable.slide1, R.drawable.slide2,
			R.drawable.slide3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		linear = (LinearLayout) findViewById(R.id.linear);
		// 初始化数据源
		initView();

		// 绑定适配器
		viewPager.setAdapter(new MyPagerAdapter());

		// 绑定监听器

		viewPager.setOnPageChangeListener(new MyPagerChangeListener());

	}

	private void initView() {

		data = new ArrayList<ImageView>();
		ImageView imageView = null;
		ImageView imageView2 = null;

		for (int i = 1; i < 4; i++) {
			imageView = new ImageView(this);

			imageView.setImageResource(resId[i - 1]);

			data.add(imageView);

			if (i == 3) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								InfoActivity.class);
						startActivity(intent);
						
						SPUtils.setSp(getApplicationContext(), "isEnter", true);
						finish();
					}
				});
			}

			imageView2 = new ImageView(this);

			if (i == 1) {
				imageView2.setImageResource(R.drawable.page_now);
			} else {
				imageView2.setImageResource(R.drawable.page);
			}

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			params.setMargins(20, 0, 20, 0);

			linear.addView(imageView2, params);

			imageView2.setTag(i - 1);// 将当前位置作为ImageView的标签

			imageView2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					int posion = (Integer) v.getTag();

					viewPager.setCurrentItem(posion);

				}
			});
		}
	}

	/*
	 * viewpager的适配器
	 */
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(data.get(position));
			return data.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(data.get(position));
		}
	}

	/**
	 * 自定义Viewpager的监听器
	 * 
	 * 
	 * @author L.Aaron
	 * 
	 * 
	 *         2015-12-4
	 * 
	 */
	class MyPagerChangeListener implements OnPageChangeListener {

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

			ImageView image = null;

			for (int i = 0; i < linear.getChildCount(); i++) {

				image = (ImageView) linear.getChildAt(i);

				if (i == arg0) {
					image.setImageResource(R.drawable.page_now);
				} else {
					image.setImageResource(R.drawable.page);
				}

			}

		}

	}
}
