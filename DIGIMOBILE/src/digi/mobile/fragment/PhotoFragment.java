package digi.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import digi.mobile.activity.R;

public class PhotoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.photo_fragment,
				container, false);
		return myFragmentView;
	}

	public void refreshView() {
		// do whatever you want
		Log.d("Photo", "Photo");
	}
}
