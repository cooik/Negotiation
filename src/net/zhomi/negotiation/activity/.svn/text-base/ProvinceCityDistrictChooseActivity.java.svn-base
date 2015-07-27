package net.zhomi.negotiation.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.ProvinceCityChooseActivity.GetCityName;
import net.zhomi.negotiation.activity.ProvinceCityChooseActivity.GetProvinceName;
import net.zhomi.negotiation.bean.AreaBean;
import net.zhomi.negotiation.bean.CityBean;
import net.zhomi.negotiation.bean.ProvinceBean;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.utils.PinYinComparator;
import net.zhomi.negotiation.utils.SystemUtils;
import net.zhomi.nogotiation.adapter.AreaAdapter;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

public class ProvinceCityDistrictChooseActivity extends BaseActivity {
	/** 选择城市后设置到结果里的标签 */
	public static final String INTENT_FLAG_RESULT_STRING = "cityinfo";

	private ListView provinceListView;
	private ProvinceAdapter provinceAdapter;
	private ListView cityListView;
	private CityAdapter cityAdapter;
	private ListView areaListView;
	private AreaAdapter areaAdapter;

	private boolean isFirst = true;// 是否第一次加载

	private List<ProvinceBean> provinceLists = new ArrayList<ProvinceBean>();
	private List<CityBean> cityList = new ArrayList<CityBean>();
	private List<AreaBean> areaList = new ArrayList<AreaBean>();
	private String provId = "";
	private String currentProvinceChoose="安徽";
	private String currentCityChoose="安庆";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_pro_city_dis);
		initView();
		// openGpsSettings();
	}

	private void openGpsSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			doWork();
			return;
		}
		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SETTINGS);
		startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
	}

	private void doWork() {
		String msg = "";
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		// 获得最好的定位效果
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		// 使用省电模式
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 获得当前的位置提供者
		String provider = locationManager.getBestProvider(criteria, true);
		// 获得当前的位置
		Location location = locationManager.getLastKnownLocation(provider);

		Geocoder gc = new Geocoder(this);
		List<Address> addresses = null;
		try {
			addresses = gc.getFromLocation(location.getLatitude(),
					location.getLongitude(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (addresses.size() > 0) {
			msg += "AddressLine：" + addresses.get(0).getAddressLine(0) + "\n";
			msg += "CountryName：" + addresses.get(0).getCountryName() + "\n";
			msg += "Locality：" + addresses.get(0).getLocality() + "\n";
			msg += "FeatureName：" + addresses.get(0).getFeatureName();
			Log.d("---", "定位信息:" + msg);
		}

	}

	private void initView() {
		initTitle();
		back.setVisibility(View.VISIBLE);

		new GetProvinceName().execute();
		provinceListView = (ListView) findViewById(R.id.location1_province_list);
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) provinceListView
				.getLayoutParams(); // 取控件textView当前的布局参数
		linearParams.height = LayoutParams.WRAP_CONTENT;
		linearParams.width = SystemUtils.getScreenWidth(this) / 3;
		provinceListView.setLayoutParams(linearParams);
		provinceAdapter = new ProvinceAdapter(
				ProvinceCityDistrictChooseActivity.this, provinceLists);
		provinceListView.setAdapter(provinceAdapter);
		provinceListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProvinceBean provinceBean = provinceLists.get(position);
				String provValue = provinceBean.getProvinceCode();
				Log.d("---", "prov value=" + provValue);
				provId = provValue;
				Log.d("---", "prov value=" + provId);
				currentProvinceChoose=provValue;
				if (provValue.equals(currentProvinceChoose)) {
					return;
				}
				new GetCityName().execute(provValue);
			}
		});
		cityListView = (ListView) findViewById(R.id.location1_city_list);
		LinearLayout.LayoutParams cityLinearParams = (LinearLayout.LayoutParams) cityListView
				.getLayoutParams(); // 取控件textView当前的布局参数
		cityLinearParams.height = LayoutParams.WRAP_CONTENT;
		cityLinearParams.width = SystemUtils.getScreenWidth(this) / 3;
		cityListView.setLayoutParams(cityLinearParams);
		cityAdapter = new CityAdapter(ProvinceCityDistrictChooseActivity.this,
				cityList);
		cityListView.setAdapter(cityAdapter);
		cityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CityBean cityBean = cityList.get(position);
				String cityName = cityBean.getCityName();
				String cityIdString = cityBean.getCityCode();
				if (cityName.equals(currentCityChoose)) {
					return;
				}
				new GetDistrictNameTask().execute(cityIdString);
				currentCityChoose=cityName;
//				String[] citys = { cityName, cityIdString };
//				setCity(citys);
			}
		});
		areaListView = (ListView) findViewById(R.id.location1_area_list);
		LinearLayout.LayoutParams areaLinearParams = (LinearLayout.LayoutParams) areaListView
				.getLayoutParams(); // 取控件textView当前的布局参数
		areaLinearParams.height = LayoutParams.WRAP_CONTENT;
		areaLinearParams.width = SystemUtils.getScreenWidth(this) / 3;
		areaListView.setLayoutParams(cityLinearParams);
		areaAdapter = new AreaAdapter(areaList, this);
		areaListView.setAdapter(areaAdapter);

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
					// provinceAdapter = new ProvinceAdapter(
					// ProvinceCityDiatrictChooseActivity.this, provinceLists);
					// provinceListView.setAdapter(provinceAdapter);
					provinceAdapter.refreshUi(provinceLists);
					break;
				}
			case 2:
				// if (cityList.size()>0) {
				// cityAdapter = new CityAdapter(
				// ProvinceCityDistrictChooseActivity.this, cityList);
				// cityListView.setAdapter(cityAdapter);
				cityAdapter.refreshUi(cityList);
				// }
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
						new GetDistrictNameTask().execute(cityList.get(0)
								.getCityCode());
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

	private class GetDistrictNameTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String cityId = params[0];
			return HttpData.getDistrictName("other", "area_list", "1.0", "2",
					cityId);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null || TextUtils.isEmpty(result)) {
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONArray dataArr=JSONUtils.getJSONArray(jsonObject, "data");
					if (dataArr.length()>0) {
						areaList=new ArrayList<AreaBean>();
						for (int i = 0; i < dataArr.length(); i++) {
							AreaBean areaBean=new AreaBean();
							areaBean.setName(JSONUtils.getString(dataArr.getJSONObject(i), "name", ""));
							areaBean.setId(JSONUtils.getString(dataArr.getJSONObject(i), "id", ""));
							areaList.add(areaBean);
						}
						areaAdapter.refresUi(areaList);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
