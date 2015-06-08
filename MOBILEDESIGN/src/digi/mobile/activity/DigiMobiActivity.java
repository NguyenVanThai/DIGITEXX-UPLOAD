package digi.mobile.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import digi.mobile.building.BroadcastService;
import digi.mobile.util.Constant;
import digi.mobile.util.Operation;

public class DigiMobiActivity extends Activity implements OnClickListener {
	// declare button
	private Button btnGallery, btnSupplement, btnCamera, btnUpload,
			btnCreateFolder;
	private Button btnProfile, btnHelp, btnAbout;
	private Button btnCreate;

	// declare object Operation
	private Operation operation;

	// Path of Application
	private String appPath;

	// Path of Sales
	private String pathMyFolder = "";

	// Dialog Loading
	private ProgressDialog dialog;

	// Declare the service for send require from device to server
	Intent intentService;

	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_digi_mobi);
		// init button
		btnGallery = (Button) findViewById(R.id.gallery);
		btnSupplement = (Button) findViewById(R.id.supplement);
		btnCamera = (Button) findViewById(R.id.camera);
		btnUpload = (Button) findViewById(R.id.upload);
		btnProfile = (Button) findViewById(R.id.profile);
		btnHelp = (Button) findViewById(R.id.help);
		btnAbout = (Button) findViewById(R.id.about);
		btnCreateFolder = (Button) findViewById(R.id.btnCreateFolder);

		// set onclick for button
		btnGallery.setOnClickListener(this);
		btnSupplement.setOnClickListener(this);
		btnCamera.setOnClickListener(this);
		btnUpload.setOnClickListener(this);
		btnProfile.setOnClickListener(this);
		btnHelp.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
		btnCreateFolder.setOnClickListener(this);

		// init object operation
		operation = new Operation();

		// init Service
		intentService = new Intent(DigiMobiActivity.this,
				BroadcastService.class);

		// create folder of application
		if (operation.createDirIfNotExists(Constant.APP_FOLDER, 0)) {
			appPath = Constant.getPathRoot(Constant.APP_FOLDER);
		} else {
			Toast.makeText(DigiMobiActivity.this,
					getString(R.string.create_folder_error), Toast.LENGTH_SHORT)
					.show();
		}

	}

	/** The broadcast receiver for receiver message from the server */
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// super.onResume();
		// startService(intentService);
		// registerReceiver(broadcastReceiver, new IntentFilter(
		// BroadcastService.BROADCAST_ACTION));

		// Check folder staff
		if (Constant.NAME_MY_FOLDER.length() > 0) {
			lockButton(true);
			btnCreateFolder.setText("My folder: " + Constant.NAME_MY_FOLDER);
		} else {
			lockButton(false);
		}

		// Use SharedPreferences storage information User
		// SharedPreferences sharedpreferences = getSharedPreferences(
		// Constant.DIGI_LOGIN_PREFERENCES, Context.MODE_PRIVATE);

		checkLogIn();
		super.onResume();
	}

	private void checkLogIn() {
		sharedPreferences = getSharedPreferences(
				Constant.DIGI_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
		if (!sharedPreferences.getBoolean(Constant.FLAG_KEY, false)) {
			Intent i = new Intent(DigiMobiActivity.this, LoginActivity.class);
			startActivity(i);
		}

	}

	private void lockButton(Boolean b) {
		// TODO Auto-generated method stub
		btnGallery.setEnabled(b);
		btnSupplement.setEnabled(b);
		btnCamera.setEnabled(b);
		btnUpload.setEnabled(b);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {

		case R.id.btnCreateFolder:
			showCreateFolderActivity();
			break;

		case R.id.gallery:
			// get image from Gallery
			Constant.TYPE = 0;

			// show CreateCustomerActivity
			createCustomer();
			break;
		case R.id.supplement:
			// get list Customer
			Constant.TYPE = 1;
			chooseCustomer();

			break;
		case R.id.camera:
			// get image from Camera
			Constant.TYPE = 2;

			// show CreateCustomerActivity
			createCustomer();
			break;
		case R.id.upload:
			// Intent intent4 = new Intent(DigiMobiActivity.this,
			// ListCustomerActivity.class);
			// startActivity(intent4);
			break;
		case R.id.profile:
			showDialogCreateUser();
			break;
		case R.id.help:
			break;
		case R.id.about:
			signOut();
			break;
		case R.id.btnCreate:
			Toast.makeText(DigiMobiActivity.this, "OK", Toast.LENGTH_LONG)
					.show();
			dialog.cancel();
			break;
		}
	}

	private void signOut() {
		// TODO Auto-generated method stub
		// SharedPreferences sharedpreferences = getSharedPreferences(
		// Constant.DIGI_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
		// Editor editor = sharedpreferences.edit();
		// editor.putBoolean(Constant.FLAG_KEY, true);
		final Dialog dialog = new Dialog(DigiMobiActivity.this,
				R.style.MyTheme_Dialog);
		// dialog.getActionBar().setIcon(R.drawable.ic_action_about);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(R.layout.dialog_signout);
		dialog.show();
		Button btnOk = (Button) dialog.findViewById(R.id.button1);
		Button btnCancel = (Button) dialog.findViewById(R.id.button2);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// SharedPreferences sharedpreferences = PreferenceManager
				// .getDefaultSharedPreferences(DigiMobiActivity.this);

				// getSharedPreferences(
				// Constant.DIGI_LOGIN_PREFERENCES, Context.MODE_PRIVATE);

				Editor editor = sharedPreferences.edit();
				// editor.clear();
				editor.putBoolean(Constant.FLAG_KEY, false);
				// editor.commit();
				editor.apply();
				dialog.dismiss();
				dialog.cancel();
				checkLogIn();
				btnCreateFolder
						.setText(getString(R.string.create_folder_upload));
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

	}

	private void showCreateFolderActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(DigiMobiActivity.this,
				CreateFolderActivity.class);
		startActivityForResult(intent, Constant.REQUEST_CODE_CREATE_FOLDER);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String nameCustomer;
		if (resultCode == RESULT_OK) {

			switch (requestCode) {

			case Constant.REQUEST_CODE_CREATE_FOLDER:
				// folder name of sales staff
				Constant.NAME_MY_FOLDER = data
						.getStringExtra(Constant.PATH_MY_FOLDER);

				// folder path of sales staff
				Constant.PATH_MY_FOLDER_SAVE = Constant
						.getPathRoot(Constant.APP_FOLDER + File.separator
								+ Constant.NAME_MY_FOLDER);

				// folder path of sales staff save new document
				Constant.PATH_MY_FOLDER_SAVE_NEW = Constant
						.getPathRoot(Constant.APP_FOLDER + File.separator
								+ Constant.NAME_MY_FOLDER + File.separator
								+ Constant.NAME_MY_FOLDER);

				// folder path of sales staff save update document
				Constant.PATH_MY_FOLDER_SAVE_SUPPLEMENT = Constant
						.getPathRoot(Constant.APP_FOLDER + File.separator
								+ Constant.NAME_MY_FOLDER + File.separator
								+ Constant.CODE_SUPPPlEMENT
								+ Constant.NAME_MY_FOLDER);

				// create 2 folder for save a new and update document
				operation.createDirIfNotExists(
						Constant.PATH_MY_FOLDER_SAVE_NEW, 1);
				operation.createDirIfNotExists(
						Constant.PATH_MY_FOLDER_SAVE_SUPPLEMENT, 1);

				break;
			case Constant.REQUEST_CODE_CREATE_CUSTOMER_ACTIVITY:
				// save Zip file upload name
				Constant.NAME_ZIP_FILE_UPLOAD = Constant.NAME_MY_FOLDER;
				// Customer name
				nameCustomer = data
						.getStringExtra(Constant.NAME_CUSTOMER_PUT_EXTRA);
				Constant.NAME_CUSTOMER = nameCustomer;
				Constant.PATH = Constant.PATH_MY_FOLDER_SAVE_NEW;

				Intent intent = new Intent(DigiMobiActivity.this,
						NewAppActivity.class);
				startActivity(intent);

				finish();
				break;
			case Constant.REQUEST_CODE_LIST_CUSTOMER_ACTIVITY:
				Constant.NAME_ZIP_FILE_UPLOAD = Constant.CODE_SUPPPlEMENT
						+ Constant.NAME_MY_FOLDER;

				nameCustomer = data
						.getStringExtra(Constant.NAME_CUSTOMER_PUT_EXTRA);
				Constant.NAME_CUSTOMER = nameCustomer;
				Constant.PATH = Constant.PATH_MY_FOLDER_SAVE_SUPPLEMENT;
				Intent intent2 = new Intent(DigiMobiActivity.this,
						NewAppActivity.class);
				startActivity(intent2);
				finish();
				break;

			}
		}
	}

	private void chooseCustomer() {
		// TODO Auto-generated method stub
		final List<String> listFolder = operation.getAllSubFolder(new File(
				Constant.PATH_MY_FOLDER_SAVE_NEW));

		if (listFolder.size() > 0) {
			Intent intent = new Intent(DigiMobiActivity.this,
					ListCustomerActivity.class);
			intent.putStringArrayListExtra(Constant.LIST_CUSTOMER,
					(ArrayList<String>) listFolder);
			startActivityForResult(intent,
					Constant.REQUEST_CODE_LIST_CUSTOMER_ACTIVITY);
		} else {
			Toast.makeText(DigiMobiActivity.this, "Customer list empty",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void createCustomer() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(DigiMobiActivity.this,
				CreateCustomerActivity.class);
		startActivityForResult(intent,
				Constant.REQUEST_CODE_CREATE_CUSTOMER_ACTIVITY);
	}

	void showDialogCreateUser() {
		// AlertDialog.Builder dialog = new AlertDialog.Builder(
		// new ContextThemeWrapper(this, R.style.AppTheme));
		// LayoutInflater inflater = DigiMobiActivity.this.getLayoutInflater();
		// View view = inflater.inflate(R.layout.activity_create_customer,
		// null);
		//
		// dialog.setView(view);
		// a = dialog.create();
		//
		// dialog.show();
		// btnCreate = (Button) view.findViewById(R.id.btnCreate);
		// btnCreate.setOnClickListener(this);

		// dialog = new ProgressDialog(new ContextThemeWrapper(this,
		// R.style.AppTheme));
		// LayoutInflater inflater = DigiMobiActivity.this.getLayoutInflater();
		// View view = inflater.inflate(R.layout.activity_create_customer,
		// null);
		// dialog.setView(view);
		//
		// dialog.show();

	}
}
