package net.zhomi.negotiation.event;

import net.zhomi.negotiation.utils.HttpUtils;
import net.zhomi.negotiation.utils.HttpUtils.ProgressRunnable;
import android.os.Handler;


public class DownloadEvent extends Event {

	protected ProgressRunnable 	mRunnable;
	protected Handler 			mHandler;
	
	protected boolean			mIsSuccess;
	protected String			mFileSavePath;
	
	public DownloadEvent(int nEventCode,ProgressRunnable pr,Handler handler) {
		super(nEventCode);
		setIsAsyncRun(true);
		setIsNotifyAfterRun(true);
		
		mRunnable = pr;
		mHandler = handler;
	}
	
	public boolean 	isSuccess(){
		return mIsSuccess;
	}
	
	public String	getFileSavePath(){
		return mFileSavePath;
	}

	@Override
	public void run(Object... params) throws Exception {
		final String strUrl = (String)params[0];
		setTag(strUrl);
		final String strSavePath = (String)params[1];
		mFileSavePath = strSavePath;
//		Log.i(SystemUtils.TAG, "download-url:" + strUrl);
//		Log.i(SystemUtils.TAG, "download-path:" + strSavePath);
		
		mIsSuccess = HttpUtils.doDownload(strUrl, strSavePath, mRunnable, mHandler, null);
//		Log.i(SystemUtils.TAG, "download��" + mIsSuccess);
	}
	
}
