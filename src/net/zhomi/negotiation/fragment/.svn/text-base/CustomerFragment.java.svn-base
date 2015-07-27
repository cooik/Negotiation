/**  
 * @Package net.zhomi.negotiation.fragment 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @date 2015-7-15 下午4:19:01 
 */
package net.zhomi.negotiation.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.bean.CustomerBean;
import net.zhomi.negotiation.customer.CustomerDetailsActivity;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.nogotiation.adapter.CustomerAdapter;
import net.zhomi.nogotiation.adapter.GridViewItemAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @ClassName: CustomerFragment
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-7-15 下午4:19:01
 */
public class CustomerFragment extends Fragment {

	private static String TAG = "CustomerFragment";

	private String[] items = { "ALL", "0-9", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	private GridView gd;
	// private ArrayAdapter<String> adapter = null;
	private ListView cusomer_lv;
	private List<CustomerBean> customers = new ArrayList<CustomerBean>();
	private CustomerAdapter cadapter = null;
	private GridViewItemAdapter gridViewItemAdapter;

	private String pinYin = "";// 筛选的拼音
	private String chooseZiMu = "";// 选择的字母拼音

	private boolean isStopLoadMore = false;// 是否停止加载更多

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_customer, null);
		gd = (GridView) v.findViewById(R.id.customer_gridview);
		cusomer_lv = (ListView) v.findViewById(R.id.customer_lv);
		gridViewItemAdapter = new GridViewItemAdapter(getActivity(), items);
		gd.setAdapter(gridViewItemAdapter);
		gridViewItemAdapter.setSeclection(0);
		gd.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gd.setNumColumns(7);
		gd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				gridViewItemAdapter.setSeclection(position);
				chooseZiMu = pinYin;
				pinYin = items[position];
				if (pinYin.equals("0-9")) {
					pinYin = "#";
				}
				if (chooseZiMu.equals(pinYin)) {
					return;
				}
				if (pinYin.equals("ALL")) {
					new GetCustomerListTask().execute("", "", "1", "10");
					return;
				}
				new GetCustomerListTask().execute(pinYin, "", "1", "10");
				Log.e("ysz", pinYin);
			}
		});
		cadapter = new CustomerAdapter(this.getActivity(),
				R.layout.customer_item, customers);
		cusomer_lv.setAdapter(cadapter);
		Log.d(TAG, Integer.toString(cadapter.getCount()));
		cusomer_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CustomerBean customerBean = customers.get(position);
				Intent intent = new Intent(CustomerFragment.this.getActivity(),
						CustomerDetailsActivity.class);
				intent.putExtra(HomeFragment.HOMENAME, customerBean.getName());
				intent.putExtra(HomeFragment.HOMEID, customerBean.getId());
				CustomerFragment.this.getActivity().startActivity(intent);
			}
		});
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GetCustomerListTask().execute(pinYin, "", "1", "10");
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	private class GetCustomerListTask extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String pinyin = params[0];
			String keyword = params[1];
			String page = params[2];
			String perpage = params[3];
			return HttpData.getCustomeList("user", "cust_list", "1.0", "2",
					App.getInstance().userInfo.getName(),
					App.getInstance().userInfo.getMd5(), pinyin, keyword, page,
					perpage);
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
					JSONArray customListArr = JSONUtils.getJSONArray(
							listJsonObject, "lists");
					if (customListArr.length() > 0) {
						customers = getData(customers, customListArr,
								isStopLoadMore);
					}
					Log.e("ysz", "customers.size()=" + customers.size());
					cadapter.refreshUi(customers);
					if (cusomer_lv.getVisibility() == View.GONE) {
						cusomer_lv.setVisibility(View.VISIBLE);
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					// Toast.makeText(CustomerFragment.this.getActivity(),
					// errorMsg, Toast.LENGTH_SHORT).show();
					if (cusomer_lv.getVisibility() == View.VISIBLE) {
						cusomer_lv.setVisibility(View.GONE);
					}
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	private List<CustomerBean> getData(List<CustomerBean> mList,
			JSONArray array, boolean isLoadeMore) {
		List<CustomerBean> list = new ArrayList<CustomerBean>();
		for (int i = 0; i < array.length(); i++) {
			CustomerBean bean = new CustomerBean();
			try {
				bean.setId(JSONUtils.getString(array.getJSONObject(i), "id", ""));
				// bean.setPic_url("http://doc.chengge.net/brand/2014/04/15/1_1c66373452.png");
				bean.setName(JSONUtils.getString(array.getJSONObject(i),
						"name", ""));
				bean.setTel(JSONUtils.getString(array.getJSONObject(i), "tel",
						""));
				bean.setPic_url(JSONUtils.getString(array.getJSONObject(i),
						"img_url", ""));
				list.add(bean);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (isLoadeMore) {
			mList.addAll(list);
		} else {
			mList = list;
		}
		return mList;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		cadapter.destroyDownloadEvent();
	}

}
