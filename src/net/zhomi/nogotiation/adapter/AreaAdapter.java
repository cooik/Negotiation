package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.login.FindPasswordActivity;
import net.zhomi.negotiation.bean.AreaBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AreaAdapter extends BaseAdapter {
	private List<AreaBean> mList;
	private Context mContext;
	private LayoutInflater inflater;
	public AreaAdapter(List<AreaBean> list,Context context){
		mList=list;
		mContext=context;
		inflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public AreaBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void refresUi(List<AreaBean> list){
		mList.clear();
		if (list!=null&&list.size()>0) {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AreaViewHolder viewHolder;
		AreaBean bean=mList.get(position);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.area_list_item, null);
			viewHolder=new AreaViewHolder();
			viewHolder.areaName=(TextView)convertView.findViewById(R.id.area_name_text);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(AreaViewHolder)convertView.getTag();
		}
		viewHolder.areaName.setText(bean.getName());
		// TODO Auto-generated method stub
		return convertView;
	}
	class AreaViewHolder{
		TextView areaName;
	}

}
