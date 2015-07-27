package net.zhomi.negotiation.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.login.LoginActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.SystemUtils;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class LodingActivity extends BaseActivity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loding);
		if (!SystemUtils.isNetworkAvailable(this)) {
			showMsg("无可用的网络，请检查网络连接!");
			finish();
		}
		imageView = (ImageView) findViewById(R.id.loading_imge);
//		new GetLodingImgTask().execute();
		new DownloadImageTask().execute("http://www.baidu.com/img/bdlogo.png"); // 得到图片的输入流
	}


	private class DownloadImageTask extends AsyncTask<String, String, byte[]> {

		protected byte[] doInBackground(String... urls) {
			return getImage(urls[0]);
		}

		protected void onPostExecute(byte[] result) {
			// 二进制数据生成位图
			Bitmap bit = BitmapFactory
					.decodeByteArray(result, 0, result.length);
			imageView.setImageBitmap(bit);
			try {
				Thread thread=new Thread(){
					@Override
					public void run() {
						synchronized(this){
							try {
								wait(1000);
								//在这里做您想做的事情
								LodingActivity.this.startActivity(new Intent(LodingActivity.this, LoginActivity.class));
								finish();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}	
							}
					}
					
				};
				thread.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] getImage(String path) {
		URL url;
		byte[] b = null;
		try {
			url = new URL(path); // 设置URL
			HttpURLConnection con;

			con = (HttpURLConnection) url.openConnection(); // 打开连接

			con.setRequestMethod("GET"); // 设置请求方法
			// 设置连接超时时间为5s
			con.setConnectTimeout(5000);
			InputStream in = con.getInputStream(); // 取得字节输入流

			b = readInputStream(in);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b; // 返回byte数组
	}

	private byte[] readInputStream(InputStream in) {
		int len = 0;
		byte buf[] = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len); // 把数据写入内存
			}
			out.close();// 关闭内存输出流
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return out.toByteArray(); // 把内存输出流转换成byte数组
	}

	private class GetLodingImgTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			return HttpData.getLoadingImg("other", "welcome", "1.0", "2");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
