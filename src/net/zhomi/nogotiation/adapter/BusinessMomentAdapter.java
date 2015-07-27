package net.zhomi.nogotiation.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.BusinessMomentBean;
import net.zhomi.negotiation.event.AndroidEventManager;
import net.zhomi.negotiation.event.DownloadEvent;
import net.zhomi.negotiation.event.Event;
import net.zhomi.negotiation.event.EventCode;
import net.zhomi.negotiation.event.EventManager.OnEventListener;
import net.zhomi.negotiation.provider.PosterBmpProvider;
import net.zhomi.negotiation.utils.ImageUtils;
import net.zhomi.negotiation.utils.SystemUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

/**
 * 商业一刻适配器
 * 
 * @author yangshouzhi
 * 
 */
public class BusinessMomentAdapter extends BaseAdapter implements
		OnEventListener {
	private List<BusinessMomentBean> mList;
	private Context mContext;
	private LayoutInflater inflater;
	private Map<String, BusinessMomentViewHolder> imageViews = new HashMap<String, BusinessMomentViewHolder>();
	private int passIndex;

	public BusinessMomentAdapter(List<BusinessMomentBean> list, Context context) {
		mList = list;
		mContext = context;
		inflater = LayoutInflater.from(mContext);
		AndroidEventManager.getInstance().addEventListener(
				EventCode.SC_DownloadImage, this, false);

	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public BusinessMomentBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 更新UI
	 * 
	 * @param list
	 */
	public void refreshUi(List<BusinessMomentBean> list) {
		mList.clear();
		if (list != null && list.size() != 0) {
			mList.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BusinessMomentViewHolder viewHolder;
		passIndex = position;
		BusinessMomentBean bean = mList.get(position);
		if (null == convertView) {
			viewHolder = new BusinessMomentViewHolder();
			convertView = inflater.inflate(
					R.layout.business_moment_listview_item, null);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.business_moment_item_time);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.business_moment_item_img);

			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHolder.img
					.getLayoutParams(); // 取控件textView当前的布局参数
			// linearParams.width = SystemUtils.getScreenWidth(mContext) *3/4;
			// linearParams.height = LayoutParams.WRAP_CONTENT;

			// linearParams.height = linearParams.width*3/5;
			// viewHolder.img.setLayoutParams(linearParams);
			viewHolder.info = (TextView) convertView
					.findViewById(R.id.business_moment_item_info);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (BusinessMomentViewHolder) convertView.getTag();
			// LinearLayout.LayoutParams linearParams =
			// (LinearLayout.LayoutParams) viewHolder.img
			// .getLayoutParams(); // 取控件textView当前的布局参数
			// linearParams.height = LayoutParams.WRAP_CONTENT;
			// linearParams.width = SystemUtils.getScreenWidth(mContext) *3/4;
			// viewHolder.img.setLayoutParams(linearParams);
			viewHolder.info = (TextView) convertView
					.findViewById(R.id.business_moment_item_info);
		}
		viewHolder.time.setText(bean.getDate());
		viewHolder.info.setText(bean.getTitle());
		// *****
		// Bitmap originBitmap = BitmapFactory.decodeResource(
		// mContext.getResources(), R.drawable.business);
		// viewHolder.img.setImageBitmap(originBitmap);
		// ******判断位置
		if (passIndex == position) {
			String url = bean.getImgUrl();
			// 下载图片
			Bitmap bitmap = PosterBmpProvider.getInstance().loadImage(url);
			if (bitmap != null) {
				LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHolder.img
						.getLayoutParams(); // 取控件textView当前的布局参数
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				// 定义预转换成的图片的宽度和高度
				int newWidth = SystemUtils.getScreenWidth(mContext) *4 / 6;
				int newHeight = SystemUtils.getScreenWidth(mContext) * 3 / 7;

				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;

				// 创建操作图片用的matrix对象
				Matrix matrix = new Matrix();
				// 缩放图片动作
				matrix.postScale(scaleWidth, scaleHeight);
				// 创建新的图片
				Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
						height, matrix, true);
				viewHolder.img.setImageBitmap(resizedBitmap);
			}
			Log.e("ysz", "picUrl====" + url);
			imageViews.put(url, viewHolder);
		}
		return convertView;
	}

	/**
	 * 销毁掉download监听
	 */
	public void destroyDownloadEvent() {
		AndroidEventManager.getInstance().removeEventListener(
				EventCode.SC_DownloadImage, this);
	}

	class BusinessMomentViewHolder {
		TextView time;
		ImageView img;
		TextView info;
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
						R.drawable.business);
			}
			Bitmap aimBitmap = BitmapFactory.decodeResource(
					mContext.getResources(), R.drawable.business);
			if (bitmap != null) {
				BusinessMomentViewHolder viewHolder = imageViews.get(url);
				Bitmap newBitmap = ImageUtils.zoomBitmap(bitmap,
						aimBitmap.getWidth(), aimBitmap.getHeight());
				if (newBitmap != null && viewHolder != null
						&& viewHolder.img != null) {
					viewHolder.img.setImageBitmap(newBitmap);
					return;
				}
			}

		}

	}

}
