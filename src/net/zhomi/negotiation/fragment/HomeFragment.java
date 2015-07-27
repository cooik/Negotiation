/**  
 * @Package net.zhomi.negotiation.fragment 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-15 下午4:08:39 
 */
package net.zhomi.negotiation.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.AddNegotiationRecordActivity;
import net.zhomi.negotiation.activity.BusinessMomentActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.bean.TaskTipBean;
import net.zhomi.negotiation.calendar.day.CalendarAdapter;
import net.zhomi.negotiation.calendar.day.DateAdapter;
import net.zhomi.negotiation.calendar.day.SpecialCalendar;
import net.zhomi.negotiation.customer.CustomerDetailsActivity;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.view.XListView;
import net.zhomi.negotiation.view.XListView.IXListViewListener;
import net.zhomi.nogotiation.adapter.TaskTipListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @ClassName: HomeFragment
 * @Description: 洽谈帮首页
 * @author yangshouzhi
 * @date 2015-7-15 下午4:08:39
 */
public class HomeFragment extends Fragment implements OnGestureListener,
		OnClickListener {
	private ViewFlipper flipper1 = null;
	// private ViewFlipper flipper2 = null;
	private static String TAG = "ZzL";
	private GridView weekGridView = null;
	private GridView monthGridView = null;
	private GestureDetector gestureDetector = null;
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private int week_c = 0;
	private int week_num = 0;
	private String currentDate = "";
	private static int jumpWeek = 0;
	private static int jumpMonth = 0;
	private static int jumpYear = 0;
	private DateAdapter dateAdapter;
	private CalendarAdapter monthAdapter;
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int weeksOfMonth = 0;
	private SpecialCalendar sc = null;
	private boolean isLeapyear = false; // 是否为闰年
	private int selectPostionWeek = 0;
	private int selectPostionMonth = 0;
	private String dayNumbers[] = new String[7];
	private String monNumbers[] = new String[35];

	private TextView tvDate;
	private TextView tvWeek;
	private int currentYear;
	private int currentMonth;
	private int currentWeek;
	private int currentDay;
	private int currentNum;
	private ToggleButton toggleButton;

	// 任务提醒
	private XListView taskTipListView;
	private TaskTipListAdapter taskTipAdapter;
	private List<TaskTipBean> tipList;
	// 有任务的天的数组
	private String[] noticeDayArr = new String[1];

	private StringBuffer dateBuffer;

	private TextView noTaskTipTextView;
	private boolean isStopRefresh = false;// 是否停止刷新
	private boolean isStopLoadMore = false;// 是否停止加载更多
	private int mPage = 1;// 页码
	private int mPerPage = 2;// 每页显示的条数
	public static final String HOMENAME="home_name";
	public static final String HOMEID="home_id";

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_home, null);
		initView(v);
		return v;
	}

	private void initView(View v) {
		tvDate = (TextView) v.findViewById(R.id.home_current_time);
		tvDate.setText(year_c + "年" + month_c + "月" + day_c + "日");
		tvWeek = (TextView) v.findViewById(R.id.home_current_week);
		StringBuffer buffer = new StringBuffer();
		buffer.append(currentYear).append("-").append(currentMonth).append("-")
				.append(currentDay);
		tvWeek.setText("第" + getWeek(buffer.toString()) + "周");
		toggleButton = (ToggleButton) v.findViewById(R.id.week_month_switch);
		toggleButton.setOnClickListener(this);
		weekGridView = (GridView) v.findViewById(R.id.week_gridview);
		monthGridView = (GridView) v.findViewById(R.id.month_gridview);
		// flipper1 = (ViewFlipper) v.findViewById(R.id.flipper1);
		gestureDetector = new GestureDetector(this);
		dateAdapter = new DateAdapter(this.getActivity(), getResources(),
				currentYear, currentMonth, currentWeek, currentNum,
				selectPostionWeek, currentWeek == 1 ? true : false,
				noticeDayArr);
		monthAdapter = new CalendarAdapter(this.getActivity(), getResources(),
				jumpMonth, jumpYear, year_c, month_c, day_c, noticeDayArr);
		addGridViewWeek();
		addGridViewMonth();
		dayNumbers = dateAdapter.getDayNumbers();
		monNumbers = monthAdapter.getDayNumbers();
		weekGridView.setAdapter(dateAdapter);
		selectPostionWeek = dateAdapter.getTodayPosition();
		weekGridView.setSelection(selectPostionWeek);

		selectPostionMonth = monthAdapter.getTodayPosition();
		monthGridView.setAdapter(monthAdapter);
		monthGridView.setSelection(selectPostionMonth);
		// flipper1.addView(gridView, 0);
		// 主页--任务提醒
		taskTipListView = (XListView) v
				.findViewById(R.id.home_task_tip_listview);
		noTaskTipTextView = (TextView) v
				.findViewById(R.id.no_task_tip_textview);
		tipList = new ArrayList<TaskTipBean>();
		taskTipAdapter = new TaskTipListAdapter(tipList, getActivity());
		taskTipListView.setAdapter(taskTipAdapter);
		taskTipListView.setPullLoadEnable(true);
		taskTipListView.setPullRefreshEnable(true);
		taskTipListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// 下拉刷新
				isStopRefresh = true;
				mPage = 1;
				new GetHomeTaskTipTask().execute(dateBuffer.toString(),
						String.valueOf(mPage), String.valueOf(mPerPage));

			}

			@Override
			public void onLoadMore() {
				// 加载更多
				isStopLoadMore = true;
				mPage++;
				new GetHomeTaskTipTask().execute(dateBuffer.toString(),
						String.valueOf(mPage), String.valueOf(mPerPage));
			}
		});
		taskTipListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TaskTipBean bean=tipList.get(position-1);
				Intent intent = new Intent(HomeFragment.this.getActivity(),
						CustomerDetailsActivity.class);
				intent.putExtra(HOMENAME, bean.getName());
				intent.putExtra(HOMEID, bean.getId());
				Log.e("ysz","name="+ bean.getName());
				Log.e("ysz","id="+ bean.getId());
				HomeFragment.this.getActivity().startActivity(intent);
			}
		});
		dateBuffer = new StringBuffer();
		dateBuffer.append(currentYear).append("-");
		if (currentMonth < 10) {
			dateBuffer.append("0").append(currentMonth).append("-");
		} else {
			dateBuffer.append(currentMonth).append("-");
		}
		if (currentDay < 10) {

			dateBuffer.append("0").append(currentDay);
		} else {
			dateBuffer.append(currentDay);
		}
		new GetHomeTaskTipTask().execute(dateBuffer.toString(),
				String.valueOf(mPage), String.valueOf(mPerPage));
		// 商业一刻
		v.findViewById(R.id.home_ad_rl).setOnClickListener(this);

		v.findViewById(R.id.home_add_negotiation_record).setOnClickListener(
				this);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date);
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		currentYear = year_c;
		currentMonth = month_c;
		currentDay = day_c;
		sc = new SpecialCalendar();
		getCalendar(year_c, month_c);
		week_num = getWeeksOfMonth();
		currentNum = week_num;
		if (dayOfWeek == 7) {
			week_c = day_c / 7 + 1;
		} else {
			if (day_c <= (7 - dayOfWeek)) {
				week_c = 1;
			} else {
				if ((day_c - (7 - dayOfWeek)) % 7 == 0) {
					week_c = (day_c - (7 - dayOfWeek)) / 7 + 1;
				} else {
					week_c = (day_c - (7 - dayOfWeek)) / 7 + 2;
				}
			}
		}
		currentWeek = week_c;
		getCurrent();
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

	@Override
	public void onPause() {
		super.onPause();
		jumpWeek = 0;
	}

	/**
	 * 判断某年某月所有的星期数
	 * 
	 * @param year
	 * @param month
	 */
	public int getWeeksOfMonth(int year, int month) {
		// 先判断某月的第一天为星期几
		int preMonthRelax = 0;
		int dayFirst = getWhichDayOfWeek(year, month);
		int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
		if (dayFirst != 7) {
			preMonthRelax = dayFirst;
		}
		if ((days + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (days + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (days + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;

	}

	/**
	 * 判断某年某月的第一天为星期几
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public int getWhichDayOfWeek(int year, int month) {
		return sc.getWeekdayOfMonth(year, month);

	}

	/**
	 * 
	 * @param year
	 * @param month
	 */
	public int getLastDayOfWeek(int year, int month) {
		return sc.getWeekDayOfLastMonth(year, month,
				sc.getDaysOfMonth(isLeapyear, month));
	}

	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
	}

	public int getWeeksOfMonth() {
		// getCalendar(year, month);
		int preMonthRelax = 0;
		if (dayOfWeek != 7) {
			preMonthRelax = dayOfWeek;
		}
		if ((daysOfMonth + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;
	}

	private void addGridViewWeek() {
		// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// weekGridView = new GridView(this.getActivity());
		weekGridView.setNumColumns(7);
		weekGridView.setGravity(Gravity.CENTER_VERTICAL);
		weekGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		weekGridView.setVerticalSpacing(1);
		weekGridView.setHorizontalSpacing(1);
		weekGridView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return HomeFragment.this.gestureDetector.onTouchEvent(event);
			}
		});

		weekGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "day:" + dayNumbers[position]);
				selectPostionWeek = position;
				dateAdapter.setSeclection(position);
				dateAdapter.notifyDataSetChanged();
				tvDate.setText(dateAdapter.getCurrentYear(selectPostionWeek)
						+ "年" + dateAdapter.getCurrentMonth(selectPostionWeek)
						+ "月" + dayNumbers[position] + "日");
				StringBuffer buffer = new StringBuffer();
				buffer.append(dateAdapter.getCurrentYear(selectPostionWeek))
						.append("-")
						.append(dateAdapter.getCurrentMonth(selectPostionWeek))
						.append("-").append(dayNumbers[position]);
				tvWeek.setText("第" + getWeek(buffer.toString()) + "周");
				dateBuffer = new StringBuffer();
				dateBuffer
						.append(dateAdapter.getCurrentYear(selectPostionWeek))
						.append("-");
				if (dateAdapter.getCurrentMonth(selectPostionWeek) < 10) {
					dateBuffer
							.append("0")
							.append(dateAdapter
									.getCurrentMonth(selectPostionWeek))
							.append("-");
				} else {
					dateBuffer.append(
							dateAdapter.getCurrentMonth(selectPostionWeek))
							.append("-");
				}
				if (dayNumbers[position].length() < 2) {

					dateBuffer.append("0").append(dayNumbers[position]);
				} else {
					dateBuffer.append(dayNumbers[position]);
				}
				tipList.clear();
				mPage = 1;
				new GetHomeTaskTipTask().execute(dateBuffer.toString(),
						String.valueOf(mPage), String.valueOf(mPerPage));

			}
		});
		// weekGridView.setLayoutParams(params);
	}

	private void addGridViewMonth() {
		// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// weekGridView = new GridView(this.getActivity());
		monthGridView.setNumColumns(7);
		monthGridView.setGravity(Gravity.CENTER_VERTICAL);
		monthGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		monthGridView.setVerticalSpacing(1);
		monthGridView.setHorizontalSpacing(1);
		monthGridView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return HomeFragment.this.gestureDetector.onTouchEvent(event);
			}
		});

		monthGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "day:" + monNumbers[position]);
				selectPostionMonth = position;
				monthAdapter.setSeclection(position);
				monthAdapter.notifyDataSetChanged();
				tvDate.setText(monthAdapter.getCurrentYear(selectPostionMonth)
						+ "年"
						+ monthAdapter.getCurrentMonth(selectPostionMonth)
						+ "月" + monNumbers[position].split("\\.")[0] + "日");
				StringBuffer buffer = new StringBuffer();
				buffer.append(monthAdapter.getCurrentYear(selectPostionMonth))
						.append("-")
						.append(monthAdapter
								.getCurrentMonth(selectPostionMonth))
						.append("-").append(monNumbers[position]);
				tvWeek.setText("第" + getWeek(buffer.toString()) + "周");
				dateBuffer = new StringBuffer();
				dateBuffer.append(
						monthAdapter.getCurrentYear(selectPostionMonth))
						.append("-");
				if (monthAdapter.getCurrentMonth(selectPostionMonth) < 10) {
					dateBuffer
							.append("0")
							.append(monthAdapter
									.getCurrentMonth(selectPostionMonth))
							.append("-");
				} else {
					dateBuffer.append(
							monthAdapter.getCurrentMonth(selectPostionMonth))
							.append("-");
				}
				if (monNumbers[position].length() < 2) {

					dateBuffer.append("0").append(monNumbers[position]);
				} else {
					dateBuffer.append(monNumbers[position]);
				}
				tipList.clear();
				mPage = 1;
				new GetHomeTaskTipTask().execute(dateBuffer.toString(),
						String.valueOf(mPage), String.valueOf(mPerPage));
			}
		});
	}

	/**
	 * 重新计算当前的年月
	 */
	public void getCurrent() {
		if (currentWeek > currentNum) {
			if (currentMonth + 1 <= 12) {
				currentMonth++;
			} else {
				currentMonth = 1;
				currentYear++;
			}
			currentWeek = 1;
			currentNum = getWeeksOfMonth(currentYear, currentMonth);
		} else if (currentWeek == currentNum) {
			if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
			} else {
				if (currentMonth + 1 <= 12) {
					currentMonth++;
				} else {
					currentMonth = 1;
					currentYear++;
				}
				currentWeek = 1;
				currentNum = getWeeksOfMonth(currentYear, currentMonth);
			}

		} else if (currentWeek < 1) {
			if (currentMonth - 1 >= 1) {
				currentMonth--;
			} else {
				currentMonth = 12;
				currentYear--;
			}
			currentNum = getWeeksOfMonth(currentYear, currentMonth);
			currentWeek = currentNum - 1;
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.week_month_switch:
			if (toggleButton.isChecked()) {
				if (weekGridView.getVisibility() == View.VISIBLE) {
					tvDate.setText(monthAdapter
							.getCurrentYear(selectPostionMonth)
							+ "年"
							+ monthAdapter.getCurrentMonth(selectPostionMonth)
							+ "月"
							+ monNumbers[selectPostionMonth].split("\\.")[0]
							+ "日");
					StringBuffer buffer = new StringBuffer();
					buffer.append(
							monthAdapter.getCurrentYear(selectPostionMonth))
							.append("-")
							.append(monthAdapter
									.getCurrentMonth(selectPostionMonth))
							.append("-").append(monNumbers[selectPostionMonth]);
					tvWeek.setText("第" + getWeek(buffer.toString()) + "周");
					weekGridView.setVisibility(View.GONE);
					monthGridView.setVisibility(View.VISIBLE);
					monthGridView.setAdapter(monthAdapter);
					dateBuffer = new StringBuffer();
					dateBuffer.append(
							monthAdapter.getCurrentYear(selectPostionMonth))
							.append("-");
					if (monthAdapter.getCurrentMonth(selectPostionMonth) < 10) {
						dateBuffer
								.append("0")
								.append(monthAdapter
										.getCurrentMonth(selectPostionMonth))
								.append("-");
					} else {
						dateBuffer.append(
								monthAdapter
										.getCurrentMonth(selectPostionMonth))
								.append("-");
					}
					if (monNumbers[selectPostionMonth].length() < 2) {

						dateBuffer.append("0").append(
								monNumbers[selectPostionMonth]);
					} else {
						dateBuffer.append(monNumbers[selectPostionMonth]);
					}
					tipList.clear();
					mPage = 1;
					new GetHomeTaskTipTask().execute(dateBuffer.toString(),
							String.valueOf(mPage), String.valueOf(mPerPage));
				}
			} else {
				if (monthGridView.getVisibility() == View.VISIBLE) {
					tvDate.setText(dateAdapter
							.getCurrentYear(selectPostionWeek)
							+ "年"
							+ dateAdapter.getCurrentMonth(selectPostionWeek)
							+ "月" + dayNumbers[selectPostionWeek] + "日");
					StringBuffer buffer = new StringBuffer();
					buffer.append(dateAdapter.getCurrentYear(selectPostionWeek))
							.append("-")
							.append(dateAdapter
									.getCurrentMonth(selectPostionWeek))
							.append("-").append(dayNumbers[selectPostionWeek]);
					tvWeek.setText("第" + getWeek(buffer.toString()) + "周");
					monthGridView.setVisibility(View.GONE);
					weekGridView.setVisibility(View.VISIBLE);
					weekGridView.setAdapter(dateAdapter);
					dateBuffer = new StringBuffer();
					dateBuffer.append(
							dateAdapter.getCurrentYear(selectPostionWeek))
							.append("-");
					if (dateAdapter.getCurrentMonth(selectPostionWeek) < 10) {
						dateBuffer
								.append("0")
								.append(dateAdapter
										.getCurrentMonth(selectPostionWeek))
								.append("-");
					} else {
						dateBuffer.append(
								dateAdapter.getCurrentMonth(selectPostionWeek))
								.append("-");
					}
					if (dayNumbers[selectPostionWeek].length() < 2) {

						dateBuffer.append("0").append(
								dayNumbers[selectPostionWeek]);
					} else {
						dateBuffer.append(dayNumbers[selectPostionWeek]);
					}
					tipList.clear();
					mPage = 1;
					new GetHomeTaskTipTask().execute(dateBuffer.toString(),
							String.valueOf(mPage), String.valueOf(mPerPage));
				}
			}
			break;
		case R.id.home_ad_rl:
			startActivity(new Intent(getActivity(),
					BusinessMomentActivity.class));
			break;
		case R.id.home_add_negotiation_record:
			startActivity(new Intent(getActivity(),
					AddNegotiationRecordActivity.class));
			break;

		default:
			break;
		}

	}

	/**
	 * 根据日期字符串判断当月第几周
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static int getWeek(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// 第几周
			int week = calendar.get(Calendar.WEEK_OF_MONTH);
			// 第几天，从周日开始
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			return week;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private class GetHomeTaskTipTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String date = params[0];
			String page = params[1];
			String perPage = params[2];
			return HttpData.getHomeTaskList("other", "home", "1.0", "2",
					App.getInstance().userInfo.getName(),
					App.getInstance().userInfo.getMd5(), date, page, perPage);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONObject json = JSONUtils.getJSONObject(jsonObject,
							"data");
					JSONArray noticeArr = JSONUtils.getJSONArray(json,
							"notice_list");
					if (noticeArr.length() > 0) {
						noticeDayArr = null;
						noticeDayArr = new String[noticeArr.length()];
						for (int i = 0; i < noticeArr.length(); i++) {
							noticeDayArr[i] = noticeArr.getString(i);
						}
						dateAdapter.refreshUi(noticeDayArr);
						monthAdapter.refreshUi(noticeDayArr);
					}
					JSONArray taskArr = JSONUtils.getJSONArray(json,
							"task_list");
					if (taskArr.length() > 0) {
						tipList = getData(tipList, taskArr, isStopLoadMore);
						// if (taskArr.length() < mPerPage) {
						// Log.e("ysz", "taskArr.length()=" + taskArr.length());
						// Toast.makeText(HomeFragment.this.getActivity(),
						// "数据已全部加载", Toast.LENGTH_SHORT).show();
						// taskTipListView.setPullLoadEnable(false);
						// } else {
						// Log.e("ysz", "taskArr.length()=" + taskArr.length());
						// taskTipListView.setPullLoadEnable(true);
						// }
					}
					Log.e("ysz", "tipList.size()=" + tipList.size());
					if (isStopRefresh) {
						taskTipListView.stopRefresh();
						isStopRefresh = false;
					}
					if (isStopLoadMore) {
						taskTipListView.stopLoadMore();
						isStopLoadMore = false;
					}
					if (tipList.size() > 0) {
						if (noTaskTipTextView.getVisibility() == View.VISIBLE) {
							noTaskTipTextView.setVisibility(View.GONE);
							taskTipListView.setVisibility(View.VISIBLE);
							taskTipAdapter.refreshUi(tipList);
						} else {
							taskTipListView.setVisibility(View.VISIBLE);
							taskTipAdapter.refreshUi(tipList);
						}
					} else {
						if (taskTipListView.getVisibility() == View.VISIBLE) {
							taskTipListView.setVisibility(View.GONE);
							noTaskTipTextView.setVisibility(View.VISIBLE);
						} else {
							noTaskTipTextView.setVisibility(View.VISIBLE);
						}
					}

				} else {
					String erroeMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					Toast.makeText(HomeFragment.this.getActivity(), erroeMsg,
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private List<TaskTipBean> getData(List<TaskTipBean> mList,
			JSONArray resultArr, boolean isLoadMore) {
		List<TaskTipBean> list = new ArrayList<TaskTipBean>();
		for (int i = 0; i < resultArr.length(); i++) {
			TaskTipBean bean = new TaskTipBean();
			try {
				bean.setName(JSONUtils.getString(resultArr.getJSONObject(i),
						"name", ""));
				bean.setNote(JSONUtils.getString(resultArr.getJSONObject(i),
						"note", ""));
				bean.setDate(JSONUtils.getString(resultArr.getJSONObject(i),
						"date", ""));
				bean.setId(JSONUtils.getString(resultArr.getJSONObject(i),
						"id", ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(bean);
		}
		if (isLoadMore) {
			mList.addAll(list);
		} else {
			mList = list;
		}

		return mList;

	}

}
