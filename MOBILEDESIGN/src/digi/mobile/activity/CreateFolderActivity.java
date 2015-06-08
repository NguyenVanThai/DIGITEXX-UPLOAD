package digi.mobile.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import digi.mobile.util.Constant;
import digi.mobile.util.Operation;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFolderActivity extends Activity {

	ImageButton btnDate, btnExit, btnNumber;

	EditText edDate, edNumber, edMyID;

	TextView txtReview;

	Button btnCreate;

	String userName;

	DatePickerDialog datePickerDialog;

	Operation operation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_folder);

		btnCreate = (Button) findViewById(R.id.btnCreate);

		btnDate = (ImageButton) findViewById(R.id.imageButton1);
		btnExit = (ImageButton) findViewById(R.id.imageButtonExit);
		btnNumber = (ImageButton) findViewById(R.id.ImageButton01);

		edMyID = (EditText) findViewById(R.id.editTextUserName);
		edDate = (EditText) findViewById(R.id.EditText01);
		edNumber = (EditText) findViewById(R.id.EditText02);

		txtReview = (TextView) findViewById(R.id.textView2);

		// init operation
		operation = new Operation();

		// edDate.setError(getString(R.string.error_date));
		edNumber.setError(getString(R.string.error_ordinal_number));

		SharedPreferences sharedpreferences = getSharedPreferences(
				Constant.DIGI_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
		userName = sharedpreferences.getString(Constant.USER_NAME, null);

		edMyID.setText(userName);

		Calendar calendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(CreateFolderActivity.this,
				R.style.MyTheme_Dialog, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						String date = new SimpleDateFormat("yyyyddMM",
								Locale.getDefault()).format(newDate.getTime());
						edDate.setText(date);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));

		edDate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.toString().length() == 8) {
					edDate.setError(null);
				} else {
					edDate.setError(getString(R.string.error_date));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String temp = s.toString();
				String myID = edMyID.getText().toString();
				String number = edNumber.getText().toString();
				if (number.length() > 0) {
					temp = myID.concat("_" + temp + "_" + number);
				} else {
					temp = myID.concat("_" + temp);
				}

				txtReview.setText(temp);
			}
		});

		edNumber.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.toString().length() == 2) {
					edNumber.setError(null);
				} else {
					edNumber.setError(getString(R.string.error_ordinal_number));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String temp = s.toString();
				String myID = edMyID.getText().toString();
				String date = edDate.getText().toString();
				if (date.length() > 0) {
					temp = myID.concat("_" + date + "_" + temp);
				} else {
					temp = myID.concat("_" + temp);
				}

				txtReview.setText(temp);
			}
		});

		btnDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// String date = new SimpleDateFormat("yyyyddMM", Locale
				// .getDefault()).format(new Date());
				// edDate.setText(date);
				// edDate.setSelection(date.length());
				datePickerDialog.show();

			}
		});

		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btnNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// get Ordinal Number from Server

			}
		});

		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nameMyFolder = txtReview.getText().toString();
				if (operation.createDirIfNotExists(Constant.APP_FOLDER + "/"
						+ nameMyFolder, 0)) {

					Toast.makeText(CreateFolderActivity.this,
							"My folder has created", Toast.LENGTH_LONG).show();

					Intent intent = new Intent();
					intent.putExtra(Constant.PATH_MY_FOLDER, nameMyFolder);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					Toast.makeText(CreateFolderActivity.this,
							"My folder can not create", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}
}
