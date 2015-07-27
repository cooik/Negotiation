package net.zhomi.nogotiation.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.CustomerBean;
import net.zhomi.negotiation.bean.MsgBean;
import net.zhomi.negotiation.event.AndroidEventManager;
import net.zhomi.negotiation.event.DownloadEvent;
import net.zhomi.negotiation.event.Event;
import net.zhomi.negotiation.event.EventCode;
import net.zhomi.negotiation.event.EventManager.OnEventListener;
import net.zhomi.negotiation.provider.PosterBmpProvider;
import net.zhomi.negotiation.utils.ImageUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 客户列表适配器
 * 
 * @author yangshouzhi
 * 
 */
public class CustomerAdapter extends ArrayAdapter<CustomerBean> implements
		OnEventListener {
	private List<CustomerBean> data;
	private Context mContext;
	private int ResourceId;
	private static String TAG = "CustomerAdapter";
	private Map<String, ViewHolder> imageViews = new HashMap<String, ViewHolder>();
	private int passIndex;

	public CustomerAdapter(Context context, int textViewResourceId,
			List<CustomerBean> objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		ResourceId = textViewResourceId;
		data = objects;
		AndroidEventManager.getInstance().addEventListener(
				EventCode.SC_DownloadImage, this, false);

	}

	public class ViewHolder {

		ImageView customers_pic;
		TextView customers_name;

	}

	public void refreshUi(List<CustomerBean> list) {
		data.clear();
		if (list != null && list.size() > 0) {
			data.addAll(list);
		}
		notifyDataSetChanged();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		passIndex = position;
		CustomerBean customer = getItem(position);
		View view;
		ViewHolder vh;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(ResourceId, null);
			vh = new ViewHolder();
			vh.customers_pic = (ImageView) view
					.findViewById(R.id.customer_head);
			vh.customers_name = (TextView) view
					.findViewById(R.id.customer_name);
			view.setTag(vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		// vh.customers_pic.setBackgroundDrawable(this.getContext().getResources()
		// .getDrawable(customer.getPic_url()));
		vh.customers_name.setText(customer.getName());
		// *****
		Bitmap originBitmap = BitmapFactory.decodeResource(
				mContext.getResources(), R.drawable.user_center_avator_default);
		vh.customers_pic.setImageBitmap(originBitmap);
		// ******判断位置
		if (passIndex == position) {
			String url = customer.getPic_url();
			// 下载图片
			Bitmap bitmap = PosterBmpProvider.getInstance().loadImage(url);
			if (bitmap != null) {
				vh.customers_pic.setImageBitmap(bitmap);
			}
			// Log.e("ysz", "picUrl===="+url);
			imageViews.put(url, vh);
		}
		Log.d(TAG, "getView");
		return view;
	}

	/**
	 * 销毁掉download监听
	 */
	public void destroyDownloadEvent() {
		AndroidEventManager.getInstance().removeEventListener(
				EventCode.SC_DownloadImage, this);
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
						R.drawable.user_center_avator_default);
			}
			Bitmap aimBitmap = BitmapFactory.decodeResource(
					mContext.getResources(),
					R.drawable.user_center_avator_default);
			if (bitmap != null) {
				ViewHolder viewHolder = imageViews.get(url);
				Bitmap newBitmap = ImageUtils.zoomBitmap(bitmap,
						aimBitmap.getWidth(), aimBitmap.getHeight());
				if (newBitmap != null && viewHolder != null
						&& viewHolder.customers_pic != null) {
					viewHolder.customers_pic.setImageBitmap(newBitmap);
					return;
				}
			}

		}
	}
}
