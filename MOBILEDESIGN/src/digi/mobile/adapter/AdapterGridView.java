package digi.mobile.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import digi.mobile.activity.R;
import digi.mobile.building.ImageItem;

public class AdapterGridView extends BaseAdapter {

	private Context context;
	private int layoutResourceId;
	// private ArrayList<ImageItem> dataSource = new ArrayList<ImageItem>();
	private ArrayList<ImageItem> data = new ArrayList<ImageItem>();
	static LayoutInflater inflater = null;
	private DisplayImageOptions options;
	ImageLoader imageLoader;
	public AdapterGridView(Context context, ArrayList<ImageItem> data) {
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// AsyncTaskLoadFiles asyncTaskLoadFiles = new AsyncTaskLoadFiles(this,
		// dataSource);
		// asyncTaskLoadFiles.execute();
		imageLoader = ImageLoader.getInstance(); 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
		 ImageLoader.getInstance().init(config);
		options = new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.ic_error)
				.showImageOnLoading(R.drawable.ic_empty)
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();

	}

	void add(ImageItem item) {
		data.add(item);
	}

	void clear() {
		data.clear();
	}

	void remove(int index) {
		data.remove(index);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		View rowView;
		rowView = inflater.inflate(R.layout.gridview_item, null);
		holder.imageTitle = (TextView) rowView.findViewById(R.id.text);
		holder.image = (ImageView) rowView.findViewById(R.id.picture);

		holder.imageTitle.setText(data.get(position).getTitle());
		// holder.image.setImageBitmap(Constant.decodeSampledBitmapFromUri(data
		// .get(position).getImage(), 300, 300));
		imageLoader.displayImage("file://" + data.get(position).getImage(), holder.image,options);

		return rowView;
	}

	public class AsyncTaskLoadFiles extends AsyncTask<Void, ImageItem, Void> {

		AdapterGridView myTaskAdapter;
		private ArrayList<ImageItem> dataSource = new ArrayList<ImageItem>();

		public AsyncTaskLoadFiles(AdapterGridView adapter,
				ArrayList<ImageItem> dataSource) {
			myTaskAdapter = adapter;
			this.dataSource = dataSource;
		}

		@Override
		protected void onPreExecute() {

			myTaskAdapter.clear();

			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			// File[] files = targetDirector.listFiles();
			// for (File file : files) {
			// publishProgress(file.getAbsolutePath());
			// if (isCancelled())
			// break;
			// }
			for (ImageItem item : dataSource) {
				publishProgress(item);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(ImageItem... values) {
			// TODO Auto-generated method stub
			myTaskAdapter.add(values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			myTaskAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

}
