/**
 * 
 */
package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.CityBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/** 

 * @ClassName: CityAdapter 

 * @Description: TODO(这里用一句话描述这个类的作用) 

 * @author 杨守志

 * @date 2014-11-10 上午10:00:12 

 */
public class CityAdapter extends BaseAdapter{
	private Context mContext;
	private List<CityBean> mCityList;
	private LayoutInflater layoutInflater;
	
	public CityAdapter(Context context,List<CityBean> cityList){
		mContext=context;
		mCityList=cityList;
		layoutInflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mCityList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void refreshUi(List<CityBean> list){
		mCityList.clear();
		if (list!=null&&list.size()>0) {
			mCityList.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.city_list_item, null);
			viewHolder=new ViewHolder();
			viewHolder.cityName=(TextView)convertView.findViewById(R.id.city_name_text);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder)convertView.getTag();
		}
		String cityName=mCityList.get(position).getCityName();
		viewHolder.cityName.setText(cityName);
		return convertView;
	}
	public class ViewHolder{
		TextView cityName;
	}

}
