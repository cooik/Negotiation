/**  
 * @Package net.zhomi.negotiation.fragment 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-15 下午4:19:52 
 */
package net.zhomi.negotiation.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.ProvinceCityChooseActivity;
import net.zhomi.negotiation.activity.ProvinceCityDistrictChooseActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.brand.ExpBrandActivity;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.nogotiation.adapter.FirstCateSpinnerAdapter;
import net.zhomi.nogotiation.adapter.GridViewItemAdapter;
import net.zhomi.nogotiation.adapter.SecondCateSpinnerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: BrandFragment
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangshouzhi
 * @date 2015-7-15 下午4:19:52
 */
public class BrandFragment extends Fragment implements LocationListener {
	List<String> data;
	private String[] items = { "ALL","0-9", "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private GridView gd;
	private GridViewItemAdapter gridViewItemAdapter;
	/** 一级业态 **/
	private List<Map<String, String>> mFirstCateList;
	private String[] mFirstCateArr;
	private Spinner firstCateSpinner;
	private FirstCateSpinnerAdapter firstCateSpinnerAdapter;
	private String firstCateId = "";
	/** 二级业态 **/
	private List<Map<String, String>> secondCateList;
	private String[] secondCateArr;
	private Spinner secondCateSpinner;
	private SecondCateSpinnerAdapter secondCateSpinnerAdapter;
	private String secondCateId = "";
	private String pinyin;
	private Button btn_search;

	private LocationManager locationManager;
	private String bestProvider;
	private Geocoder geocoder;

	private TextView seclectCityTextView;
	private String cityName;
	private String name = "";// 搜索的名称
	private String cityId = "";

	private String lon = "";// 经度
	private String lat = "";// 纬度
	public final static int REQUEST_CODE_CITY = 10037;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_brand, null);
		gd = (GridView) v.findViewById(R.id.gridview);
		gridViewItemAdapter = new GridViewItemAdapter(getActivity(), items);
		gd.setAdapter(gridViewItemAdapter);
		gridViewItemAdapter.setSeclection(0);
		gd.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gd.setNumColumns(7);
		btn_search = (Button) v.findViewById(R.id.btn_find);
		seclectCityTextView = (TextView) v.findViewById(R.id.tv_curr_city);
		v.findViewById(R.id.ly_curr_city).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								ProvinceCityChooseActivity.class);
						startActivityForResult(intent, REQUEST_CODE_CITY);
					}
				});
		firstCateSpinner = (Spinner) v.findViewById(R.id.brand_first_cate);
		secondCateSpinner = (Spinner) v.findViewById(R.id.brand_second_cate);
		firstCateSpinnerAdapter = new FirstCateSpinnerAdapter(getActivity(),
				R.layout.spinner_checked_text, mFirstCateArr);
		firstCateSpinnerAdapter
				.setDropDownViewResource(R.layout.spinner_item_layout);
		firstCateSpinner.setAdapter(firstCateSpinnerAdapter);
		firstCateSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						firstCateId = mFirstCateList.get(position).get(
								mFirstCateArr[position]);
						Log.e("ysz", "firstCateId=" + firstCateId);
						if (firstCateId.equals("")) {
							secondCateArr = new String[] { "请选择二级业态" };
							secondCateSpinnerAdapter.refreshUi(secondCateArr);
							secondCateSpinner.setClickable(false);
							return;
						}
						secondCateSpinner.setClickable(true);
						new GetSecondCateTask().execute(firstCateId);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
		secondCateArr = new String[] { "请选择二级业态" };
		// Map<String, String> map1 = new HashMap<String, String>();
		// secondCateList = new ArrayList<Map<String, String>>();
		// map1.put(secondCateArr[0], "1111111");
		// secondCateList.add(map1);
		secondCateSpinnerAdapter = new SecondCateSpinnerAdapter(getActivity(),
				R.layout.spinner_checked_text, secondCateArr);
		secondCateSpinner.setAdapter(secondCateSpinnerAdapter);
		secondCateSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (secondCateList == null
								|| secondCateList.get(position) == null) {
							return;
						}
						secondCateId = secondCateList.get(position).get(
								secondCateArr[position]);
						Log.e("ysz", "secondCateId" + secondCateId);

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}

				});
		gd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				gridViewItemAdapter.setSeclection(position);
				pinyin = (String) gd.getItemAtPosition(position);
				if (pinyin.equals("0-9")) {
					pinyin="#";
				}
				if (pinyin.equals("ALL")) {
					pinyin="";
				}
				Log.e("ysz", "pinyin:" + pinyin);
			}
		});

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cityId.equals("")) {
					Toast.makeText(BrandFragment.this.getActivity(), "请选择地区",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(getActivity(),
						ExpBrandActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("lon", lon);
				bundle.putString("lat", lat);
				bundle.putString("cityname", cityName);
				bundle.putString("cityid", cityId);
				bundle.putString("name", name);
				bundle.putString("firstCateId", firstCateId);
				bundle.putString("secondCateId", secondCateId);
				bundle.putString("pinyin", pinyin);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFirstCateArr = App.firstCateArr;
		mFirstCateList = App.firstCateList;
		// Get the location manager
		locationManager = (LocationManager) BrandFragment.this.getActivity()
				.getSystemService(
						BrandFragment.this.getActivity().LOCATION_SERVICE);
		geocoder = new Geocoder(BrandFragment.this.getActivity());
		// List all providers:
		Criteria criteria = new Criteria();
		// 查询精度：高
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 是否查询海拨：否
		criteria.setAltitudeRequired(false);
		// 是否查询方位角 : 否
		criteria.setBearingRequired(false);
		// 是否允许付费：是
		criteria.setCostAllowed(true);
		// 电量要求：低
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		bestProvider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		getLocation(location);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	private class GetSecondCateTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String id = params[0];
			return HttpData.getFirstCate("cate", "cate_list", "1.0", "2", "2",
					id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONArray jsonArray = JSONUtils.getJSONArray(jsonObject,
							"data");
					Log.e("ysz", "" + jsonArray.length());
					if (jsonArray.length() > 0) {
						secondCateArr = new String[jsonArray.length() + 1];
						secondCateList = new ArrayList<Map<String, String>>();
						secondCateArr[0] = "请选择二级业态";
						Map<String, String> map1 = new HashMap<String, String>();
						map1.put(secondCateArr[0], "1111111");
						secondCateList.add(map1);
						for (int i = 0; i < jsonArray.length(); i++) {
							String id = JSONUtils.getString(
									jsonArray.getJSONObject(i), "id", "");
							String name = JSONUtils.getString(
									jsonArray.getJSONObject(i), "name", "");
							secondCateArr[i + 1] = name;
							Map<String, String> map2 = new HashMap<String, String>();
							map2.put(name, id);
							secondCateList.add(map2);
						}
						secondCateSpinnerAdapter.refreshUi(secondCateArr);
						secondCateSpinner.setSelection(0);
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					Toast.makeText(BrandFragment.this.getActivity(), errorMsg,
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(BrandFragment.this.getActivity(), "获取二级业态失败!",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CITY
				&& resultCode == BrandFragment.this.getActivity().RESULT_OK
				&& data != null) {
			String[] cityStrings = data
					.getStringArrayExtra(ProvinceCityChooseActivity.INTENT_FLAG_RESULT_STRING);
			if (cityStrings == null || cityStrings.length < 2) {
				return;
			}
			cityName = cityStrings[0];
			seclectCityTextView.setText(cityName);
			cityId = cityStrings[1];

		}
	}

	@Override
	public void onLocationChanged(Location location) {
		getLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	private void getLocation(Location location) {
		if (location==null) {
			return;
		}
		lon = String.valueOf(location.getLongitude());
		lat = String.valueOf(location.getLatitude());
	}

}
