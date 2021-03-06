/**  
 * @Package net.zhomi.negotiation.view.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-15 下午10:58:34 
 */
package net.zhomi.negotiation.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @ClassName: HttpData
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangshouzhi
 * @date 2015-7-15 下午10:58:34
 */
public class HttpData {
	private static final int TIMEOUT_CONNECTION = 5000;
	private static final int TIMEOUT_SO = 10000;
	/** 测试url **/
	private static final String testUrl = "http://talk.chengge.net/interface.php";

	public static final String CONNECTION_ERROR_JSON = "{\"status\":0,\"error\":\"%s\"}";
	public static final String CONNECTION_ERROR_URL = "{\"status\":0,\"error\":\"请求地址无效!\"}";

	/**
	 * HttpPost请求
	 * 
	 * @param url
	 * @param jsonObject
	 * 
	 * @return
	 */
	private static String post(String url, List<NameValuePair> nameValuePairs) {
		SystemUtils.print("POST--URL:" + url);
		SystemUtils.print("entity--:" + nameValuePairs);

		String strResult = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							TIMEOUT_CONNECTION);
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, TIMEOUT_SO);
			HttpPost post = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					nameValuePairs, HTTP.UTF_8);
			post.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(post);
			strResult = EntityUtils.toString(httpResponse.getEntity());
		} catch (IllegalArgumentException e) {
			strResult = CONNECTION_ERROR_URL;
		} catch (Exception e) {
			Log.e("111", "result:" + strResult);
			strResult = String.format(CONNECTION_ERROR_JSON,
					e.getMessage() == null ? "" : e.getMessage());
		}
		SystemUtils.print("result:" + strResult);
		return strResult;
	}


	public static String getLoadingImg(String app, String act, String ver,
			String pt) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		return post(testUrl, nvps);
	}

	/**
	 * 
	 * TODO 登录
	 * 
	 * @param app
	 *            模块名称
	 * @param act
	 *            方法名称
	 * @param ver
	 *            版本号
	 * @param pt
	 *            平台类型：1：ios；2：android
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码原文
	 * @return
	 * @return: String
	 */
	public static String login(String app, String act, String ver, String pt,
			String username, String pwd) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair usernameParam = new BasicNameValuePair("username",
				username);
		BasicNameValuePair pwdParam = new BasicNameValuePair("pwd", pwd);

		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(usernameParam);
		nvps.add(pwdParam);
		return post(testUrl, nvps);
	}

	/**
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param mobile
	 *            用户手机号
	 * @param type
	 *            类型
	 * @param code
	 *            对手机号加密后的字符串
	 * @return
	 */
	public static String getCode(String app, String act, String ver, String pt,
			String mobile, String type, String code) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair mobileParam = new BasicNameValuePair("mobile",
				mobile);
		BasicNameValuePair typeParam = new BasicNameValuePair("type", type);
		BasicNameValuePair codeParam = new BasicNameValuePair("code", code);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(mobileParam);
		nvps.add(typeParam);
		nvps.add(codeParam);
		return post(testUrl, nvps);
	}

	/**
	 * 验证验证码
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param mobile
	 * @param code
	 * @return
	 */
	public static String validateode(String app, String act, String ver,
			String pt, String mobile, String code) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair mobileParam = new BasicNameValuePair("mobile",
				mobile);
		BasicNameValuePair codeParam = new BasicNameValuePair("code", code);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(mobileParam);
		nvps.add(codeParam);
		return post(testUrl, nvps);
	}

	/**
	 * 用户注册
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public static String register(String app, String act, String ver,
			String pt, String userName, String pwd) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair mobileParam = new BasicNameValuePair("username",
				userName);
		BasicNameValuePair codeParam = new BasicNameValuePair("pwd", pwd);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(mobileParam);
		nvps.add(codeParam);
		return post(testUrl, nvps);

	}

	/**
	 * 找回密码
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param type
	 * @param userName
	 * @param pwd1
	 * @param pwd2
	 * @return
	 */
	public static String reset_password(String app, String act, String ver,
			String pt, String type,String userName, String pwd1,String pwd2) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair typeParam=new BasicNameValuePair("type",type);
		BasicNameValuePair mobileParam = new BasicNameValuePair("username",
				userName);
		BasicNameValuePair pwd1Param = new BasicNameValuePair("pwd1", pwd1);
		BasicNameValuePair pwd2Param = new BasicNameValuePair("pwd2", pwd2);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(typeParam);
		nvps.add(mobileParam);
		nvps.add(pwd1Param);
		nvps.add(pwd2Param);
		return post(testUrl, nvps);

	}
	
	/**
	 * 获取一级业态
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param type
	 *            获取数据类型：1一级业态；2一级业态下的二级业态
	 * @return
	 */
	public static String getFirstCate(String app, String act, String ver,
			String pt, String type, String firstCateId) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair typeParam = new BasicNameValuePair("type", type);
		BasicNameValuePair firstCateIdParam = new BasicNameValuePair(
				"first_cate_id", firstCateId);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(typeParam);
		nvps.add(firstCateIdParam);
		return post(testUrl, nvps);
	}


	/**
	 * 获取首页任务提醒
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param username
	 * @param pwd
	 * @param date
	 *            指定日期（如2015-07-25，筛选某天具体任务时用到）
	 * @param page
	 *            当前页
	 * @param perpage
	 *            每页显示的记录数
	 * @return
	 */
	public static String getHomeTaskList(String app, String act, String ver,
			String pt, String username, String pwd, String date, String page,
			String perpage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair usernameParam = new BasicNameValuePair("username",
				username);
		BasicNameValuePair pwdParam = new BasicNameValuePair("pwd", pwd);
		BasicNameValuePair dateParam = new BasicNameValuePair("date", date);
		BasicNameValuePair pageParam = new BasicNameValuePair("page", page);
		BasicNameValuePair perpageParam = new BasicNameValuePair("perpage",
				perpage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(usernameParam);
		nvps.add(pwdParam);
		nvps.add(dateParam);
		nvps.add(pageParam);
		nvps.add(perpageParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取客户列表
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param username
	 * @param pwd
	 * @param pinyin
	 *            拼音首字母，非A-Z用#表示
	 * @param keyword
	 *            搜索关键字
	 * @param page
	 * @param perPage
	 * @return
	 */
	public static String getCustomeList(String app, String act, String ver,
			String pt, String username, String pwd, String pinyin,
			String keyword, String page, String perpage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair usernameParam = new BasicNameValuePair("username",
				username);
		BasicNameValuePair pwdParam = new BasicNameValuePair("pwd", pwd);
		BasicNameValuePair pinyinParam = new BasicNameValuePair("pinyin",
				pinyin);
		BasicNameValuePair keywordParam = new BasicNameValuePair("keyword",
				keyword);
		BasicNameValuePair pageParam = new BasicNameValuePair("page", page);
		BasicNameValuePair perpageParam = new BasicNameValuePair("perpage",
				perpage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(usernameParam);
		nvps.add(pwdParam);
		nvps.add(pinyinParam);
		nvps.add(keywordParam);
		nvps.add(pageParam);
		nvps.add(perpageParam);
		return post(testUrl, nvps);
	}



	/**
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param city_id
	 * @param name
	 * @param first_cate_id
	 *            一级业态编号
	 * @param second_cate_id
	 *            二级业态编号
	 * @param pinyin
	 *            品牌首字母（0为全部；如果首字母为0-9，则该参数就传0-9；）
	 * @param page
	 *            分页参数（默认1）
	 * @param perpage
	 *            每页显示参数记录条数（默认20条）
	 * @return
	 */
	public static String getBrandList(String app, String act, String ver,
			String pt, String city_id, String name, String first_cate_id,
			String second_cate_id, String pinyin, String page, String perpage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cityidParam = new BasicNameValuePair("city_id",
				city_id);
		BasicNameValuePair firstcateidParam = new BasicNameValuePair(
				"first_cate_id", first_cate_id);
		BasicNameValuePair secondcateidParam = new BasicNameValuePair(
				"second_cate_id", second_cate_id);
		BasicNameValuePair nameParam = new BasicNameValuePair("name", name);
		BasicNameValuePair pinyinParam = new BasicNameValuePair("pinyin",
				pinyin);

		BasicNameValuePair pageParam = new BasicNameValuePair("page", page);
		BasicNameValuePair perpageParam = new BasicNameValuePair("perpage",
				perpage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cityidParam);
		nvps.add(nameParam);
		nvps.add(pinyinParam);
		nvps.add(firstcateidParam);
		nvps.add(secondcateidParam);
		nvps.add(pageParam);
		nvps.add(perpageParam);
		return post(testUrl, nvps);

	}


	/**
	 * 获取客户详情
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param id
	 * 客户id
	 * @param username
	 * @param pwd
	 * @param page
	 * @param perpage
	 * @return
	 */
	public static String getCustomerDetail(String app, String act, String ver,
			String pt, String id, String username, String pwd, String page,
			String perpage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair usernameParam = new BasicNameValuePair("username",
				username);
		BasicNameValuePair pwdParam = new BasicNameValuePair("pwd", pwd);
		BasicNameValuePair idParam = new BasicNameValuePair("id", id);
		BasicNameValuePair pageParam = new BasicNameValuePair("page", page);
		BasicNameValuePair perpageParam = new BasicNameValuePair("perpage",
				perpage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(usernameParam);
		nvps.add(pwdParam);
		nvps.add(idParam);
		nvps.add(pageParam);
		nvps.add(perpageParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取商业一刻分类列表
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @return
	 */
	public static String getNewsCateList(String app, String act, String ver,
			String pt) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取商业一刻新闻列表
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param cateId
	 * @return
	 */
	public static String getNewsList(String app, String act, String ver,
			String pt, String cateId) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cateIdParam = new BasicNameValuePair("cate_id",
				cateId);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cateIdParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取商业一刻详情
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param id
	 *            商业一刻编号
	 * @return
	 */
	public static String getNewsDetail(String app, String act, String ver,
			String pt, String id) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair idParam = new BasicNameValuePair("id", id);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(idParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取地区接口
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param type
	 *            获取数据类型：1：省；2：市
	 * @param provinceId
	 *            省编号（当type=2时，该值必须）
	 * @return
	 */
	public static String getProvinceCityName(String app, String act,
			String ver, String pt, String type, String provinceId) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair typeParam = new BasicNameValuePair("type", type);
		BasicNameValuePair provinceIdParam = new BasicNameValuePair(
				"province_id", provinceId);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(typeParam);
		nvps.add(provinceIdParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取行政区
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param cityId
	 *            市级别地区编号
	 * @return
	 */
	public static String getDistrictName(String app, String act, String ver,
			String pt, String cityId) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cityIdParam = new BasicNameValuePair("city_id",
				cityId);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cityIdParam);
		return post(testUrl, nvps);

	}
    /**
     * 获取业态下品牌列表
     * @param app
     * @param act
     * @param ver
     * @param pt
     * @param cityId
     * @param name
     * @param firstCateId
     * @param secondCateId
     * @param pinYin
     * @param page
     * @param perPage
     * @return
     */
	public static String getBrandListOfCate(String app, String act, String ver,
			String pt, String cityId, String name, String firstCateId,
			String secondCateId, String pinYin, String page, String perPage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cityIdParam = new BasicNameValuePair("city_id",
				cityId);
		BasicNameValuePair nameParam=new BasicNameValuePair("name",name);
		BasicNameValuePair firstCateIdParam=new BasicNameValuePair("first_cate_id", firstCateId);
		BasicNameValuePair secondCateIdParam=new BasicNameValuePair("second_cate_id", secondCateId);
		BasicNameValuePair pinyinParam=new BasicNameValuePair("pinyin", pinYin);
		BasicNameValuePair pageParam=new BasicNameValuePair("page", page);
		BasicNameValuePair perPageParam=new BasicNameValuePair("perpage", perPage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cityIdParam);
		nvps.add(nameParam);
		nvps.add(firstCateIdParam);
		nvps.add(secondCateIdParam);
		nvps.add(pinyinParam);
		nvps.add(pageParam);
		nvps.add(perPageParam);
		return post(testUrl, nvps);
	}

	/**
	 * 获取品牌旗下商家接口
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param cityId
	 *            市级城市编号
	 * @param brandId
	 *            品牌编号
	 * @param lon
	 * @param lat
	 * @param page
	 * @param perPage
	 * @return
	 */
	public static String getBrandList(String app, String act, String ver,
			String pt, String cityId, String brandId, String lon, String lat,
			String page, String perPage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cityIdParam = new BasicNameValuePair("city_id",
				cityId);
		BasicNameValuePair brandIdParam = new BasicNameValuePair("brand_id",
				brandId);
		BasicNameValuePair lonParam = new BasicNameValuePair("lon", lon);
		BasicNameValuePair latParam = new BasicNameValuePair("lat", lat);
		BasicNameValuePair pageParam = new BasicNameValuePair("page", page);
		BasicNameValuePair perPageParam = new BasicNameValuePair("perpage",
				perPage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cityIdParam);
		nvps.add(brandIdParam);
		nvps.add(lonParam);
		nvps.add(latParam);
		nvps.add(pageParam);
		nvps.add(perPageParam);
		return post(testUrl, nvps);
	}

	/**
	 * 商家库列表
	 * 
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param cityId
	 * @param areaId
	 * @param firstCateId
	 * @param secondCateId
	 * @param pinyin
	 * @param lon
	 * @param lat
	 * @param page
	 * @param perPage
	 * @return
	 */
	public static String getBusinessList(String app, String act, String ver,
			String pt, String cityId, String areaId, String firstCateId,
			String secondCateId, String pinyin, String lon, String lat,
			String page, String perPage) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cityIdParam = new BasicNameValuePair("city_id",
				cityId);
		BasicNameValuePair areaIdParam = new BasicNameValuePair("brand_id",
				areaId);
		BasicNameValuePair firstCateIdParam = new BasicNameValuePair(
				"first_cate_id", firstCateId);
		BasicNameValuePair secondCateIdParam = new BasicNameValuePair(
				"second_cate_id", secondCateId);
		BasicNameValuePair pinyinParam = new BasicNameValuePair("pinyin",
				pinyin);
		BasicNameValuePair lonParam = new BasicNameValuePair("lon", lon);
		BasicNameValuePair latParam = new BasicNameValuePair("lat", lat);
		BasicNameValuePair pageParam = new BasicNameValuePair("page", page);
		BasicNameValuePair perPageParam = new BasicNameValuePair("perpage",
				perPage);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cityIdParam);
		nvps.add(areaIdParam);
		nvps.add(firstCateIdParam);
		nvps.add(secondCateIdParam);
		nvps.add(pinyinParam);
		nvps.add(lonParam);
		nvps.add(latParam);
		nvps.add(pageParam);
		nvps.add(perPageParam);
		return post(testUrl, nvps);
	}
	/**
	 * 获取品牌旗下商家列表
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param city_id 市级城市编号
	 * @param brand_id 品牌编号
	 * @param lon
	 * @param lat
	 * @param page
	 * @param perPage
	 * @return
	 */
	public static String getBusinessOfBrandList(String app,String act,String ver,String pt,String city_id,String brand_id,String lon,String lat,String page,String perPage){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair cityIdParam = new BasicNameValuePair("city_id",
				city_id);
		BasicNameValuePair brandIdParam=new BasicNameValuePair("brand_id", brand_id);
		BasicNameValuePair lonParam=new BasicNameValuePair("lon", lon);
		BasicNameValuePair latParam=new BasicNameValuePair("lat", lat);
		BasicNameValuePair pageParam=new BasicNameValuePair("page", page);
		BasicNameValuePair perPageParam=new BasicNameValuePair("perpage", perPage);
		
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(cityIdParam);
		nvps.add(brandIdParam);
		nvps.add(lonParam);
		nvps.add(latParam);
		nvps.add(pageParam);
		nvps.add(perPageParam);
		return post(testUrl, nvps);
	}
	/**
	 * 获取品牌联系人
	 * @param app
	 * @param act
	 * @param ver
	 * @param pt
	 * @param brand_id 品牌编号
	 * @return
	 */
	public static String getContactsListOfBrand(String app,String act,String ver,String pt,String brand_id){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		BasicNameValuePair appParam = new BasicNameValuePair("app", app);
		BasicNameValuePair actParam = new BasicNameValuePair("act", act);
		BasicNameValuePair verParam = new BasicNameValuePair("ver", ver);
		BasicNameValuePair ptParam = new BasicNameValuePair("pt", pt);
		BasicNameValuePair brandIdParam=new BasicNameValuePair("brand_id", brand_id);
		nvps.add(appParam);
		nvps.add(actParam);
		nvps.add(verParam);
		nvps.add(ptParam);
		nvps.add(brandIdParam);
		return post(testUrl, nvps);
	}
}
