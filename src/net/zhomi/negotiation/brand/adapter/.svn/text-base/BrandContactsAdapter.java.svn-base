package net.zhomi.negotiation.brand.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.brand.bean.BrandContactsBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BrandContactsAdapter extends BaseAdapter{
	private List<BrandContactsBean> mList;
	private Context mContext;
	private LayoutInflater inflater;
	public BrandContactsAdapter(List<BrandContactsBean> list,Context context){
		mList=list;
		mContext=context;
		inflater=LayoutInflater.from(mContext);
		
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public BrandContactsBean getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	 * 刷新界面
	 * @param list
	 */
	public void refreshUi(List<BrandContactsBean> list){
		mList.clear();
		if (list!=null&&list.size()>0) {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BrandContactsBean bean=mList.get(position);
		BrandContactsViewHolder viewHolder;
		if (convertView==null) {
			viewHolder=new BrandContactsViewHolder();
			convertView=inflater.inflate(R.layout.listview_brand_contacts_item, null);
			viewHolder.name=(TextView)convertView.findViewById(R.id.brand_contacts_item_name);
			viewHolder.agentName=(TextView)convertView.findViewById(R.id.brand_contacts_item_agent_name);
			viewHolder.job=(TextView)convertView.findViewById(R.id.brand_contacts_item_job);
			viewHolder.email=(TextView)convertView.findViewById(R.id.brand_contacts_item_email);
			viewHolder.tel=(TextView)convertView.findViewById(R.id.brand_contacts_item_tel);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(BrandContactsViewHolder)convertView.getTag();
		}
		viewHolder.name.setText(bean.getName());
		viewHolder.agentName.setText(bean.getAgentName());
		viewHolder.job.setText(bean.getJob());
		viewHolder.email.setText(bean.getEmail());
		viewHolder.tel.setText(bean.getTel());
		return convertView;
	}
	class BrandContactsViewHolder{
		TextView name;
		TextView agentName;
		TextView job;
		TextView email;
		TextView tel;
	}

}
