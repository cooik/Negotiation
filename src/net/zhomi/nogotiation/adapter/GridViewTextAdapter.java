package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.nogotiation.adapter.TextAdapter.OnItemClickListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewTextAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mArrayData;
	private int selectedPos = -1;
	private String selectedText = "";
	private float textSize;
	private LayoutInflater inflater;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	public GridViewTextAdapter(Context context, String[] arrayData) {
		mContext = context;
		mArrayData = arrayData;
		inflater=LayoutInflater.from(mContext);
		init();
	}

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos];
			notifyDataSetChanged();
		}

	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos];
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gridview_item, null);
			viewHolder = new ItemViewHolder();
			viewHolder.ietemTextView = (TextView) convertView
					.findViewById(R.id.gridvie_item_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ItemViewHolder) convertView.getTag();
		}
		viewHolder.ietemTextView.setTag(position);
		viewHolder.ietemTextView.setText(mArrayData[position]);
		if (selectedPos == position) {
			viewHolder.ietemTextView.setBackgroundColor(mContext.getResources()
					.getColor(R.color.gd_item_selector_bg));
		} else {
			viewHolder.ietemTextView.setBackgroundColor(Color.WHITE);
		}
		viewHolder.ietemTextView.setOnClickListener(onClickListener);
		return convertView;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayData.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayData[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class ItemViewHolder {
		TextView ietemTextView;
	}

}
