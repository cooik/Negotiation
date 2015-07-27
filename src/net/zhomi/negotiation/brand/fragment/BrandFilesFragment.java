package net.zhomi.negotiation.brand.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.hp.hpl.sparta.Text;

import net.zhaomi.negotiation.R;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BrandFilesFragment extends Fragment {
	ArrayList<HashMap<String, Object>> item = new ArrayList<HashMap<String, Object>>();
	private String brandId;

	public BrandFilesFragment(String brandId) {
		this.brandId = brandId;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_brand_files, null);
		GridView gv_o = (GridView) v.findViewById(R.id.gv_opponent);
		GridView gv_other = (GridView) v.findViewById(R.id.other_brand);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImage", R.drawable.brand_logo);
		map.put("itemName", "阿迪达斯");
		item.add(map);
		item.add(map);
		gv_o.setAdapter(new SimpleAdapter(getActivity(), item,
				R.layout.brand_detail_gvitem, new String[] { "itemImage",
						"itemName" }, new int[] { R.id.brand_logo,
						R.id.brand_name }) {
		});
		gv_other.setAdapter(new SimpleAdapter(getActivity(), item,
				R.layout.brand_detail_gvitem, new String[] { "itemImage",
						"itemName" }, new int[] { R.id.brand_logo,
						R.id.brand_name }) {
		});
		TextView tv = (TextView) v.findViewById(R.id.area);
		tv.setText(Html.fromHtml(getResources().getString(
				R.string.brand_math_code)));
		TextView tv_e = (TextView) v.findViewById(R.id.power);
		tv_e.setText(Html.fromHtml(getResources().getString(
				R.string.brand_math_code_2)));
		return v;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
