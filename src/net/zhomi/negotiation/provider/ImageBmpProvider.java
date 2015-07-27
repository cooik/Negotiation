package net.zhomi.negotiation.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.event.AndroidEventManager;
import net.zhomi.negotiation.event.DownloadEvent;
import net.zhomi.negotiation.event.Event;
import net.zhomi.negotiation.event.EventCode;
import net.zhomi.negotiation.event.EventManager.OnEventListener;
import net.zhomi.negotiation.utils.FilePaths;
import net.zhomi.negotiation.utils.ImageUtils;
import net.zhomi.negotiation.utils.SystemUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

public abstract class ImageBmpProvider implements OnEventListener {
	

	protected HashMap<String, Bitmap> mMapImageUrlToImageBmp = new HashMap<String, Bitmap>();
	
	public HashMap<String, Bitmap> getmMapImageUrlToImageBmp() {
		return mMapImageUrlToImageBmp;
	}

	private boolean	mIsDownloading;
	private List<String> mListUrlWaitDownload = new ArrayList<String>();
	private List<String> mListUrlDownloading  = new ArrayList<String>();
	
	protected Bitmap	mBmpDefault;
	
	public Bitmap loadImage(String imagerUrl){
		if(TextUtils.isEmpty(imagerUrl)) {
			return null;
		}
		//通过缓存获得bmp
		Bitmap bmp = mMapImageUrlToImageBmp.get(imagerUrl);
		if(bmp == null){
			//获得文件的名称
			String strFileName = SystemUtils.getFileNameFronUrl(imagerUrl);
			if(strFileName == null) {
				return null;
			}
			SystemUtils.print("path:" + FilePaths.getPosterSavePath(strFileName));
			//根据地址获得本地bmp
			bmp = ImageUtils.compressImage(FilePaths.getPosterSavePath(strFileName), ImageUtils.EXPECT_SIDE_640);
			if(bmp == null){
				bmp = mBmpDefault;
				//请求网络下载
				requestDownloadImage(imagerUrl, strFileName);
			}
			if(bmp != null){
				//把下载的文件加到缓存中
				addAvatarToCache(imagerUrl, bmp);
			}
		}
		return bmp;
	}

	protected void addAvatarToCache(String strImagerUrl,Bitmap bmp){
	}
	
	protected void removeCache(String strImagerUrl){
	}
	
	private void requestDownloadImage(String imageUrl, String fileName){
		if(!TextUtils.isEmpty(imageUrl)){
			if(mListUrlDownloading.contains(imageUrl) || mListUrlWaitDownload.contains(imageUrl)){
				return;
			}
			
			if(!mIsDownloading){
				Log.i("TAG", "开始下载:" + imageUrl);
				mIsDownloading = true;
				DownloadEvent de = new DownloadEvent(EventCode.SC_DownloadImage, null, null);
				de.setTag(imageUrl);
				AndroidEventManager.getInstance().postEvent(de, 0, imageUrl,
						FilePaths.getPosterSavePath(fileName));
				mListUrlDownloading.add(imageUrl);
			}else {
				mListUrlWaitDownload.add(imageUrl);
			}
		}
	}

	@Override
	public void onEventRunEnd(Event event) {
		final int nCode = event.getEventCode();
		if(nCode == EventCode.SC_DownloadImage){
			Log.i("TAG", "image-bmp-provider");
			DownloadEvent dEvent = (DownloadEvent)event;
			if(dEvent.isSuccess()){
				final String strImageUrl = (String)dEvent.getTag();
				removeCache(strImageUrl);
				mListUrlDownloading.remove(strImageUrl);
			}
			mIsDownloading = false;
			if(mListUrlWaitDownload.size() > 0){
				for(String strImageUrl : mListUrlWaitDownload){
					mListUrlWaitDownload.remove(strImageUrl);
					requestDownloadImage(strImageUrl, SystemUtils.getFileNameFronUrl(strImageUrl));
					return;
				}
			}
		}
	}

	/**
	 * 加载图片
	 * 并做圆角处理
	 * @param imagerUrl
	 * @param resId 默认图片id
	 * @return
	 */
	public Bitmap loadImageForCorner(String imagerUrl, int resId){
		Bitmap mBitmap = loadImage(imagerUrl);
		if(mBitmap == null) {
			mBitmap = BitmapFactory.decodeResource(App.instance.getResources(), resId);
		}
		mBitmap = ImageUtils.getRoundedCornerBitmap(mBitmap);
		return mBitmap;
	}
	/**
	 * 加载图片, 默认图片为R.drawable.avatar_0。
	 * @param imagerUrl
	 * @return
	 */
	public Bitmap loadImageForCorner(String imagerUrl){
		return loadImageForCorner(imagerUrl, R.drawable.user_center_avator_default);
	}
	
	/**
	 * 加载图片， 默认图片为R.drawable.weibo_touxiang
	 * @param imagerUrl
	 * @return
	 */
	public Bitmap loadImageForRound(String imagerUrl) {
		return loadImage(imagerUrl, R.drawable.user_center_avator_default);
	}
	
	/**
	 * 加载图片
	 * @param imagerUrl 图片地址
	 * @param resId 默认图片id
	 * @return
	 */
	public Bitmap loadImage(String imagerUrl, int resId) {
		Bitmap mBitmap = loadImage(imagerUrl);
		if(mBitmap == null) {
			mBitmap = BitmapFactory.decodeResource(App.instance.getResources(), resId);
		}
		return mBitmap;
	}
}
