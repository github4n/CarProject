package com.mh.core.task;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask.Status;

import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.ThreadUtil;

import java.util.HashMap;
import java.util.Map;

/**
* 处理Command请求
* */
public class MHCommandExecute {
	private static MHCommandExecute instance;
	private static Map<String, MHCommandAsyncTask> taskMap;
	
	public static synchronized MHCommandExecute getInstance() {
		if (instance == null) {
			instance = new MHCommandExecute();
			taskMap = new HashMap<String, MHCommandAsyncTask>();
		}
		return instance;
	}
	
	/**
	 * 执行操作
	 * */
	public void asynExecute(Context context, MHCommand command) {
		if (command != null) {
			MHCommandAsyncTask task = new MHCommandAsyncTask(context, command);
			//task.execute();
			excute(task);
			taskMap.put(command.getCommandId(), task);
		}
	}
	
	@Deprecated
	public void asynExecute(Activity activity, MHCommand command) {
		if (command != null) {
			MHCommandAsyncTask task = new MHCommandAsyncTask(activity, command);
			//task.execute();
			excute(task);
			taskMap.put(command.getCommandId(), task);
		}
	}
	
	
	
	//@SuppressLint("NewApi")
	private void excute(MHCommandAsyncTask asyncTask){
		
		/*try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				asyncTask.execute();
			}
		} catch (Exception e) {
			MHLogUtil.logE("mh", "err:" + e.getMessage());
			e.printStackTrace();
		}*/
		
		// 必须通过主线程调用
		ThreadUtil.checkUiThread();
		if (asyncTask == null) {
			return;
		}
		MHLogUtil.logD("mh", "asyncTask status:" + asyncTask.getStatus().toString());
		if (asyncTask == null || asyncTask.getStatus() == Status.RUNNING || asyncTask.getStatus() == Status.FINISHED) {
			return;
		}
		asyncTask.asyncExcute();
	}
	
	/**
	 * 取消请求操作,后台不执行
	 * */
	public void Canneled(String commandId){
		MHCommandAsyncTask task = taskMap.get(commandId);
		if(task!=null){
			task.cancel(true);
			task.onCancelled();
			taskMap.remove(taskMap.get(commandId));
		}
	}
}