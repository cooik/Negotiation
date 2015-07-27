package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.DetailsBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomerDeatailAdapter extends BaseAdapter {
	private List<DetailsBean> mList;
	private LayoutInflater inflater;
	private Context mContext;
	public CustomerDeatailAdapter(List<DetailsBean> list,Context context){
		mList=list;
		mContext=context;
		inflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public DetailsBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	 * 刷新UI
	 * @param list
	 */
	public void refreshUi(List<DetailsBean> list){
		mList.clear();
		if (list!=null&&list.size()>0) {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CustomerDetailViewHolder viewHolder;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.details_items, null);
			viewHolder=new CustomerDetailViewHolder();
			viewHolder.time=(TextView)convertView.findViewById(R.id.details_time);
			viewHolder.location=(TextView)convertView.findViewById(R.id.details_location);
			viewHolder.note=(TextView)convertView.findViewById(R.id.details_note);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(CustomerDetailViewHolder)convertView.getTag();
		}
		DetailsBean bean=mList.get(position);
		viewHolder.time.setText(bean.getTime());
		viewHolder.location.setText(bean.getLocation());
		viewHolder.note.setText(bean.getNote());
		
		
		return convertView;
	}
    class CustomerDetailViewHolder{
	   TextView time;
	   TextView location;
	   TextView note;
   }
}
