package net.zhomi.negotiation.brand.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.brand.bean.BrandBusinessBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BrandBusinessAdapter extends BaseAdapter {
	private List<BrandBusinessBean> mList;
	private Context mContext;
	private LayoutInflater inflater;

	public BrandBusinessAdapter(List<BrandBusinessBean> list, Context context) {
		mList = list;
		mContext = context;
		inflater = LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public BrandBusinessBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void refreshUi(List<BrandBusinessBean> list) {
		mList.clear();
		if (list != null && list.size() > 0) {
			mList.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BrandBusinessViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.listview_brand_business_item, null);
			viewHolder = new BrandBusinessViewHolder();
			viewHolder.logo = (ImageView) convertView
					.findViewById(R.id.brand_business_item_logo);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.brand_business_item_name);
			viewHolder.ditric = (TextView) convertView
					.findViewById(R.id.brand_business_item_district);
			viewHolder.street = (TextView) convertView
					.findViewById(R.id.brand_business_item_street);
			viewHolder.distance = (TextView) convertView
					.findViewById(R.id.brand_business_item_distance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (BrandBusinessViewHolder) convertView.getTag();
		}
		return convertView;
	}

	class BrandBusinessViewHolder {
		ImageView logo;
		TextView name;
		TextView ditric;
		TextView street;
		TextView distance;
	}

}
