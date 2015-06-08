package digi.mobile.building;

import digi.mobile.activity.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class NewAppDialog extends DialogFragment implements
		OnEditorActionListener {

	private EditText editText;

	public interface NewAppDialogListener {
		void onFinishEditDialog(String inputText);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			NewAppDialogListener activity = (NewAppDialogListener) getActivity();
			activity.onFinishEditDialog(editText.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_app, container);
		editText = (EditText) view.findViewById(R.id.et_appname);
		getDialog().setTitle("Giving new app's name");
		// getDialog().setContentView(R.drawable.vpbank_icon_50x44);
		// getDialog().getActionBar().setIcon(R.drawable.vpbank_icon_50x44);
		editText.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		editText.setOnEditorActionListener(this);

		return view;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		// TODO Auto-generated method stub
		super.show(manager, tag);
	};

}
