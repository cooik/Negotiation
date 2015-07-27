package net.zhomi.nogotiation.adapter;

import net.zhaomi.negotiation.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewItemAdapter extends BaseAdapter{
	private String[] mArr;
	private Context mContext;
	private LayoutInflater inflater;
	private int clickTemp=-1;
	public GridViewItemAdapter(Context context,String[] arr ){
		mContext=context;
		mArr=arr;
		inflater=LayoutInflater.from(mContext);
	}
	public void setSeclection(int postion){
		clickTemp=postion;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mArr.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return mArr[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder viewHolder;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.gridview_item, null);
			viewHolder=new ItemViewHolder();
			viewHolder.ietemTextView=(TextView)convertView.findViewById(R.id.gridvie_item_textview);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ItemViewHolder)convertView.getTag();
		}
		viewHolder.ietemTextView.setText(mArr[position]);
		if (clickTemp==position) {
			viewHolder.ietemTextView.setBackgroundColor(mContext.getResources().getColor(R.color.gd_item_selector_bg));
		}else {
			viewHolder.ietemTextView.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}
 private class ItemViewHolder{
	 TextView ietemTextView;
 }
}
