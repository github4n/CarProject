package com.mh.core.task;


import com.mh.core.task.command.abstracts.MHCommand;


/**
 * 请求回调
 * @author Administrator
 *
 */
public interface MHCommandCallBack {
	public void cmdCallBack(MHCommand command);
}
