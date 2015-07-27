package net.zhomi.nogotiation.adapter;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.utils.SystemUtils;
import net.zhomi.nogotiation.adapter.MsgAdapter.ViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FirstCateSpinnerAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private String[] mFistCateArr;
	private LayoutInflater inflater;
	private int mResource;

	public FirstCateSpinnerAdapter(Context context, int resource,
			String[] firstCateArr) {
		super(context, resource);
		mFistCateArr = firstCateArr;
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		mResource = resource;
	}

	@Override
	public int getCount() {
		return mFistCateArr.length;
	}

	@Override
	public String getItem(int position) {
		return mFistCateArr[position];
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
		mFistCateArr = null;
		if (cateArr != null && cateArr.length > 0) {
			mFistCateArr = new String[cateArr.length];
			for (int i = 0; i < cateArr.length; i++) {
				mFistCateArr[i] = cateArr[i];
			}
		}
		notifyDataSetChanged();
	}

	public class ViewHolder {
		TextView spinner_item;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String item = mFistCateArr[position];
		View view = null;
		ViewHolder vh;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.spinner_item_layout, null);
			vh = new ViewHolder();
			vh.spinner_item = (TextView) view
					.findViewById(R.id.spinner_item_label);
			view.setTag(vh);
			// convertView.setPadding(SystemUtils.dipToPixel(mContext, 7),
			// SystemUtils.dipToPixel(mContext, 12),
			// SystemUtils.dipToPixel(mContext, 2),
			// SystemUtils.dipToPixel(mContext, 12));
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		vh.spinner_item.setText(item);

		// ((TextView)
		// convertView).setTextColor(mContext.getResources().getColor(
		// R.color.jwbb_query_spiner_text));
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		String item = mFistCateArr[position];
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.spinner_item_layout, null);
		ViewHolder vh = new ViewHolder();
		vh.spinner_item = (TextView) view.findViewById(R.id.spinner_item_label);
		vh.spinner_item.setText(item);

		// if (convertView == null) {
		//
		// convertView = LayoutInflater.from(mContext).inflate(
		// R.layout.spinner_checked_text, null);
		// convertView.setPadding(SystemUtils.dipToPixel(mContext, 7),
		// SystemUtils.dipToPixel(mContext, 12),
		// SystemUtils.dipToPixel(mContext, 2),
		// SystemUtils.dipToPixel(mContext, 12));
		// }
		// ((TextView) convertView).setText(mFistCateArr[position]);
		// ((TextView)
		// convertView).setTextColor(mContext.getResources().getColor(R.color.jwbb_query_spiner_text));
		return view;
	}

}
