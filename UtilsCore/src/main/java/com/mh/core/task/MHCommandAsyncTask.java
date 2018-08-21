package com.mh.core.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;

import com.mh.core.res.MHResConfiguration;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHLocalUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.ThreadUtil;

import java.lang.ref.WeakReference;

public class MHCommandAsyncTask extends MHRequestAsyncTask{
	
	private MHCommand command;
	private ProgressDialog progress;
	private WeakReference<Context> mContext;
	private boolean canceled = false;
	
	public MHCommandAsyncTask(Context context,MHCommand _command) {
		this.command = _command;
		this.mContext = new WeakReference<>(context);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		MHLogUtil.logI("MHCommandAsyncTask", "onPreExecute");
		//必须通过主线程调用
		if(mContext.get() != null) {
			ThreadUtil.checkUiThread(mContext.get());
			MHResConfiguration.getSDKLoginSign(mContext.get());
		}
		if (command.isShowProgress()) {//判断是否要显示进度条
			if (!TextUtils.isEmpty(command.getCommandMsg())) {
				if (mContext.get() != null) {
					progress = new ProgressDialog(mContext.get());
					progress.setOnDismissListener(new OnDismissListener() {
						
						@Override
						public void onDismiss(DialogInterface dialog) {
							MHLogUtil.logD("mh", "dismiss");
						}
					});
					progress.setOnCancelListener(new OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							MHLogUtil.logD("mh", "onCancel");
							command.setHasCancel(true);
							canceled = true;
							MHCommandAsyncTask.this.cancel(true);
							dialog.dismiss();
							if (command != null && command.getCancelListener() != null) {
								command.getCancelListener().onCancel(dialog);
							}
						}
					});
					progress.setMessage(command.getCommandMsg());
					progress.setCanceledOnTouchOutside(command.getCanceledOnTouchOutside());
					progress.setCancelable(command.getCancelable());
					try {
						progress.show();
					} catch (Exception e) {
						progress = null;
						e.printStackTrace();
					}
				}
			}
		}
	}  
	
	@Override
	protected String doInBackground(String... params) {
		MHLogUtil.logI("MHCommandAsyncTask", "doInBackground");
		try {
			if(mContext.get() != null) {
				MHLocalUtil.getMHUUid(mContext.get());
			}
			if (canceled) {
				return "";
			}
			command.execute();			
		} catch (Exception e) {
			e.printStackTrace();
			if (!command.isShowProgress()) {
				return null;
			}
			if(mContext !=null/*&&!mContext.isFinishing()*/){
				if(progress!=null&&progress.isShowing()){
					try {
						progress.dismiss();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					progress=null;
				}
			}else{
				try{
					if (progress != null) {
						progress.dismiss();
						progress = null;
					}
				}catch(Exception ex){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		MHLogUtil.logI("MHCommandAsyncTask", "onPostExecute");
		if (command.isHasCancel()) {
			return;
		}
		if (canceled) {
			return;
		}
		if(command.getCallback()!=null){
			command.getCallback().cmdCallBack(command);
			
			if (!command.isShowProgress()) {
				return;
			}
			
			if(mContext !=null/*&&!mContext.isFinishing()*/){
				if(progress!=null&&progress.isShowing()){
					try {
						progress.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
					progress=null;
				}
			}else{
				try{
					if (progress != null) {
						progress.dismiss();
						progress = null;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		command.getCallback().cmdCallBack(command);
	}

}
