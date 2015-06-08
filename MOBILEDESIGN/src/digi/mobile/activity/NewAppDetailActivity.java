package digi.mobile.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import paul.arian.fileselector.FileSelectionActivity;
import android.R.style;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import digi.mobile.adapter.AdapterGridView;
import digi.mobile.building.ImageItem;
import digi.mobile.util.Constant;
import digi.mobile.util.Operation;

public class NewAppDetailActivity extends Activity {

	// declare gridview for images display
	GridView gridView;
	private String nameShortDucoment;
	private String pathCustomer;
	private String nameCustomer;
	Operation operation;
	public static final int REQUEST_CODE_GALLERY = 0x1;
	public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
	public static final int REQUEST_CODE_OPTION = 0x3;
	public static Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_app_detail);

		operation = new Operation();
		gridView = (GridView) findViewById(R.id.gridview);

		// get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

		// get Intent
		// Intent intent = getIntent();
		// nameShortDucoment = intent.getStringExtra(Constant.NAME_DUCOMENT);

		nameShortDucoment = Constant.NAME_SHORT_DUCOMENT;

		nameCustomer = Constant.NAME_CUSTOMER;
		// pathCustomer = Constant.getPathRoot(Constant.APP_FOLDER)
		// + File.separator + nameCustomer;

		pathCustomer = Constant.PATH + File.separator + nameCustomer;

		// pathCustomer = Constant.getPathRoot(Constant.APP_FOLDER
		// + File.separator + Constant.NAME_MY_FOLDER + File.separator
		// + nameCustomer);
		setTitle(nameShortDucoment);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showImage();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case REQUEST_CODE_TAKE_PICTURE:
			showOptionActiviy(Constant.fileFinal.getPath());

			break;
		case REQUEST_CODE_GALLERY:
			String path = data.getStringExtra("path");

			showOptionActiviy(path);
			break;

		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	private void showOptionActiviy(String path) {
		// TODO Auto-generated method stub
		// Display display = getWindowManager().getDefaultDisplay();
		//
		// int width = display.getWidth() / 2;
		// int height = display.getHeight() / 2;
		Bitmap bitmap = Constant.getBitmap(path);
		Constant.updateBitmap(bitmap);
		Intent intent = new Intent(NewAppDetailActivity.this,
				OptionActivity.class);
		intent.putExtra(Constant.PATH_IMAGE, path);
		startActivity(intent);
		finish();
	}

	private void showImage() {
		// TODO Auto-generated method stub
		ArrayList<ImageItem> arrayImageItem = operation.getData(pathCustomer,
				nameShortDucoment);
		if (arrayImageItem != null && arrayImageItem.size() > 0) {

			AdapterGridView adapterGridView = new AdapterGridView(
					NewAppDetailActivity.this, arrayImageItem);
			gridView.setAdapter(adapterGridView);
		}
	}

	/*
	 * (using split action bar)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// handling clicks on action items
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.action_newappdetail_activity, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * (handling clicks on action items)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent intent = new Intent(NewAppDetailActivity.this,
					NewAppActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.itemHome:
			Intent intentHome = new Intent(NewAppDetailActivity.this,
					DigiMobiActivity.class);
			startActivity(intentHome);
			finish();
			break;
		case R.id.itemFinish:
			createFilePDF(pathCustomer, nameShortDucoment);
			// Intent intentNewApp = new Intent(NewAppDetailActivity.this,
			// NewAppActivity.class);
			// startActivity(intentNewApp);
			// finish();
			break;
		case R.id.itemAddPicture:
			switch (Constant.TYPE) {
			case 0:
				showGallery();
				break;
			case 1:
			case 2:
				takePhoto();
				break;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void createFilePDF(final String pathCustomer,
			final String nameShortDucoment) {

		dialog = new Dialog(NewAppDetailActivity.this,
				R.style.Theme_D1NoTitleDim);
		dialog.setContentView(R.layout.dialog_loading_animation);
		dialog.setCanceledOnTouchOutside(false);

		// init TextViewLoading and ImageLoading
		TextView txtLoading = (TextView) dialog
				.findViewById(R.id.textViewLoading);
		txtLoading.setText("Loading...");
		ImageView imageLoading = (ImageView) dialog
				.findViewById(R.id.imageViewLoading);
		imageLoading.setBackgroundResource(R.drawable.animation_loading);

		// using Animation for ImageLoading
		final AnimationDrawable animation = (AnimationDrawable) imageLoading
				.getBackground();

		new AsyncTask<Void, Integer, Boolean>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				if (dialog.isShowing()) {
					dialog.cancel();
				} else {
					dialog.show();
					animation.start();
				}
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				List<String> listImage = operation.listImagebyCategory(
						pathCustomer, nameShortDucoment, "path");

				if (listImage.size() <= 0) {
					return false;
				}

				return operation.createPDF(pathCustomer, listImage,
						nameShortDucoment);

			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result == true) {
					animation.stop();
					dialog.cancel();
					Intent intentNewApp = new Intent(NewAppDetailActivity.this,
							NewAppActivity.class);
					startActivity(intentNewApp);
					finish();
					Toast.makeText(NewAppDetailActivity.this,
							nameShortDucoment + ".pdf" + " has created",
							Toast.LENGTH_SHORT).show();
				}

			}

		}.execute();

		// String path = pathCustomer + File.separator + nameCustomer + ".pdf";
		// MyAsynTask myAsynTask = new MyAsynTask(path);
		// myAsynTask.execute();

	}

	private void takePhoto() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			Uri mImageCaptureUri = null;
			if (checkStorage()) {

				mImageCaptureUri = Uri.fromFile(getOutputMediaFile());
			} else {
				// mImageCaptureUri = CONTENT_URI;
				Toast.makeText(NewAppDetailActivity.this, "Can't create file!",
						Toast.LENGTH_SHORT).show();
			}
			intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
			startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);

		} catch (ActivityNotFoundException e) {
			Log.d(Constant.TAG, "Can't take Picture");
		}
	}

	private File getOutputMediaFile() {
		// TODO Auto-generated method stub
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mFile = new File(pathCustomer + File.separator + nameShortDucoment
				+ "_" + timeStamp + ".jpg");

		Constant.fileFinal = mFile;
		return Constant.fileFinal;
	}

	private void showGallery() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getBaseContext(),
				FileSelectionActivity.class);
		Constant.fileFinal = getOutputMediaFile();
		startActivityForResult(intent, REQUEST_CODE_GALLERY);
	}

	private boolean checkStorage() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	class MyAsynTask extends AsyncTask<Void, Void, Void> {
		Dialog dialog;
		TextView txtLoading;
		ImageView imageLoading;
		AnimationDrawable animation;

		String path;

		public MyAsynTask(String path) {
			// TODO Auto-generated constructor stub
			this.path = path;
			dialog = new Dialog(NewAppDetailActivity.this,
					R.style.Theme_D1NoTitleDim);
			dialog.setContentView(R.layout.dialog_loading_animation);
			dialog.setCanceledOnTouchOutside(false);
			// init TextViewLoading and ImageLoading
			txtLoading = (TextView) dialog.findViewById(R.id.textViewLoading);
			txtLoading.setText("Loading...");
			imageLoading = (ImageView) dialog
					.findViewById(R.id.imageViewLoading);
			imageLoading.setBackgroundResource(R.drawable.animation_loading);

			// using Animation for ImageLoading
			animation = (AnimationDrawable) imageLoading.getBackground();
			animation.start();
			dialog.show();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// while (true) {
			// if (new File(path).exists()) {
			// publishProgress();
			// break;
			// }
			// }
			for (int i = 0; i < 100; i++) {
				// SystemClock.sleep(100);
			}
			return null;

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub

			dialog.dismiss();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}

	}
}
