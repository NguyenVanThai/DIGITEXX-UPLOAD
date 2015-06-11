package digi.mobile.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import digi.mobile.activity.R;
import digi.mobile.building.EventListener;
import digi.mobile.building.UpdateableFragment;

public class CreateCustomerFragment extends Fragment implements
		UpdateableFragment {
	Button btn;

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// setHasOptionsMenu(true);
	// // get the action bar
	// ActionBar actionBar = getActivity().getActionBar();
	//
	// // // Enabling Back navigation on Action Bar icon
	// // actionBar.setDisplayHomeAsUpEnabled(true);
	// }
	//
	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// // TODO Auto-generated method stub
	// super.onCreateContextMenu(menu, v, menuInfo);
	//
	// }
	private EventListener listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof EventListener) {
			listener = (EventListener) activity;
		} else {
			// Throw an error!
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myFragmentView = inflater.inflate(
				R.layout.activity_create_fragment, container, false);

		// Log.d("Fragment 1", "onCreateView");
		// Toast.makeText(getActivity(), "onCreateView",
		// Toast.LENGTH_LONG).show();
		btn = (Button) myFragmentView.findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.sendDataToActivity("Hello World!");
			}
		});

		return myFragmentView;
	}

	public void refreshView() {
		// do whatever you want
		Log.d("Create customer", "Create customer");

	}

	@Override
	public void update(String handing) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // TODO Auto-generated method stub
	// // MenuInflater menuInflater = getActivity().getMenuInflater();
	//
	// inflater.inflate(R.menu.action_newappdetail_activity, menu);
	// getActivity().getWindow().setUiOptions(
	// ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
	//
	// super.onCreateOptionsMenu(menu, inflater);
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// // handling clicks on action items
	// MenuInflater menuInflater = getMenuInflater();
	// menuInflater.inflate(R.menu.action_newappdetail_activity, menu);
	//
	// return super.onCreateOptionsMenu(menu);
	// }
}
