package net.zhomi.nogotiation.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.BrandBean;
import net.zhomi.negotiation.bean.CustomerBean;
import net.zhomi.negotiation.bean.MsgBean;
import net.zhomi.negotiation.event.AndroidEventManager;
import net.zhomi.negotiation.event.DownloadEvent;
import net.zhomi.negotiation.event.Event;
import net.zhomi.negotiation.event.EventCode;
import net.zhomi.negotiation.event.EventManager.OnEventListener;
import net.zhomi.negotiation.provider.PosterBmpProvider;
import net.zhomi.negotiation.utils.ImageUtils;
import net.zhomi.negotiation.utils.SystemUtils;
import net.zhomi.nogotiation.adapter.MsgAdapter.ViewHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BrandAdapter extends ArrayAdapter<BrandBean> implements
		OnEventListener {
	int resourceId;
	List<BrandBean> data;
	Context mContext;
	String mCityName;
	String mlogo;
	String mname;
	String mtype_name;
	String mlevel_name;
	String mcity_poi_num;
	String mcountry_poi_num;
	private Map<String, ViewHolder> imageViews = new HashMap<String, ViewHolder>();
	private int passIndex;

	public BrandAdapter(Context context, int resource, List<BrandBean> objects,
			String cityName) {
		super(context, resource, objects);
		resourceId = resource;
		mContext = context;
		data = objects;
		mCityName = cityName;
		AndroidEventManager.getInstance().addEventListener(
				EventCode.SC_DownloadImage, this, false);

	}

	public class ViewHolder {

		ImageView logo;

		TextView name;

		TextView type_name;

		TextView num;

	}

	public void refreshUi(List<BrandBean> list) {
		data.clear();
		if (list != null && list.size() > 0) {
			data.addAll(list);
		}
		notifyDataSetChanged();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("debuginfo", "getView is start");
		passIndex = position;
		BrandBean brand = getItem(position);
		View view;
		ViewHolder vh;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(resourceId, null);
			vh = new ViewHolder();
			vh.name = (TextView) view.findViewById(R.id.name);
			vh.type_name = (TextView) view.findViewById(R.id.type_name);
			vh.num = (TextView) view.findViewById(R.id.num);
			//
			vh.logo=(ImageView)view.findViewById(R.id.logo);
			view.setTag(vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		vh.name.setText(brand.getName());
		vh.type_name
				.setText(brand.getType_name() + "/" + brand.getLevel_name());
		vh.num.setText(mCityName + brand.getCity_poi_num() + "家,全国"
				+ brand.getCountry_poi_num() + "家");
		Log.d("debuginfo", "name:" + vh.name.getText().toString());
		Log.d("debuginfo", "type_name:" + vh.type_name.getText().toString());
		Log.d("debuginfo", "num:" + vh.num.getText().toString());

//		String url = brand.getLogo();
//		// 下载图片
//		Bitmap bitmap = PosterBmpProvider.getInstance().loadImage(url);
//		if (bitmap != null) {
//			vh.logo.setImageBitmap(bitmap);
//		}
//		imageViews.put(url, vh);
		if (passIndex == position) {
			String url = brand.getLogo();
			// 下载图片
			Bitmap bitmap = PosterBmpProvider.getInstance().loadImage(url);
			if (bitmap != null) {
				vh.logo.setImageBitmap(bitmap);
			}
			// Log.e("ysz", "picUrl===="+url);
			imageViews.put(url, vh);
		}
		return view;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public BrandBean getItem(int position) {
		return data.get(position);
	}

	@Override
	public void onEventRunEnd(Event event) {
		int code = event.getEventCode();
		if (code == EventCode.SC_DownloadImage) {
			DownloadEvent dEvent = (DownloadEvent) event;
			String url = (String) dEvent.getTag();
			if (TextUtils.isEmpty(url)) {
				return;
			}
			Bitmap bitmap = null;
			if (dEvent.isSuccess()) {
				bitmap = PosterBmpProvider.getInstance().loadImage(url);
			} else {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(),
						R.drawable.icon);
			}
			Bitmap aimBitmap = BitmapFactory.decodeResource(
					mContext.getResources(), R.drawable.icon);
			if (bitmap != null) {
				ViewHolder viewHolder = imageViews.get(url);
				Bitmap newBitmap = ImageUtils.zoomBitmap(bitmap,
						aimBitmap.getWidth(), aimBitmap.getHeight());
				if (newBitmap != null && viewHolder != null
						&& viewHolder.logo != null) {
					viewHolder.logo.setImageBitmap(newBitmap);
					return;
				}
			}

		}
	}
}
