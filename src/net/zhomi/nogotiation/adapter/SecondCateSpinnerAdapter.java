package net.zhomi.nogotiation.adapter;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.utils.SystemUtils;
import net.zhomi.nogotiation.adapter.FirstCateSpinnerAdapter.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecondCateSpinnerAdapter extends ArrayAdapter<String> {
	private Context mContext;
	private String[] mSecondCateArr;
	private LayoutInflater inflater;
	private int mResource;

	public SecondCateSpinnerAdapter(Context context, int resource,
			String[] secondCateArr) {
		super(context, resource);
		mSecondCateArr = secondCateArr;
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		mResource = resource;
	}

	@Override
	public int getCount() {
		return mSecondCateArr.length;
	}

	@Override
	public String getItem(int position) {
		return mSecondCateArr[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 更新UI
	 * 
	 * @param cateArr
	 */
	public void refreshUi(String[] cateArr) {
		mSecondCateArr = null;
		if (cateArr != null && cateArr.length > 0) {
			mSecondCateArr = new String[cateArr.length];
			for (int i = 0; i < cateArr.length; i++) {
				mSecondCateArr[i] = cateArr[i];
			}
		}
		notifyDataSetChanged();
	}

	public class ViewHolder {
		TextView spinner_item;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String item = mSecondCateArr[position];
		View view = null;
		ViewHolder vh;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.spinner_item_layout, null);
			vh = new ViewHolder();
			vh.spinner_item = (TextView) view
					.findViewById(R.id.spinner_item_label);
			view.setTag(vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		vh.spinner_item.setText(item);
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		String item = mSecondCateArr[position];
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.spinner_item_layout, null);
		ViewHolder vh = new ViewHolder();
		vh.spinner_item = (TextView) view.findViewById(R.id.spinner_item_label);
		vh.spinner_item.setText(item);
		return view;
	}

}
