package net.zhomi.negotiation.brand.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.brand.adapter.BrandBusinessAdapter;
import net.zhomi.negotiation.brand.bean.BrandBusinessBean;
import net.zhomi.negotiation.utils.HttpData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 品牌库——旗下商家
 * 
 * @author yangshouzhi
 * 
 */
public class BrandBusinessFragment extends Fragment {
	private ListView brandBusinessListView;
	private List<BrandBusinessBean> brandBusinessList;
	private BrandBusinessAdapter brandBusinessAdapter;
	private String cityId;
	private String brandId;
	private String lon;
	private String lat;
	private int page = 1;
	private int perPage = 10;

	public BrandBusinessFragment(String cityId, String brandId, String lon,
			String lat) {
		this.cityId = cityId;
		this.brandId = brandId;
		this.lon = lon;
		this.lat = lat;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_brand_business, null);
		initView(v);
		return v;
	}

	private void initView(View v) {
		brandBusinessListView = (ListView) v
				.findViewById(R.id.brand_business_listview);
		brandBusinessList = new ArrayList<BrandBusinessBean>();
		brandBusinessAdapter = new BrandBusinessAdapter(brandBusinessList,
				getActivity());
		brandBusinessListView.setAdapter(brandBusinessAdapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public void getData(){
		new GetBusinessOfBrandTask().execute(cityId, brandId, lon, lat,
				String.valueOf(page), String.valueOf(perPage));
	}

	private class GetBusinessOfBrandTask extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String city_id = params[0];
			String brand_id = params[1];
			String lon = params[2];
			String lat = params[3];
			String page = params[4];
			String perpage = params[5];
			//ceshi
			return HttpData.getBusinessOfBrandList("brand", "brand_shop",
					"1.0", "2", city_id, brand_id, lon, lat, page, perpage);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

	}

}
