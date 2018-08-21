package com.mh.core.tools;

import com.mh.core.callback.MHCallBack;

public interface MHReportListener extends MHCallBack{
	/**
	 * 
	 * @param mhOrdeId mh订单号
	 * @param payMentSque  储值订单号
	 * @param creitedId   creitedId
	 * @param projectId   储值项ID
	 * @param projectPrice  储值项金额
	 * @param serverCode   服务器ID
	 * @param remark   remark
	 */
	public void mhWallet(String mhOrdeId,String payMentSque,String mhUserId,String crietedId,String serverCode,String projectId,String projectPrice,String remark);
	public void mhError();
}
