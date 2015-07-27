/**
 * 
 */
package net.zhomi.nogotiation.adapter;

import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.ProvinceBean;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 
 * @ClassName: ProvinceAdapter
 * 
 * @Description: TODO(这里用一句话描述这个类的作用)
 * 
 * @author 杨守志
 * 
 * @date 2014-11-7 下午4:49:20
 */
public class ProvinceAdapter extends BaseAdapter implements SectionIndexer{
	private Context mContext;
//	private String[] provinceName;
	private List<ProvinceBean> mProvinceList;
	static int i;
	static String[] first;

	public ProvinceAdapter(Context mContext,List<ProvinceBean> provinceList) {
		this.mContext = mContext;
		mProvinceList = provinceList;
	}

	@Override
	public int getCount() {
		return mProvinceList.size();
	}

	@Override
	public Object getItem(int position) {
		return mProvinceList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void refreshUi(List<ProvinceBean> list){
		mProvinceList.clear();
		if (list!=null&&list.size()>0) {
			mProvinceList.addAll(list);
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String nickName = mProvinceList.get(position).getPyProvinceName();
		i = position;

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.province_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvCatalog = (TextView) convertView
					.findViewById(R.id.contactitem_catalog);
			viewHolder.tvNick = (TextView) convertView
					.findViewById(R.id.contactitem_nick);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String catalog = converterToFirstSpell(nickName).substring(0, 1);
		viewHolder.tvCatalog.setText(catalog);
		if (position == 0) {
			viewHolder.tvCatalog.setVisibility(View.VISIBLE);
			viewHolder.tvCatalog.setText(catalog);
		} else {
			String lastCatalog = converterToFirstSpell(mProvinceList.get(position - 1).getPyProvinceName())
					.substring(0, 1);
			if (catalog.equals(lastCatalog)) {
				viewHolder.tvCatalog.setVisibility(View.GONE);
			} else {
				viewHolder.tvCatalog.setVisibility(View.VISIBLE);
				viewHolder.tvCatalog.setText(catalog);
			}
		}

		// viewHolder.ivAvatar.setImageResource(R.drawable.default_avatar);
		viewHolder.tvNick.setText(mProvinceList.get(position).getProvinceName());
		return convertView;
	}

	static class ViewHolder {
		TextView tvCatalog;
		// ImageView ivAvatar;
		TextView tvNick;
	}

	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < mProvinceList.size(); i++) {
			String l = converterToFirstSpell(mProvinceList.get(i).getProvinceName()).substring(0, 1);
			char firstChar = l.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}
}
