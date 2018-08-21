package com.mh.core.tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;

import com.mh.core.res.MHResConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class MHResourceUtil {
	/**
	 * 获取资源
	 *
	 * @param context 上下文
	 * @param packageName 包名
	 * @param resourcesName 资源名
	 * @return
	 */
	public static int getResourcesIdByName(Context context, String packageName, String resourcesName) {
		MHResConfiguration.mContext = context.getApplicationContext();
		Resources resources = context.getResources();
		int id = resources.getIdentifier(resourcesName, packageName, context.getPackageName());
		if (id == 0) {
			MHLogUtil.logE("MHResourceUtil", "资源文件读取不到！resourcesName:" + resourcesName);
		}
		return id;
	}

	/**
	 * 获取布局ID
	 *
	 * @param context 上下文
	 * @param resourcesName 布局文件名
	 * @return 布局ID
	 */
	public static int findLayoutIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "layout", resourcesName);
	}

	/**
	 * 获取attr属性
	 * @param context 上下文
	 * @param attrName attr名称
	 * @return attr属性
	 */
	public static int findAttrIdByName(Context context, String attrName){
		if(MHStringUtil.isEmpty(attrName)){
			return 0;
		}
		return getResourcesIdByName(context, "attr", attrName);
	}


	/**
	 * 获取attr在declare-styleable 数组中的下标
	 * @param cxt 上下文
	 * @param declareStyleableName 自定义style名称
	 * @param attrName 属性名
	 * @return 如果找到对应的属性则返回非负数，否则返回-1
	 */
	public static int getAttrIndexInDeclareStyleable(Context cxt,String declareStyleableName,
													 String attrName){
		int defaultValue = -1;
		if(MHStringUtil.isEmpty(declareStyleableName) || MHStringUtil.isEmpty(attrName)){
			return defaultValue;
		}
		try {
			String replacedAttrName = attrName.replaceAll(":", "_");
			Object resIndex = getResourceId(cxt.getPackageName(),
					declareStyleableName + "_" + replacedAttrName, "styleable");
			if(resIndex != null && resIndex instanceof Integer){
				return (int)resIndex;
			}
		}catch (Exception ex){
			MHLogUtil.logE("获取资源出错，resName=" + attrName, ex);
		}
		return defaultValue;
	}

	/**
	 * 获取自定义style属性id数组
	 * @param declareStyleableName 声明的自定义style名称
	 * @return 自定义style属性id数组
	 */
	public static int[] getDeclareStyleableAttrList(Context context, String declareStyleableName){
		if(MHStringUtil.isEmpty(declareStyleableName)){
			return null;
		}
		try {
			Object resIds = getResourceId(context.getPackageName(), declareStyleableName, "styleable");
			if(resIds != null && resIds instanceof int[]){
				return (int[]) resIds;
			}
		} catch (Exception ex){
			MHLogUtil.logE("获取资源出错，declareStyleableName=" + declareStyleableName, ex);
		}

		return null;
	}

	/**
	 * 获取dimen资源ID
	 * @param cxt 上下文
	 * @param resourceName 资源名称
	 * @return dimen资源ID
	 */
	public static int findDimenIdByName(Context cxt, String resourceName){
		return getResourcesIdByName(cxt, "dimen", resourceName);
	}

	/**
	 * 获取 color ID
	 *
	 * @param context 上下文
	 * @param resourcesName color名称
	 * @return color ID
	 */
	public static int findColorIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "color", resourcesName);
	}
	/**
	 * 获取 array ID
	 *
	 * @param context 上下文
	 * @param resourcesName array名称
	 * @return array ID
	 */
	public static int findArrayIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "array", resourcesName);
	}
	/**
	 * 获取String资源ID
	 *
	 * @param context 上下文
	 * @param resourcesName string 名称
	 * @return string资源ID
	 */
	public static int findStringIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "string", resourcesName);
	}

	public static String findStringByName(Context context, String resourcesName) {
		String res = "";
		try {
			res = context.getResources().getString(getResourcesIdByName(context, "string", resourcesName));
		} catch (Exception e) {
			MHLogUtil.logE("mh", "resourcesName:" + resourcesName);
		}
		return res;
	}

	/**
	 * 获取view id资源
	 *
	 * @param context 上下文
	 * @param resourcesName view 名称
	 * @return view ID
	 */
	public static int findViewIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "id", resourcesName);
	}

	/**
	 * 获取drawable资源
	 *
	 * @param context 上下文
	 * @param resourcesName drawable名称
	 * @return drawable ID
	 */
	public static int findDrawableIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "drawable", resourcesName);
	}

	/**
	 * 获取drawable资源
	 *
	 * @param context 上下文
	 * @param resourcesName drawable 名称
	 * @return drawable ID
	 */
	public static int findAnimIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "anim", resourcesName);
	}

	public static int findStyleIdByName(Context context, String resourcesName) {
		return getResourcesIdByName(context, "style", resourcesName);
	}

	/**
	 * 读取Assets中的文本
	 *
	 * @param ctx 上下文
	 * @param filaName 文件名
	 * @return 数据
	 */
	public static String getStringFromAssets(Context ctx, String filaName, String format) {
		try {
			InputStream is = ctx.getAssets().open(filaName + "." + format);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			return (new String(buffer)).trim();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取Application中的数据
	 *
	 * @param context 上下文
	 * @param metadata
	 * @return
	 */
	public static JSONObject getApplicationMetaData(Context context, String... metadata) {
		ApplicationInfo appInfo = null;
		JSONObject dataObject = null;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (appInfo != null) {
				dataObject = new JSONObject();
				for (int i = 0; i < metadata.length; i++) {
					dataObject.put(metadata[i], appInfo.metaData.getString(metadata[i]));
				}
			}
		} catch (NameNotFoundException | JSONException e) {
			e.printStackTrace();
		}
		return dataObject;
	}

	/**
	 * 获取Application中的数据
	 *
	 * @param context 上下文
	 * @param metadata meta data key 值
	 * @return application中的数据
	 */
	public static String getApplicationMetaData(Context context, String metadata) {
		ApplicationInfo appInfo = null;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (appInfo != null) {
			return appInfo.metaData.getString(metadata);
		}
		return null;
	}

	/**
	 * 获取Activity中的数据
	 *
	 * @param activity  上下文
	 * @param metadata  meta data key 值
	 * @return activity中的数据
	 */
	public static String getActivityMetaData(Activity activity, String metadata) {
		ActivityInfo info = null;
		try {
			info = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info != null) {
			return info.metaData.getString(metadata);
		}
		return null;
	}

	/**
	 * 获取Activity中的数据
	 *
	 * @param context 上下文
	 * @param metadata meta data key 值
	 * @return activity中的数据
	 */
	public static String getActivityMetaData(Context context, String metadata, Class<?> clazz) {
		ActivityInfo info = null;
		try {
			info = context.getPackageManager().getActivityInfo(new ComponentName(context, clazz), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info != null) {
			return info.metaData.getString(metadata);
		}
		return null;
	}

	/**
	 * 获取Activity中的数据
	 *
	 * @param activity 上下文
	 * @param metadata meta data key值
	 * @return activity 中的数据
	 */
	public static JSONObject getActivityMetaData(Activity activity, String... metadata) {
		ActivityInfo info = null;
		JSONObject dataObject = null;
		try {
			info = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
			if (info != null) {
				dataObject = new JSONObject();
				for (String aMetadata : metadata) {
					dataObject.put(aMetadata, info.metaData.getString(aMetadata));
				}
			}
		} catch (NameNotFoundException | JSONException e) {
			e.printStackTrace();
		}
		return dataObject;
	}

	/**
	 * 获取Service中的数据
	 *
	 * @param context 上下文
	 * @param metadata meta data key 值
	 * @param serviceClass service class
	 * @return service中的数据
	 */
	public static String getServiceMetaData(Context context, String metadata, Class<?> serviceClass) {
		ComponentName cn = new ComponentName(context, serviceClass);
		ServiceInfo info = null;
		try {
			info = context.getPackageManager().getServiceInfo(cn, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info != null) {
			return info.metaData.getString(metadata);
		}
		return null;
	}

	/**
	 * 获取Application中的数据
	 *
	 * @param context 上下文
	 * @param metadata meta data key值
	 * @return application data
	 */
	public static String getReceiverMetaData(Context context, String metadata, Class<?> serviceClass) {
		ComponentName cn = new ComponentName(context, serviceClass);
		ActivityInfo info = null;
		try {
			info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info != null) {
			return info.metaData.getString(metadata);
		}
		return null;
	}

	/**
	 * 对于 context.getResources().getIdentifier 无法获取的数据 , 或者数组
	 * 资源反射值
	 * @param packageName 包名
	 * @param name 资源名称
	 * @param type 资源类型
	 * @return 数据结果
	 */
	private static Object getResourceId(String packageName, String name, String type) {
		String className = packageName + ".R";
		try {
			Class<?> cls = Class.forName(className);
			for (Class<?> childClass : cls.getClasses()) {
				String simple = childClass.getSimpleName();
				if (simple.equals(type)) {
					for (Field field : childClass.getFields()) {
						String fieldName = field.getName();
						if (fieldName.equals(name)) {
							return field.get(null);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
