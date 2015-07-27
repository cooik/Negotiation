package net.zhomi.negotiation.view;


import net.zhaomi.negotiation.R;
import net.zhomi.nogotiation.adapter.GridViewItemAdapter;
import net.zhomi.nogotiation.adapter.GridViewTextAdapter;
import net.zhomi.nogotiation.adapter.TextAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;


public class ViewRight extends RelativeLayout implements ViewBaseAction{

	private GridView gd;
	private String[] items = { "ALL", "0-9", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	private OnSelectListener mOnSelectListener;
	private GridViewTextAdapter adapter;
//	private GridViewItemAdapter gridViewItemAdapter;
	private String showText = "首字母";
	private Context mContext;
	private String pinYin = "";// 筛选的拼音
	private String currentPinYin="";

	public String getShowText() {
		return showText;
	}

	public ViewRight(Context context) {
		super(context);
		init(context);
	}

	public ViewRight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_expsingle, this, true);
		gd = (GridView) findViewById(R.id.exp_gridview);
		adapter=new GridViewTextAdapter(context, items);
		adapter.setSelectedPosition(0);
		gd.setAdapter(adapter);
		gd.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gd.setNumColumns(7);
		adapter.setOnItemClickListener(new GridViewTextAdapter.OnItemClickListener() {
			
			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				adapter.setSelectedPosition(position);
				pinYin = items[position];
				if (currentPinYin.equals(pinYin)) {
					return;
				}
				currentPinYin=pinYin;
				if (pinYin.equals("0-9")) {
					pinYin = "#";
				}
				if (pinYin.equals("ALL")) {
					pinYin="";
				}
				if (mOnSelectListener != null) {
					showText = pinYin;
					mOnSelectListener.getValue(pinYin);
				}
				Log.e("ysz", pinYin);
				
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String showText);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
