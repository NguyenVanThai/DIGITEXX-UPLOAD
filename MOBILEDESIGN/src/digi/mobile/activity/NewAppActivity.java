package digi.mobile.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import digi.mobile.building.AndroidMultiPartEntity.ProgressListener;
import digi.mobile.adapter.PdfListAdapter;
import digi.mobile.building.AndroidMultiPartEntity;
import digi.mobile.building.ConnectionDetector;
import digi.mobile.building.DigiCompressFile;
import digi.mobile.util.Config;
import digi.mobile.util.Constant;
import digi.mobile.util.Operation;

public class NewAppActivity extends Activity {
	// declare listview for PDF files display
	ListView lvPdf;
	private Operation operation;
	private String nameCustomer;
	private String nameCategory;
	private String pathCustomer;

	private String pathZip = null;

	public List<String> map = null;

	public ArrayList<File> arrPdfFile;

	String user = "";

	long totalSize = 0;

	Dialog dialog;
	AnimationDrawable animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_app);

		// get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

		// init listview
		lvPdf = (ListView) findViewById(R.id.lvPdf);

		operation = new Operation();
		nameCustomer = Constant.NAME_CUSTOMER;

		setTitle(nameCustomer);

		// pathCustomer = Constant.PathMyFolder + File.separator + nameCustomer;
		pathCustomer = Constant.PATH + File.separator + nameCustomer;
		createFolderCustomer();

	}

	/*
	 * (using split action bar)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.actionbar_newapp_activity, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constant.REQUEST_CODE_DUCOMENT_TYPE_ACTIVITY:
				Intent intent = new Intent(NewAppActivity.this,
						NewAppDetailActivity.class);
				startActivity(intent);
				finish();
				break;
			}

		}
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
		// id of up button on action bar
		case android.R.id.home:
			showHome();
			break;
		case R.id.itemHome:
			showHome();
			break;
		case R.id.itemUpload:

			dialog = new Dialog(NewAppActivity.this, R.style.Theme_D1NoTitleDim);
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
			animation = (AnimationDrawable) imageLoading.getBackground();

			arrPdfFile = checkChooseFdf();
			if (arrPdfFile.size() > 0) {
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... params) {
						// TODO Auto-generated method stub

						return pathZip = createZipFile(nameCustomer, arrPdfFile);
						// String pathsave = Constant
						// .getPathRoot(Constant.APP_FOLDER);
						// return pathZip = createZipFolder(pathCustomer,
						// pathsave, Constant.NAME_MY_FOLDER);
					}

					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						dialog.show();
						animation.start();

					}

					@Override
					protected void onPostExecute(String result) {
						// TODO Auto-generated method stub
						super.onPostExecute(result);
						if (result.length() > 0) {
							uploadHandle(result);

							animation.stop();
							dialog.dismiss();
						}

					}

				}.execute();

			} else {
				Toast.makeText(NewAppActivity.this, "Please choose file PDF",
						Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.itemDeselectAll:
			// deselectAll();
			break;
		case R.id.itemCheckAll:
			// checkAll();
			break;
		case R.id.itemNew:
			chooseDocument();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void uploadHandle(String pathZip) {
		// TODO Auto-generated method stub
		ConnectionDetector cnnDec = new ConnectionDetector(
				getApplicationContext());
		boolean isInternetPresent = cnnDec.isConnectingToInternet();
		if (isInternetPresent) {
			SharedPreferences sharedPerferences = getSharedPreferences(
					Constant.DIGI_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
			if (sharedPerferences.contains(Constant.FLAG_KEY)) {
				user = sharedPerferences.getString(Constant.USER_NAME, null);
				if (user != null) {
					uploadFileToServer(pathZip);
				}
			} else {
				Intent i = new Intent(this, LoginActivity.class);
				startActivityForResult(i, 30);
			}
		} else {
			animation.stop();
			dialog.dismiss();
			Toast.makeText(getApplicationContext(), "No Internet Connection",
					Toast.LENGTH_LONG).show();
		}
	}

	void showHome() {
		Intent intent = new Intent(NewAppActivity.this, DigiMobiActivity.class);
		startActivity(intent);
		finish();
	}

	private void chooseDocument() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(NewAppActivity.this,
				DocumentTypeActivity.class);
//		intent.putExtra(Constant.PATH_MY_FOLDER, pathCustomer);
		startActivityForResult(intent,
				Constant.REQUEST_CODE_DUCOMENT_TYPE_ACTIVITY);
	}

	private void checkAll() {
		// TODO Auto-generated method stub
		for (int i = 0; i < lvPdf.getCount(); i++) {
			CheckBox checkBox = (CheckBox) lvPdf.getChildAt(i).findViewById(
					R.id.checkBox1);
			checkBox.setChecked(true);
		}
	}

	private void deselectAll() {
		// TODO Auto-generated method stub
		for (int i = 0; i < lvPdf.getCount(); i++) {
			CheckBox checkBox = (CheckBox) lvPdf.getChildAt(i).findViewById(
					R.id.checkBox1);
			checkBox.setChecked(false);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showPdf();
	}

	private String createZipFile(String nameCustomer, ArrayList<File> arrPdfFile) {

		DigiCompressFile compressFile = new DigiCompressFile();
		try {
			// return compressFile.compressFilesToZip(arrPdfFile, "123456",
			// pathCustomer, nameCustomer + ".zip");
			// String pathSave = Constant.getPathRoot(Constant.APP_FOLDER);
			// return compressFile.compressFolderToZip(pathCustomer, pathSave,
			// Constant.NAME_MY_FOLDER + ".zip");
	
			String pathSave = Constant.PATH_MY_FOLDER_SAVE;

			

			return compressFile.compressFolderToZip(pathCustomer, pathSave,
					Constant.NAME_MY_FOLDER + ".zip");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String createZipFolder(String pathFolder, String pathSave,
			String nameFileZip) {
		DigiCompressFile compressFile = new DigiCompressFile();
		try {
			// return compressFile.compressFilesToZip(arrPdfFile, "123456",
			// pathCustomer, nameCustomer + ".zip");
			// String pathSave = Constant.getPathRoot(Constant.APP_FOLDER);
			return compressFile.compressFolderToZip(pathCustomer, pathSave,
					Constant.NAME_ZIP_FILE_UPLOAD + ".zip");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<File> checkChooseFdf() {
		// TODO Auto-generated method stub

		ArrayList<File> arr = new ArrayList<File>();
		for (int i = 0; i < lvPdf.getCount(); i++) {
			CheckBox checkBox = (CheckBox) lvPdf.getChildAt(i).findViewById(
					R.id.checkBox1);
			TextView txtView = (TextView) lvPdf.getChildAt(i).findViewById(
					R.id.item);
			if (checkBox.isChecked()) {
				File file = new File(pathCustomer + File.separator
						+ txtView.getText());

				arr.add(file);
			}
		}
		return arr;

	}

	private void showPdf() {
		// TODO Auto-generated method stub
		List<String> listPDF = operation.listImagebyCategory(pathCustomer,
				".pdf", "name");
		if (listPDF.size() > 0) {

			PdfListAdapter pdfListAdapter = new PdfListAdapter(
					NewAppActivity.this, convertListToArray(listPDF));
			pdfListAdapter.notifyDataSetChanged();
			lvPdf.setAdapter(pdfListAdapter);

		}
	}

	private void createFolderCustomer() {
		// TODO Auto-generated method stub
		// pathCustomer = Constant.getPathRoot(Constant.APP_FOLDER
		// + File.separator + nameCustomer);

		operation.createDirIfNotExists(pathCustomer, 1);
	}

	private String[] convertListToArray(List<String> listFolder) {
		ArrayList<String> list = (ArrayList<String>) listFolder;
		return list.toArray(new String[list.size()]);

	}

	private void uploadFileToServer(final String pathZip) {
		new AsyncTask<Void, Integer, String>() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub

				super.onPreExecute();
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return uploadFile();
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
				// progressBar.setVisibility(View.VISIBLE);
				// progressBar.setProgress(values[0]);
				// txtPercentage.setText(String.valueOf(values[0]) + "%");

			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				animation.stop();
				dialog.dismiss();
				Toast.makeText(NewAppActivity.this, "Upload successful!",
						Toast.LENGTH_LONG).show();
			}

			private String uploadFile() {
				// TODO Auto-generated method stub
				String responseString = null;
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(Config.FILE_UPLOAD_URL);

				try {
					AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
							new ProgressListener() {

								@Override
								public void transferred(long num) {
									// TODO Auto-generated method stub
									publishProgress((int) num);
								}
							});

					// filePath = map.get(finishedUpload);
					File sourceFile = new File(pathZip);
					entity.addPart("myFile", new FileBody(sourceFile));
					entity.addPart("userId", new StringBody(user));
					entity.addPart("appFolder", new StringBody(nameCustomer));

					totalSize = entity.getContentLength();
					httpPost.setEntity(entity);
					HttpResponse response = httpClient.execute(httpPost);
					HttpEntity r_entity = response.getEntity();
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						// Server response
						responseString = EntityUtils.toString(r_entity);
					} else {
						responseString = "Error occurred! Http Status Code: "
								+ statusCode;
					}

				} catch (ClientProtocolException e) {
					responseString = e.toString();
				} catch (IOException e) {
					responseString = e.toString();
				}

				return responseString;
			}
		}.execute();
	}

}
