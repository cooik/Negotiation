package net.zhomi.negotiation.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.CityBean;
import net.zhomi.negotiation.bean.ProvinceBean;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.utils.PinYinComparator;
import net.zhomi.negotiation.utils.SystemUtils;
import net.zhomi.nogotiation.adapter.CityAdapter;
import net.zhomi.nogotiation.adapter.ProvinceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

public class ProvinceCityChooseActivity extends BaseActivity {

	/** 选择城市后设置到结果里的标签 */
	public static final String INTENT_FLAG_RESULT_STRING = "cityinfo";

	private ListView provinceListView;
	private ProvinceAdapter provinceAdapter;
	private ListView cityListView;
	private CityAdapter cityAdapter;

	private boolean isFirst = true;// 是否第一次加载

	private List<ProvinceBean> provinceLists;
	private List<CityBean> cityList;
	private String provId = "";

	// public static HashMap<String, Boolean> states = new HashMap<String,
	// Boolean>();// 用于记录每个RadioButton的状态，并保证只可选一个

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_location);
		initView();
	}


	private void initView() {
		// 初始化（赋值为false）
		// for (String key : states.keySet()) {
		// states.put(key, false);
		// }
		initTitle();
		back.setVisibility(View.VISIBLE);

		new GetProvinceName().execute();
		provinceListView = (ListView) findViewById(R.id.location_province_list);
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) provinceListView
				.getLayoutParams(); // 取控件textView当前的布局参数
		linearParams.height = LayoutParams.WRAP_CONTENT;
		linearParams.width = SystemUtils.getScreenWidth(this) / 2;
		provinceListView.setLayoutParams(linearParams);
		provinceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProvinceBean provinceBean = provinceLists.get(position);
				String provValue = provinceBean.getProvinceCode();
				Log.d("---", "prov value=" + provValue);
				provId = provValue;
				Log.d("---", "prov value=" + provId);
				new GetCityName().execute(provValue);
			}
		});
		cityListView = (ListView) findViewById(R.id.location_city_list);
		LinearLayout.LayoutParams cityLinearParams = (LinearLayout.LayoutParams) cityListView
				.getLayoutParams(); // 取控件textView当前的布局参数
		cityLinearParams.height = LayoutParams.WRAP_CONTENT;
		cityLinearParams.width = SystemUtils.getScreenWidth(this) / 2;
		cityListView.setLayoutParams(cityLinearParams);
		cityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CityBean cityBean = cityList.get(position);
				String cityName = cityBean.getCityName();
				String cityIdString = cityBean.getCityCode();

				String[] citys = { cityName, cityIdString };
				setCity(citys);
			}
		});
	}

	private void setCity(String[] citys) {
		Intent intent = getIntent();
		intent.putExtra("provinfo", provId);
		intent.putExtra(INTENT_FLAG_RESULT_STRING, citys);
		setResult(RESULT_OK, intent);
		finish();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (provinceLists.size() > 0) {
					Collections.sort(provinceLists, new PinYinComparator());
					provinceAdapter = new ProvinceAdapter(
							ProvinceCityChooseActivity.this, provinceLists);
					provinceListView.setAdapter(provinceAdapter);
					break;
				}
			case 2:
				cityAdapter = new CityAdapter(ProvinceCityChooseActivity.this,
						cityList);
				cityAdapter.notifyDataSetChanged();
				cityListView.setAdapter(cityAdapter);
				break;
			default:
				break;
			}
		}

	};

	public class GetProvinceName extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String result = HttpData.getProvinceCityName("other", "city_list",
					"1.0", "2", "1", "");
			return result;
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog("", "正在更新省份数据...");
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			if (TextUtils.isEmpty(result)) {
				showMsg("更新省份数据失败，请重新获取");
				return;
			}
			try {
				Log.d("---", result);
				JSONObject json = new JSONObject(result);
				String success = JSONUtils.getString(json, "result", "");
				if (success.equals("1")) {
					JSONArray provinceArray = JSONUtils.getJSONArray(json,
							"data");
					if (provinceArray.length() > 0) {
						provinceLists = new ArrayList<ProvinceBean>();
						for (int i = 0; i < provinceArray.length(); i++) {
							ProvinceBean province = new ProvinceBean();
							province.setProvinceName(JSONUtils.getString(
									provinceArray.getJSONObject(i), "name", ""));
							province.setProvinceCode(JSONUtils.getString(
									provinceArray.getJSONObject(i), "id", ""));
							provinceLists.add(province);
						}
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						provId = "3";
						new GetCityName().execute("3");
						isFirst = false;
					}
				} else {
					String desc = JSONUtils.getString(json, "msg", "");
					showMsg("获取数据失败失败，" + desc);
				}
			} catch (JSONException e) {
				showMsg("数据发生错误，请尝试重新启动程序");
			}

		}
	}

	public class GetCityName extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String provinceId = params[0];
			return HttpData.getProvinceCityName("other", "city_list", "1.0",
					"2", "2", provinceId);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			if (TextUtils.isEmpty(result)) {
				showMsg("更新城市数据失败,请重新获取");
				return;
			}
			try {
				Log.d("---", result);
				JSONObject json = new JSONObject(result);
				String success = JSONUtils.getString(json, "result", "");
				if (success.equals("1")) {
					JSONArray cityArray = JSONUtils.getJSONArray(json, "data");
					if (cityArray.length() > 0) {
						cityList = new ArrayList<CityBean>();
						for (int i = 0; i < cityArray.length(); i++) {
							CityBean cityBean = new CityBean();
							cityBean.setCityName(JSONUtils.getString(
									cityArray.getJSONObject(i), "name", ""));
							cityBean.setCityCode(JSONUtils.getString(
									cityArray.getJSONObject(i), "id", ""));
							cityList.add(cityBean);
						}
						new GetDistrictNameTask().execute(cityList.get(0).getCityCode());
					} else {
						cityList = new ArrayList<CityBean>();
						CityBean cityBean = new CityBean();
						cityBean.setCityName("");
						cityBean.setCityCode("");
						cityList.add(cityBean);
					}
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
				} else {
					String desc = JSONUtils.getString(json, "msg", "");
					showMsg("获取数据失败失败，" + desc);
				}
			} catch (JSONException e) {
				showMsg("数据发生错误，请尝试重新启动程序");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (!isFirst) {
			// showProgressDialog("", "城市信息数据加载中...");
			// }
		}

	}
	private class GetDistrictNameTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String cityId=params[0];
			return HttpData.getDistrictName("other", "area_list", "1.0", "2", cityId);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
		
		
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
