package net.zhomi.negotiation.brand.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.brand.adapter.BrandContactsAdapter;
import net.zhomi.negotiation.brand.bean.BrandContactsBean;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class BrandContactsFragment extends Fragment {

	private ListView contactsListView;
	private List<BrandContactsBean> contactsList;
	private BrandContactsAdapter contactsAdapter;
	private String brandId;

	public BrandContactsFragment(String brandId) {
		this.brandId = brandId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_brand_contacts, null);
		initView(v);
		return v;
	}

	private void initView(View v) {
		contactsListView = (ListView) v
				.findViewById(R.id.brand_contacts_listview);
		contactsList = new ArrayList<BrandContactsBean>();
		contactsAdapter = new BrandContactsAdapter(contactsList, getActivity());
		contactsListView.setAdapter(contactsAdapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void getData() {
		new GetContactsOfBrandTask().execute(brandId);
	}

	private class GetContactsOfBrandTask extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String brandid = params[0];
			return HttpData.getContactsListOfBrand("brand", "brand_contact",
					"1.0", "2", brandid);
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
					JSONArray dataArr = JSONUtils.getJSONArray(jsonObject,
							"data");
					if (dataArr.length() > 0) {
						contactsList = new ArrayList<BrandContactsBean>();
						for (int i = 0; i < dataArr.length(); i++) {
							BrandContactsBean bean = new BrandContactsBean();
							bean.setId(JSONUtils.getString(
									dataArr.getJSONObject(i), "id", ""));
							bean.setName(JSONUtils.getString(
									dataArr.getJSONObject(i), "name", ""));
							bean.setAgentName(JSONUtils.getString(
									dataArr.getJSONObject(i), "agent_name", ""));
							bean.setJob(JSONUtils.getString(
									dataArr.getJSONObject(i), "job", ""));
							bean.setEmail(JSONUtils.getString(
									dataArr.getJSONObject(i), "email", ""));
							bean.setTel(JSONUtils.getString(
									dataArr.getJSONObject(i), "tel", ""));
							contactsList.add(bean);
						}
						contactsAdapter.refreshUi(contactsList);
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					Toast.makeText(BrandContactsFragment.this.getActivity(),
							errorMsg, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}
