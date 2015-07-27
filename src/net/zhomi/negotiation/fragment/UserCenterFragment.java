/**  
* @Package net.zhomi.negotiation.fragment 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yangshouzhi
* @date 2015-7-15 下午4:20:37 
*/ 
package net.zhomi.negotiation.fragment;

import net.zhaomi.negotiation.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** 
 * @ClassName: UserCenterFragment 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author yangshouzhi
 * @date 2015-7-15 下午4:20:37 
 */
public class UserCenterFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_more, null);
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

}
