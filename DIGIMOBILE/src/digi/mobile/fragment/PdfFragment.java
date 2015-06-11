package digi.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import digi.mobile.activity.R;

public class PdfFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.pdf_fragment,
				container, false);
		return myFragmentView;
	}

	public void refreshView() {
		// do whatever you want
		Log.d("paf", "Paf");
	}
	
	
}
