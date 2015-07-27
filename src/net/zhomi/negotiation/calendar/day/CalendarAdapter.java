package net.zhomi.negotiation.calendar.day;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.zhaomi.negotiation.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 日历gridview中的每一个item显示的textview
 * 
 * @author lmw
 * 
 */
public class CalendarAdapter extends BaseAdapter {
	private boolean isLeapyear = false; // 是否为闰�?
	private int daysOfMonth = 0; // 某月的天�?
	private int dayOfWeek = 0; // 具体某一天是星期�?
	private int lastDaysOfMonth = 0; // 上一个月的�?天数
	private Context context;
	private String[] dayNumber = new String[35]; // �?��gridview中的日期存入此数组中
	// private static String week[] =
	// {"周日","周一","周二","周三","周四","周五","周六"};
	private SpecialCalendar sc = null;
	private Resources res = null;
	private Drawable drawable = null;

	private String currentYear = "";
	private String currentMonth = "";
	private String currentDay = "";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private int currentFlag = -1; // 用于标记当天
	private int[] schDateTagFlag = null; // 存储当月�?��的日程日�?

	private String showYear = ""; // 用于在头部显示的年份
	private String showMonth = ""; // 用于在头部显示的月份
	private String animalsYear = "";
	private String leapMonth = ""; // 闰哪�?���?
	private String cyclical = ""; // 天干地支
	// 系统当前时间
	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";
	private int clickTemp = -1;
	private String[] noticeDayArr;

	// ��ʶѡ���Item
	public void setSeclection(int position) {
		clickTemp = position;
	}

	public CalendarAdapter() {
		Date date = new Date();
		sysDate = sdf.format(date); // 当期日期
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];

	}

	public CalendarAdapter(Context context, Resources rs, int jumpMonth,
			int jumpYear, int year_c, int month_c, int day_c,String[] dayArr) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		this.res = rs;
		noticeDayArr=dayArr;

		int stepYear = year_c + jumpYear;
		int stepMonth = month_c + jumpMonth;
		if (stepMonth > 0) {
			// �?���?��月滑�?
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// �?���?��月滑�?
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		currentYear = String.valueOf(stepYear);
		; // 得到当前的年�?
		currentMonth = String.valueOf(stepMonth); // 得到本月
													// （jumpMonth为滑动的次数，每滑动�?��就增加一月或减一月）
		currentDay = String.valueOf(day_c); // 得到当前日期是哪�?

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));

	}

	public CalendarAdapter(Context context, Resources rs, int year, int month,
			int day) {
		this();
		this.context = context;
		sc = new SpecialCalendar();
		this.res = rs;
		currentYear = String.valueOf(year);
		; // 得到跳转到的年份
		currentMonth = String.valueOf(month); // 得到跳转到的月份
		currentDay = String.valueOf(day); // 得到跳转到的�?

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));

	}

	@Override
	public int getCount() {
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void refreshUi(String[] dayArr) {
		noticeDayArr = null;
		if (dayArr != null && dayArr.length > 0) {
			noticeDayArr = dayArr;
		}
		notifyDataSetChanged();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_calendar, null);
		}
		TextView textView = (TextView) convertView
				.findViewById(R.id.tv_calendar);
		String d = dayNumber[position].split("\\.")[0];
		String dv = dayNumber[position];
//
		SpannableString sp = new SpannableString(d);
//		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
//				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(),
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		if (dv != null || dv != "") {
//			sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1,
//					dayNumber[position].length(),
//					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
		// sp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 14, 16,
		// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
		textView.setText(sp);
		textView.setTextColor(Color.GRAY);

		// if(position<7){
		// //设置�?
		// textView.setTextColor(Color.WHITE);
		// textView.setBackgroundColor(color.search_txt_color);
		// textView.setTextSize(14);
		// }

		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			// 当前月信息显�?
			textView.setTextColor(Color.BLACK);// 当月字体设黑
			// drawable = res.getDrawable(R.drawable.current_day_bgc);
			drawable = res.getDrawable(R.drawable.circle_message);

		}
		if (schDateTagFlag != null && schDateTagFlag.length > 0) {
			for (int i = 0; i < schDateTagFlag.length; i++) {
				if (schDateTagFlag[i] == position) {
					// 设置日程标记背景
					textView.setBackgroundResource(R.drawable.circle_message);
				}
			}
		}
		if (currentFlag == position) {
			// 设置当天的背�?
			drawable = res.getDrawable(R.drawable.circle_message);
			textView.setBackgroundDrawable(drawable);
			textView.setTextColor(Color.WHITE);
		}
		if (clickTemp == position) {
			textView.setSelected(true);
			textView.setTextColor(Color.WHITE);
			textView.setBackgroundResource(R.drawable.circle_message);
		} else {
			textView.setSelected(false);
			textView.setTextColor(Color.BLACK);
			textView.setBackgroundColor(Color.TRANSPARENT);
			StringBuffer buffer=new StringBuffer();
			buffer.append(getCurrentYear(position)).append("-");
			if (getCurrentMonth(position)<10) {
				buffer.append(0).append(getCurrentMonth(position)).append("-");
			}else{
				buffer.append(getCurrentMonth(position)).append("-");
			}
			if (dayNumber[position].length()<2) {
				buffer.append(0).append(dayNumber[position].split("\\.")[0]);
			}else{
				buffer.append(dayNumber[position].split("\\.")[0]);
			}
			for (int i = 0; i < noticeDayArr.length; i++) {
				if (buffer.toString().equals(noticeDayArr[i])) {
					textView.setSelected(true);
					textView.setTextColor(Color.WHITE);
					textView.setBackgroundResource(R.drawable.circle_notice_bg);
					break;
				}
			}
		}
		return convertView;
	}

	// 得到某年的某月的天数且这月的第一天是星期�?
	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰�?
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的�?天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期�?
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的�?天数
		Log.d("DAY", isLeapyear + " ======  " + daysOfMonth
				+ "  ============  " + dayOfWeek + "  =========   "
				+ lastDaysOfMonth);
		getweek(year, month);
	}

	// 将一个月中的每一天的值添加入数组dayNuber�?
	private void getweek(int year, int month) {
		int j = 1;
		int flag = 0;
		String lunarDay = "";

		// 得到当前月的�?��日程日期(这些日期�?��标记)

		for (int i = 0; i < dayNumber.length; i++) {
			// 周一
			// if(i<7){
			// dayNumber[i]=week[i]+"."+" ";
			// }
			if (i < dayOfWeek) { // 前一个月
				int temp = lastDaysOfMonth - dayOfWeek + 1;
				dayNumber[i] = (temp + i) + "." + lunarDay;

			} else if (i < daysOfMonth + dayOfWeek) { // 本月
				String day = String.valueOf(i - dayOfWeek + 1); // 得到的日�?
				dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
				// 对于当前月才去标记当前日�?
				if (sys_year.equals(String.valueOf(year))
						&& sys_month.equals(String.valueOf(month))
						&& sys_day.equals(day)) {
					// 标记当前日期
					currentFlag = i;
				}
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
			} else { // 下一个月
				dayNumber[i] = j + "." + lunarDay;
				j++;
			}
		}

		String abc = "";
		for (int i = 0; i < dayNumber.length; i++) {
			abc = abc + dayNumber[i] + ":";
		}
		Log.d("DAYNUMBER", abc);

	}

	public void matchScheduleDate(int year, int month, int day) {

	}

	/**
	 * 点击每一个item时返回item中的日期
	 * 
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position) {
		return dayNumber[position];
	}

	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * 
	 * @return
	 */
	public int getStartPositon() {
		return dayOfWeek + 7;
	}

	/**
	 * 在点击gridView时，得到这个月中�?���?��的位�?
	 * 
	 * @return
	 */
	public int getEndPosition() {
		return (dayOfWeek + daysOfMonth + 7) - 1;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}

	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}
	

	public String[] getDayNumbers() {
		return dayNumber;
	}

	public int getCurrentMonth(int position) {
		int thisDayOfWeek = sc.getWeekDayOfLastMonth(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth),Integer.parseInt(currentDay));
		if (thisDayOfWeek != 7) {
			if (position < thisDayOfWeek) {
				return Integer.parseInt(currentMonth) - 1 == 0 ? 12 : Integer
						.parseInt(currentMonth) - 1;
			} else {
				Log.e("ysz", "dayNumber[clickTemp]"+dayNumber[clickTemp].split("\\.")[0]);
				if(dayNumber[clickTemp].split("\\.")[0].equals("1")){
					return Integer.parseInt(currentMonth)+1;
				}
				return Integer.parseInt(currentMonth);
			}
		} else {
			return Integer.parseInt(currentMonth);
		}
	}

	public int getCurrentYear(int position) {
		int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));
		if (thisDayOfWeek != 7) {
			if (position < thisDayOfWeek) {
				return Integer.parseInt(currentMonth) - 1 == 0 ? Integer
						.parseInt(currentYear) - 1 : Integer
						.parseInt(currentYear);
			} else {
				return Integer.parseInt(currentYear);
			}
		} else {
			return Integer.parseInt(currentYear);
		}
	}

	public int getTodayPosition() {
//		int todayWeek = sc.getWeekDayOfLastMonth(Integer.parseInt(sys_year),
//				Integer.parseInt(sys_month), Integer.parseInt(sys_day));
//		if (todayWeek == 7) {
//			clickTemp = 0;
//		} else {
//			clickTemp = todayWeek;
//		}
//		return clickTemp;
		if (currentFlag == 42) {
			clickTemp = 0;
		} else {
			clickTemp = currentFlag;
		}
		return clickTemp;
	}
	//�õ�ĳ���ж�������
	public int getDaysOfMonth(boolean isLeapyear, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			daysOfMonth = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			daysOfMonth = 30;
			break;
		case 2:
			if (isLeapyear) {
				daysOfMonth = 29;
			} else {
				daysOfMonth = 28;
			}

		}
		return daysOfMonth;
	}
	
	

}
