package net.zhomi.negotiation.brand;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.bean.BrandBean;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.view.ExpandTabView;
import net.zhomi.negotiation.view.ViewLeft;
import net.zhomi.negotiation.view.ViewMiddle;
import net.zhomi.negotiation.view.ViewRight;
import net.zhomi.nogotiation.adapter.BrandAdapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ExpBrandActivity extends BaseActivity {

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	// private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private String cityId;
	private String cityName;
	private String name;
	private String firstCateId;
	private String secondCateId;
	private String pinYin;
	private ListView lv;
	private List<BrandBean> brandList;
	private String page = "1";
	private String perPage = "20";
	private boolean isStopLoadMore = false;// 是否停止加载更多
	private BrandAdapter adapter;

	private String lon;
	private String lat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main111);
		initView();
		initVaule();
		initListener();

	}

	private void initView() {

		initTitle();
		menu_search.setVisibility(View.VISIBLE);
		title.setText("品牌库");
		back.setBackgroundResource(R.drawable.ic_launcher);
		back.setVisibility(View.VISIBLE);
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);
		lv = (ListView) findViewById(R.id.result_brand_list);
	}

	private void initVaule() {

		// mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("业态");
		mTextArray.add("首字母");
		// mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle(viewMiddle.getShowText(), 0);
		expandTabView.setTitle(viewRight.getShowText(), 1);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		lon = bundle.getString("lon");
		lat = bundle.getString("lat");
		cityId = bundle.getString("cityid");
		cityName = bundle.getString("cityname");
		name = bundle.getString("name");
		firstCateId = bundle.getString("firstCateId");
		secondCateId = bundle.getString("secondCateId");
		pinYin = bundle.getString("pinyin");
		Log.d("debuginfo", "cityId:" + cityId + ",cityName:" + cityName
				+ ",name:" + name + ",firstCateId:" + firstCateId
				+ ",secondCateId:" + secondCateId + ",pinyin:" + pinYin);
		new GetBrandListTask().execute(cityId, name, firstCateId, secondCateId,
				pinYin, page, perPage);

		brandList = new ArrayList<BrandBean>();
		adapter = new BrandAdapter(ExpBrandActivity.this,
				R.layout.brand_list_item, brandList, cityName);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ExpBrandActivity.this,
						BrandDetailActivity.class);
				BrandBean brandBean = brandList.get(position);
				Bundle bundle = new Bundle();
				bundle.putString("lon", lon);
				bundle.putString("lat", lat);
				bundle.putString("cityid", cityId);
				bundle.putString("brandid", brandBean.getId());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void initListener() {
		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
			@Override
			public void getValue(String firstId, String secondId) {
				firstCateId=firstId;
				secondCateId=secondId;
				expandTabView.onPressBack();
				new GetBrandListTask().execute(cityId, name, firstCateId, secondCateId,
						pinYin, page, perPage);

			}
		});

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String showText) {
				expandTabView.onPressBack();
				pinYin=showText;
				new GetBrandListTask().execute(cityId, name, firstCateId, secondCateId,
						pinYin, page, perPage);
			}

		});

	}

//	private void onRefresh(View view, String showText) {
//
//		expandTabView.onPressBack();
//		int position = getPositon(view);
//		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
//			// expandTabView.setTitle(showText, position);
//		}
//		Toast.makeText(ExpBrandActivity.this, showText, Toast.LENGTH_SHORT)
//				.show();
//
//	}
//
//	private int getPositon(View tView) {
//		for (int i = 0; i < mViewArray.size(); i++) {
//			if (mViewArray.get(i) == tView) {
//				return i;
//			}
//		}
//		return -1;
//	}

	@Override
	public void onBackPressed() {
		if (!expandTabView.onPressBack()) {
			finish();
		}

	}

	private class GetBrandListTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String cityid = params[0];
			String searchName = params[1];
			String firstcateId = params[2];
			String secondcateId = params[3];
			String pinyin = params[4];
			String page = params[5];
			String perpage = params[6];
			return HttpData.getBrandListOfCate("brand", "brand_list", "1.0",
					"2", cityid, searchName, firstcateId, secondcateId, pinyin,
					page, perpage);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null && TextUtils.isEmpty(result)) {
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONObject listJsonObject = JSONUtils.getJSONObject(
							jsonObject, "data");
					JSONArray brandListArr = JSONUtils.getJSONArray(
							listJsonObject, "brand_list");
					if (brandListArr.length() > 0) {
						brandList = new ArrayList<BrandBean>();
						brandList = getData(brandList, brandListArr,
								isStopLoadMore);
						// if (brandList == null) {
						// return;
						// } else {
						// adapter = new BrandAdapter(ExpBrandActivity.this,
						// R.layout.brand_list_item, brandList,
						// cityName);
						// lv.setAdapter(adapter);
						// }
					}
					Log.e("ysz", "customers.size()=" + brandList.size());
					adapter.refreshUi(brandList);

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private List<BrandBean> getData(List<BrandBean> mList, JSONArray resultArr,
			boolean isLoadMore) {
		List<BrandBean> list = new ArrayList<BrandBean>();
		for (int i = 0; i < resultArr.length(); i++) {
			BrandBean brandBean = new BrandBean();
			try {
				brandBean.setId(JSONUtils.getString(resultArr.getJSONObject(i),
						"id", ""));
				brandBean.setLogo(JSONUtils.getString(
						resultArr.getJSONObject(i), "logo", ""));
				brandBean.setName(JSONUtils.getString(
						resultArr.getJSONObject(i), "name", ""));
				brandBean.setType_name(JSONUtils.getString(
						resultArr.getJSONObject(i), "type_name", ""));
				brandBean.setLevel_name(JSONUtils.getString(
						resultArr.getJSONObject(i), "level_name", ""));
				brandBean.setCity_poi_num(JSONUtils.getString(
						resultArr.getJSONObject(i), "city_poi_num", ""));
				brandBean.setCountry_poi_num(JSONUtils.getString(
						resultArr.getJSONObject(i), "country_poi_num", ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}

			list.add(brandBean);

		}
		if (isLoadMore) {
			mList.addAll(list);
		} else {
			mList = list;
		}
		return mList;

	}
}
