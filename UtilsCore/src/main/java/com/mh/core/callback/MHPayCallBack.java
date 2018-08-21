package com.mh.core.callback;

import android.os.Bundle;

public interface MHPayCallBack extends MHCallBack{
	
	void onPaySuccess(Bundle bundle);
	
	void onPayFailure(Bundle bundle);
}


