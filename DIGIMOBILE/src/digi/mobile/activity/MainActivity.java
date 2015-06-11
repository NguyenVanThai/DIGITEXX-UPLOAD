package digi.mobile.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {
	// declare button
	private Button btnNew, btnSupplement, btnCamera, btnUpload;
	private Button btnProfile, btnHelp, btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// init button
		btnNew = (Button) findViewById(R.id.gallery);
		btnSupplement = (Button) findViewById(R.id.supplement);
		btnCamera = (Button) findViewById(R.id.camera);
		btnUpload = (Button) findViewById(R.id.upload);
		btnProfile = (Button) findViewById(R.id.profile);
		btnHelp = (Button) findViewById(R.id.help);
		btnAbout = (Button) findViewById(R.id.about);

		// set onclick for button
		btnNew.setOnClickListener(this);
		btnSupplement.setOnClickListener(this);
		btnCamera.setOnClickListener(this);
		btnUpload.setOnClickListener(this);
		btnProfile.setOnClickListener(this);
		btnHelp.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.gallery:
			Intent intent = new Intent(MainActivity.this,
					NewCustomerActivity.class);
			startActivity(intent);
			break;
		case R.id.supplement:

			break;
		case R.id.camera:

			break;
		case R.id.upload:

			break;
		case R.id.profile:

			break;
		case R.id.help:
			break;
		case R.id.about:
			break;

		}
	}
}
