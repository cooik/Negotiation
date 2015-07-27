package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.MsgBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<MsgBean> {
	int resourceId;
	List<MsgBean> data;
	Context mContext;

	public MsgAdapter(Context context, int resource, List<MsgBean> objects) {
		super(context, resource, objects);
		resourceId = resource;
		data = objects;

	}

	public class ViewHolder {

		TextView date;

		TextView time;

		TextView Content;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MsgBean msg = getItem(position);
		View view;
		ViewHolder vh;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			vh = new ViewHolder();
			vh.date = (TextView) view.findViewById(R.id.msg_date);
			vh.time = (TextView) view.findViewById(R.id.msg_time);
			vh.Content = (TextView) view.findViewById(R.id.msg_content);
			view.setTag(vh);
		} else {
			view = convertView;
			vh = (ViewHolder) view.getTag();
		}
		vh.date.setText(msg.getDate());
		vh.time.setText(msg.getTime());
		vh.Content.setText(msg.getContent());
		return view;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public MsgBean getItem(int position) {
		return data.get(position);
	}
}
