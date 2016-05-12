package com.keda.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtils {

	// 创建保存目录
	public static final File CHACH_DIR = new File(
			Environment.getExternalStorageDirectory(), "heibeikeda");

	// 判断SD卡是否可用
	public static boolean isMethed() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	// 获得图片名字
	public static String getImageName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);

	}

	// 保存图片
	public static void saveImage(String url, Bitmap bitmap) {
		if (!isMethed())
			return;
		// 判断保存目录是否存在，不存在就创建一个
		if (!CHACH_DIR.exists())
			CHACH_DIR.mkdir();

		// 创建图片文件的对象
		File imageFile = new File(CHACH_DIR, getImageName(url));
		try {
			FileOutputStream fos = new FileOutputStream(imageFile);

			bitmap.compress(CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 取出图片
	public static Bitmap getImage(String url) {
		if (!isMethed())
			return null;

		File imageFile = new File(CHACH_DIR, getImageName(url));

		if (imageFile.exists()) {
			return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		}

		return null;

	}

}
