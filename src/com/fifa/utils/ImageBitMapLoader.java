package com.fifa.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.fifa.R;



public class ImageBitMapLoader {

	private boolean isToResize;

	private LayoutInflater infaltor;

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;

	public ImageBitMapLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
		infaltor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	int stub_id = R.drawable.ic_launcher;

	public void DisplayImage(String url, int loadingImageId, ImageView imageView,
			boolean isToResize) {

		this.isToResize = isToResize;
		stub_id = loadingImageId;
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else {
			queuePhoto(url, imageView);
			imageView.setImageResource(loadingImageId);

		}
	}

	

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Util.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			if (isToResize) {

				final int IMAGE_MAX_SIZE = 630;
				File file = null;
				FileInputStream fis;

				BitmapFactory.Options opts;
				int resizeScale;
				Bitmap bmp;
				file = new File(f.toString());

				// This bit determines only the width/height of the bitmap
				// without loading the contents
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				fis = new FileInputStream(file);
				BitmapFactory.decodeStream(fis, null, opts);

				fis.close();

				// Find the correct scale value. It should be a power of 2
				resizeScale = 1;

				if (opts.outHeight > IMAGE_MAX_SIZE
						|| opts.outWidth > IMAGE_MAX_SIZE) {
					resizeScale = (int) Math.pow(
							2,
							(int) Math.round(Math.log(IMAGE_MAX_SIZE
									/ (double) Math.max(opts.outHeight,
											opts.outWidth))
									/ Math.log(0.5)));
				}
				// Load pre-scaled bitmap
				opts = new BitmapFactory.Options();
				opts.inSampleSize = resizeScale;
				fis = new FileInputStream(file);
				bmp = BitmapFactory.decodeStream(fis, null, opts);

				fis.close();

				return bmp;

				/*
				 * try { BitmapFactory.Options options = new
				 * BitmapFactory.Options(); options.inSampleSize = 3; return
				 * BitmapFactory.decodeFile(f.toString(), options); } catch
				 * (Exception e) { Log.e("ImageBitMapLoader", e.toString()); }
				 */

			} else {
				// decode image size
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(new FileInputStream(f), null, o);

				// Find the correct scale value. It should be the power of 2.
				final int REQUIRED_SIZE = 70;
				int width_tmp = o.outWidth, height_tmp = o.outHeight;
				int scale = 1;
				while (true) {
					if (width_tmp / 2 < REQUIRED_SIZE
							|| height_tmp / 2 < REQUIRED_SIZE)
						break;
					width_tmp /= 2;
					height_tmp /= 2;
					scale *= 2;
				}

				// decode with inSampleSize
				BitmapFactory.Options o2 = new BitmapFactory.Options();
				o2.inSampleSize = scale;
				return BitmapFactory.decodeStream(new FileInputStream(f), null,
						o2);

			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				photoToLoad.imageView.setImageBitmap(bitmap);
			} else {
				photoToLoad.imageView.setImageResource(stub_id);
			}

		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
