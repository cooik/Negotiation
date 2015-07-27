package net.zhomi.negotiation.activity;

import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.event.AndroidEventManager;
import net.zhomi.negotiation.event.DownloadEvent;
import net.zhomi.negotiation.event.Event;
import net.zhomi.negotiation.event.EventCode;
import net.zhomi.negotiation.event.EventManager.OnEventListener;
import net.zhomi.negotiation.provider.PosterBmpProvider;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.ImageUtils;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.utils.SystemUtils;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewsDetailActivity extends BaseActivity implements OnEventListener {
	private String id;
	TextView tv_title;
	TextView tv_date;
	TextView tv_cate;
	TextView tv_content;
	ImageView iv_img;
	String news_title;
	String news_date;
	String news_cate;
	String news_content;
	String img_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsdetail);
		AndroidEventManager.getInstance().addEventListener(
				EventCode.SC_DownloadImage, this, false);
		initView();
		Intent intent = getIntent();
		id = intent.getStringExtra(BusinessMomentActivity.NEWSID);
		new GetNewsDetailTask().execute(id);
	}

	private void initView() {
		initTitle();
		title.setText("商业一刻详情");
		back.setBackgroundResource(R.drawable.ic_launcher);
		back.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_title = (TextView) findViewById(R.id.news_title);
		tv_date = (TextView) findViewById(R.id.news_date);
		tv_cate = (TextView) findViewById(R.id.news_cate);
		iv_img = (ImageView) findViewById(R.id.news_img);
		tv_content = (TextView) findViewById(R.id.news_content);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private class GetNewsDetailTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String newsId = params[0];
			return HttpData.getNewsDetail("news", "detail", "1.0", "2", newsId);
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
					JSONObject dataJson = JSONUtils.getJSONObject(jsonObject,
							"data");
					news_title = JSONUtils.getString(dataJson, "title", "");
					news_date = JSONUtils.getString(dataJson, "date", "");
					news_cate = JSONUtils.getString(dataJson, "cate", "");
					img_url = JSONUtils.getString(dataJson, "img_url", "");
					news_content = JSONUtils.getString(dataJson, "content", "");
					setView();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		private void setView() {
			tv_title.setText(news_title);
			tv_cate.setText(news_cate);
			tv_date.setText(news_date);
			tv_content.setText(news_content);
			Bitmap bitmap = PosterBmpProvider.getInstance().loadImage(img_url);
			if (bitmap != null) {
				RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) iv_img
						.getLayoutParams(); // 取控件当前的布局参数
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				// 定义预转换成的图片的宽度和高度
				int newWidth = SystemUtils
						.getScreenWidth(NewsDetailActivity.this);
				int newHeight = SystemUtils
						.getScreenWidth(NewsDetailActivity.this) * 3 / 5;

				float scaleWidth = ((float) newWidth) / width;
				float scaleHeight = ((float) newHeight) / height;

				// 创建操作图片用的matrix对象
				Matrix matrix = new Matrix();
				// 缩放图片动作
				matrix.postScale(scaleWidth, scaleHeight);
				// 创建新的图片
				Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
						height, matrix, true);
				iv_img.setImageBitmap(resizedBitmap);
			}
		}

	}

	@Override
	public void onEventRunEnd(Event event) {
		int code = event.getEventCode();
		if (code == EventCode.SC_DownloadImage) {
			DownloadEvent dEvent = (DownloadEvent) event;
			String url = (String) dEvent.getTag();
			if (TextUtils.isEmpty(url)) {
				return;
			}
			Bitmap bitmap = null;
			if (dEvent.isSuccess()) {
				bitmap = PosterBmpProvider.getInstance().loadImage(url);
			} else {
				bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.business);
			}
			Bitmap aimBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.business);
			if (bitmap != null) {
				Bitmap newBitmap = ImageUtils.zoomBitmap(bitmap,
						aimBitmap.getWidth(), aimBitmap.getHeight());
				if (newBitmap != null && iv_img != null) {
					iv_img.setImageBitmap(newBitmap);
					return;
				}
			}
		}
	}

}
