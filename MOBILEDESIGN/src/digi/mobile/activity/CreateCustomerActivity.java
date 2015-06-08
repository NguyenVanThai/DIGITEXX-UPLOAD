package digi.mobile.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import digi.mobile.util.Constant;

public class CreateCustomerActivity extends Activity {

	Button btnCreate;
	ImageButton imageExit;
	EditText edCustomerName, edIdCard, edSales;
	TextView txtReview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_customer);

		// clock touched outside
		setFinishOnTouchOutside(false);

		// init Button
		btnCreate = (Button) findViewById(R.id.btnCreate);
		imageExit = (ImageButton) findViewById(R.id.imageButtonExit);
		edCustomerName = (EditText) findViewById(R.id.editTextUserName);
		edIdCard = (EditText) findViewById(R.id.EditText01);
		edSales = (EditText) findViewById(R.id.EditText02);
		txtReview = (TextView) findViewById(R.id.textView2);

		edCustomerName.setError(getString(R.string.error_customer_name));
		edIdCard.setError(getString(R.string.error_customer_id));
		edSales.setError(getString(R.string.error_channel));

		/*
		 * edCustomerName.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View v, boolean hasFocus) { //
		 * TODO Auto-generated method stub if (hasFocus) { if
		 * (edCustomerName.getText().length() <= 0) { edCustomerName
		 * .setError(getString(R.string.error_customer_name)); } } } });
		 * 
		 * edIdCard.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View v, boolean hasFocus) { //
		 * TODO Auto-generated method stub if (hasFocus) { if
		 * (edIdCard.getText().length() <= 0) {
		 * edIdCard.setError(getString(R.string.error_customer_id)); } } } });
		 * 
		 * edSales.setOnFocusChangeListener(new OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View v, boolean hasFocus) { //
		 * TODO Auto-generated method stub if (hasFocus) { if
		 * (edSales.getText().length() <= 0) {
		 * edSales.setError(getString(R.string.error_channel)); } } } });
		 */

		edCustomerName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

				// if (s.toString().length() <= 0) {
				// edCustomerName.setError("Customer name can NOT be empty");
				// } else {
				// edCustomerName.setError(null);
				// }
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// String temp = s.toString();
				// if (!temp.equals(temp.toUpperCase())) {
				// edCustomerName.setText(temp.toUpperCase());
				//
				// }
				// edCustomerName.setSelection(edCustomerName.getText().length());
				//
				if (s.toString().length() <= 0) {
					edCustomerName
							.setError(getString(R.string.error_customer_name));
				} else {
					edCustomerName.setError(null);
				}
				String temp = s.toString();

				if (edIdCard.getText().toString().length() > 0) {

					temp = temp + "_" + edIdCard.getText().toString();
				}

				if (edSales.getText().toString().length() > 0) {

					temp = temp + "_" + edSales.getText().toString();
				}

				txtReview.setText(temp);

			}
		});

		edIdCard.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				int length = s.toString().length();
				if (length == 8 || length == 9 || length == 12) {
					edIdCard.setError(null);
				} else {

					edIdCard.setError(getString(R.string.error_customer_id));
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

				if (edCustomerName.getText().toString().length() > 0) {

					temp = edCustomerName.getText().toString() + "_" + temp;
				}

				if (edSales.getText().toString().length() > 0) {

					temp = temp + "_" + edSales.getText().toString();
				}

				txtReview.setText(temp);
			}
		});

		edSales.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				int length = s.toString().length();
				if (length == 3) {
					edSales.setError(null);

				} else {
					edSales.setError(getString(R.string.error_channel));
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

				if (edCustomerName.getText().toString().length() > 0) {

					temp = edCustomerName.getText().toString() + "_" + temp;
				}

				if (edIdCard.getText().toString().length() > 0) {

					temp = edCustomerName.getText().toString() + "_"
							+ edIdCard.getText().toString() + "_"
							+ s.toString();
				}

				txtReview.setText(temp);

				// if (edCustomerName.getText().toString().length() > 0) {
				//
				// temp = edCustomerName.getText().toString() + "_" + temp;
				// }
				//
				// if (edIdCard.getText().toString().length() > 0) {
				//
				// temp = edIdCard.getText().toString() + "_" + temp;
				// }

				txtReview.setText(temp);
			}
		});

		// handling click for buttonCreate
		btnCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (edCustomerName.getError() != null
						|| edIdCard.getError() != null
						|| edSales.getError() != null) {
					final Dialog dialog = new Dialog(
							CreateCustomerActivity.this, R.style.MyTheme_Dialog);
					dialog.setContentView(R.layout.dialog_warning);
					Button btnOk = (Button) dialog.findViewById(R.id.btnCreate);
					btnOk.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				} else {
					resultData(txtReview.getText().toString());
				}

				// if (edCustomerName.getError().toString().length() > 0
				// && edIdCard.getError().toString().length() > 0
				// && edSales.getError().toString().length() > 0) {
				// Log.d("OK", "OK");
				// } else {
				// Log.d("Error", "error");
				// }

				// resultData();

				// String customerName = txtCustomerName.getText().toString();
				// Constant.NAME_CUSTOMER = customerName;
				//
				// // open NewAppActivity
				// Intent intent = new Intent(CreateCustomerActivity.this,
				// NewAppActivity.class);
				// startActivity(intent);
				// Toast.makeText(CreateCustomerActivity.this,
				// customerName + " customer is created",
				// Toast.LENGTH_SHORT).show();
				// finish();
			}
		});

		// handling click for buttonExit
		imageExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void resultData(String nameCustomer) {
		Intent intent = new Intent();
		intent.putExtra(Constant.NAME_CUSTOMER_PUT_EXTRA, nameCustomer);
		setResult(RESULT_OK, intent);

		finish();
	}

	private String concatString(String start, String mid, String stop) {
		String temp = null;
		if (start.equals("")) {
			return temp = mid + "_" + stop;
		}
		if (mid.equals("")) {
			return temp = start + "_" + stop;
		}
		if (stop.equals("")) {
			return temp = start + "_" + mid;
		}

		return start + "_" + mid + "_" + stop;
	}
}
