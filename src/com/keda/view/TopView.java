package com.keda.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.keda.mode.InfoTop;
import com.keda.utils.FileUtils;
import com.keda.view.TopUtils.Callback;
import com.keda.viewpagerdemo.R;

/**
 * 自定义控件，用于显示tops图片
 * 
 * @author apple
 * 
 */
public class TopView extends FrameLayout {

	private ViewPager viewPager;
	private List<ImageView> views;

	private TextView titleTv;

	private LinearLayout navLayout;

	private List<InfoTop> tops;

	private Handler handler = new Handler();

	public TopView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_tops, this, true);

		viewPager = (ViewPager) findViewById(R.id.topViewPagerId);
		titleTv = (TextView) findViewById(R.id.titleId);

		navLayout = (LinearLayout) findViewById(R.id.topnavLayoutId);
	}

	public void loadUrl(String url) {

		TopUtils.download(url, new Callback() {
			@Override
			public void response(String url, byte[] data) {

				try {
					String json = new String(data, "utf-8");
					JSONObject obj = new JSONObject(json);
					JSONArray array = obj.getJSONArray("data");

					tops = JSON.parseArray(array.toString(), InfoTop.class);

					views = new ArrayList<ImageView>();
					ImageView imageView = null;
					for (InfoTop top : tops) {
						imageView = new ImageView(getContext());
						imageView.setTag(top.getImage());
						imageView.setScaleType(ScaleType.CENTER_CROP);

						views.add(imageView);
					}
					ImageView img = null;
					for (int i = 0; i < 3; i++) {
						img = new ImageView(getContext());

						if (i == 0) {
							img.setImageResource(R.drawable.page_now);
						} else {
							img.setImageResource(R.drawable.page);
						}
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);

						params.setMargins(20, 0, 20, 0);

						navLayout.addView(img, params);

						img.setTag(i);
						img.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								viewPager.setCurrentItem((Integer) v.getTag());
							}
						});

					}

					viewPager.setAdapter(new MyPagerAdapter());

					viewPager
							.setOnPageChangeListener(new MyOnPageChangeListener());

					loadImgs(); // 下载所有图片

					loopImgs();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public boolean isCanceled(String url) {
				return false;
			}
		});

	}

	// 循环播放图片
	private void loopImgs() {
		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (i >= views.size())
						i = 0;

					final int index = i++;

					handler.post(new Runnable() {
						@Override
						public void run() {
							viewPager.setCurrentItem(index);
							titleTv.setText(tops.get(index).getTitle());
						}
					});

				}
			}
		}.start();
	}

	private void loadImgs() {
		int i = 0;
		for (InfoTop top : tops) {

			Bitmap bitmap = FileUtils.getImage(top.getImage());
			if (bitmap != null) {
				views.get(i).setImageBitmap(bitmap);
			} else {
				TopUtils.download(top.getImage(), new Callback() {

					@Override
					public void response(String url, byte[] data) {
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);

						ImageView imgView = (ImageView) viewPager
								.findViewWithTag(url);
						if (imgView != null) {
							imgView.setImageBitmap(bitmap);
						}

						FileUtils.saveImage(url, bitmap);
					}

					@Override
					public boolean isCanceled(String url) {
						return false;
					}
				});
			}

			i++;
		}
	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {

			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
		}

	}

	class MyOnPageChangeListener implements OnPageChangeListener {

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
			for (int i = 0, len = navLayout.getChildCount(); i < len; i++) {
				image = (ImageView) navLayout.getChildAt(i);

				if (i == arg0) {
					image.setImageResource(R.drawable.page_now);
				} else
					image.setImageResource(R.drawable.page);
			}
		}

	}

}
