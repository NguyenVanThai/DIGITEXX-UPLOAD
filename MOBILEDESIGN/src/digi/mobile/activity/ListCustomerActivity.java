package digi.mobile.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import digi.mobile.util.Constant;

public class ListCustomerActivity extends Activity {

	ImageButton imageExit;
	ListView lvListCustomer;
	ArrayAdapter<String> arrAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_customer);

		lvListCustomer = (ListView) findViewById(R.id.listViewCustomer);

		imageExit = (ImageButton) findViewById(R.id.imageButtonExit);

		// get ListCustomer from DigiMobiActivity
		Intent intent = getIntent();
		final List<String> listCustomer = intent
				.getStringArrayListExtra(Constant.LIST_CUSTOMER);

		arrAdapter = new ArrayAdapter<String>(ListCustomerActivity.this,
				android.R.layout.simple_list_item_1, listCustomer);
		lvListCustomer.setAdapter(arrAdapter);

		lvListCustomer
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						// switch(Constant.TYPE)
						// {
						// case 0:
						// Constant.NAME_CUSTOMER = listCustomer.get(position)
						// .toString();
						// break;
						// case 1:
						// Constant.NAME_CUSTOMER = Constant.CODE_SUPPPlEMENT +
						// listCustomer.get(position)
						// .toString();
						// break;
						//
						// case 2:
						// Constant.NAME_CUSTOMER = listCustomer.get(position)
						// .toString();
						// break;
						// }

						// Intent intent = new Intent(ListCustomerActivity.this,
						// NewAppActivity.class);
						// startActivity(intent);
						Intent intent = new Intent();
						intent.putExtra(Constant.NAME_CUSTOMER_PUT_EXTRA, listCustomer
								.get(position).toString());
						setResult(RESULT_OK, intent);
						finish();
					}
				});

		imageExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
			}
		});

	}
}
