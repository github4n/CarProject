package com.mh.core.beans;

import java.io.Serializable;

import android.text.TextUtils;

public class UrlBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String mhLoginPreferredUrl;
	private String mhLoginSpareUrl;
	private String mhAdsPreferredUrl;
	private String mhAdsSpareUrl;
	private String mhPayPreferredUrl;
	private String mhPaySpareUrl;
	private String mhFbPreferredUrl;
	private String mhFbSpareUrl;
	private String mhGameSpareUrl;
	private String mhGamePreferredUrl;
	private String mhQuestionPreferredUrl;
	private String mhPlatformPreferredUrl;
//	private String mhLuaSwitchUrl;
	private boolean isQxdlSwitch = false;
	
	public boolean isEmpty() {
		return TextUtils.isEmpty(mhLoginPreferredUrl) || TextUtils.isEmpty(mhAdsPreferredUrl) || TextUtils.isEmpty(mhPayPreferredUrl);
	}
	
	public String getMHLoginPreferredUrl() {
		return mhLoginPreferredUrl;
	}
	public void setMHLoginPreferredUrl(String mhLoginPreferredUrl) {
		this.mhLoginPreferredUrl = mhLoginPreferredUrl;
	}
	public String getMHLoginSpareUrl() {
		return mhLoginSpareUrl;
	}
	public void setMHLoginSpareUrl(String mhLoginSpareUrl) {
		this.mhLoginSpareUrl = mhLoginSpareUrl;
	}
	public String getMHAdsPreferredUrl() {
		return mhAdsPreferredUrl;
	}
	public void setMHAdsPreferredUrl(String mhAdsPreferredUrl) {
		this.mhAdsPreferredUrl = mhAdsPreferredUrl;
	}
	public String getMHAdsSpareUrl() {
		return mhAdsSpareUrl;
	}
	public void setMHAdsSpareUrl(String mhAdsSpareUrl) {
		this.mhAdsSpareUrl = mhAdsSpareUrl;
	}
	public String getMHPayPreferredUrl() {
		return mhPayPreferredUrl;
	}
	public void setMHPayPreferredUrl(String mhPayPreferredUrl) {
		this.mhPayPreferredUrl = mhPayPreferredUrl;
	}
	public String getMHPaySpareUrl() {
		return mhPaySpareUrl;
	}
	public void setMHPaySpareUrl(String mhPaySpareUrl) {
		this.mhPaySpareUrl = mhPaySpareUrl;
	}
	public String getMHFbPreferredUrl() {
		return mhFbPreferredUrl;
	}
	public void setMHFbPreferredUrl(String mhFbPreferredUrl) {
		this.mhFbPreferredUrl = mhFbPreferredUrl;
	}
	public String getMHFbSpareUrl() {
		return mhFbSpareUrl;
	}
	public void setMHFbSpareUrl(String mhFbSpareUrl) {
		this.mhFbSpareUrl = mhFbSpareUrl;
	}
	public String getMHGameSpareUrl() {
		return mhGameSpareUrl;
	}
	public void setMHGameSpareUrl(String mhGameSpareUrl) {
		this.mhGameSpareUrl = mhGameSpareUrl;
	}
	public String getMHGamePreferredUrl() {
		return mhGamePreferredUrl;
	}
	public void setMHGamePreferredUrl(String mhGamePreferredUrl) {
		this.mhGamePreferredUrl = mhGamePreferredUrl;
	}
	public String getMHQuestionPreferredUrl() {
		return mhQuestionPreferredUrl;
	}
	public void setMHQuestionPreferredUrl(String mhQuestionPreferredUrl) {
		this.mhQuestionPreferredUrl = mhQuestionPreferredUrl;
	}
	public String getMHPlatformPreferredUrl() {
		return mhPlatformPreferredUrl;
	}
	public void setMHPlatformPreferredUrl(String mhPlatformPreferredUrl) {
		this.mhPlatformPreferredUrl = mhPlatformPreferredUrl;
	}
	
	public boolean isQxdlSwitch() {
		return isQxdlSwitch;
	}
	public void setQxdlSwitch(boolean isQxdlSwitch) {
		this.isQxdlSwitch = isQxdlSwitch;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UrlBean [mhLoginPreferredUrl=" + mhLoginPreferredUrl + ", mhLoginSpareUrl=" + mhLoginSpareUrl + ", mhAdsPreferredUrl="
				+ mhAdsPreferredUrl + ", mhAdsSpareUrl=" + mhAdsSpareUrl + ", mhPayPreferredUrl=" + mhPayPreferredUrl + ", mhPaySpareUrl="
				+ mhPaySpareUrl + ", mhFbPreferredUrl=" + mhFbPreferredUrl + ", mhFbSpareUrl=" + mhFbSpareUrl + ", mhGameSpareUrl="
				+ mhGameSpareUrl + ", mhGamePreferredUrl=" + mhGamePreferredUrl + ", mhQuestionPreferredUrl=" + mhQuestionPreferredUrl
				+ ", mhPlatformPreferredUrl=" + mhPlatformPreferredUrl  + ", isQxdlSwitch="
				+ isQxdlSwitch + "]";
	}

/*	public String getMHLuaSwitchUrl() {
		return mhLuaSwitchUrl;
	}

	public void setMHLuaSwitchUrl(String mhLuaSwitchUrl) {
		this.mhLuaSwitchUrl = mhLuaSwitchUrl;
	}*/
	
	
}
