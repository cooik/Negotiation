package net.zhomi.negotiation.view;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.app.App;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BusinessMomentSortPopupWindow extends PopupWindow {
	private Activity context;
	private View mCanversLayout;
	private String[] arrays;
	private ChoseOnClikListener choseOnClikListener;

	public BusinessMomentSortPopupWindow(Context context, View mCanversLayout,
			ChoseOnClikListener choseOnClikListener) {
		super(context);
		this.context = (Activity) context;
		this.mCanversLayout = mCanversLayout;
		this.choseOnClikListener = choseOnClikListener;
		// arrays = new String[] {
		// context.getResources().getString(
		// R.string.business_moment_sort_one),
		// context.getResources().getString(
		// R.string.business_moment_sort_two),
		// context.getResources().getString(
		// R.string.business_moment_sort_three),
		// context.getResources().getString(
		// R.string.business_moment_sort_four),
		// context.getResources().getString(
		// R.string.business_moment_sort_five),
		// context.getResources().getString(
		// R.string.business_moment_sort_six),
		// context.getResources().getString(
		// R.string.business_moment_sort_seven),
		// context.getResources().getString(
		// R.string.business_moment_sort_eight), };
		arrays = App.newsCateArr;

	}

	/**
	 * 显示弹出框
	 * 
	 * @param:上下文
	 * @param：监听事件
	 * @param:控件位置标记
	 */
	@SuppressLint("InlinedApi")
	public void showPopupWindow() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.myshop_popupwindow, null);
		ListView mGridView = (ListView) view
				.findViewById(R.id.business_sort_listview);
		mGridView.setAdapter(new MyShopGirdAdapter());
		mGridView.setOnItemClickListener(new MyOnItemClickListener());
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setTouchable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		this.setAnimationStyle(R.style.PopupAnimation);
		this.setContentView(view);

	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String choose = arrays[position];
			choseOnClikListener.chose(choose);
			dismiss();
		}
	}

	/***
	 * 添加Adapter布局文件
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyShopGirdAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return arrays.length;
		}

		@Override
		public Object getItem(int position) {
			return arrays[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.business_moment_sort_textview, null);
				holder = new Holder();
				holder.mTextView = (TextView) convertView
						.findViewById(R.id.business_moment_sort_item_textview);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			holder.mTextView.setText(arrays[position]);
			return convertView;
		}

		class Holder {
			TextView mTextView;
		}
	}

	public interface ChoseOnClikListener {
		void chose(String chose);
	}

}
