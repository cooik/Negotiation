package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.TaskTipBean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskTipListAdapter extends BaseAdapter{
	private List<TaskTipBean> mList;
	private Context mContext;
	private LayoutInflater inflater;
	public TaskTipListAdapter(List<TaskTipBean> list,Context context){
		mList=list;
		mContext=context;
		inflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public TaskTipBean getItem(int position) {
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
	public void refreshUi(List<TaskTipBean> list) {
		mList.clear();
		if (list != null && list.size() != 0) {
			mList.addAll(list);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TaskTipViewHolder viewHolder;
		if (null==convertView) {
			viewHolder=new TaskTipViewHolder();
			convertView=inflater.inflate(R.layout.task_tip_listview_item, null);
			viewHolder.title=(TextView)convertView.findViewById(R.id.task_tip_item_title);
			viewHolder.info=(TextView)convertView.findViewById(R.id.task_tip_item_info);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(TaskTipViewHolder)convertView.getTag();
		}
		viewHolder.title.setText(mList.get(position).getName());
		viewHolder.info.setText(mList.get(position).getNote());
		return convertView;
	}
	class TaskTipViewHolder{
		TextView title;
		TextView info;
	}

}
