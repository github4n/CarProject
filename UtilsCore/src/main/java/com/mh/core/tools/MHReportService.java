package com.mh.core.tools;

import java.util.Vector;

public class MHReportService {
private static MHReportService walletService = null;
	
	private static Vector<MHReportListener> walletListeners = new Vector<MHReportListener>();
	
	public static MHReportService getInstance(){
		if (walletService == null) {
			synchronized (MHReportService.class) {
				if (walletService == null) {
					walletService = new MHReportService();
				}
			}
		}
		return walletService;
	}

	public  Vector<MHReportListener> getWalletListeners() {
		return walletListeners;
	}

	public  void addWalletListeners(MHReportListener walletListeners) {
		if (walletListeners != null) {
			MHReportService.walletListeners.add(walletListeners);
		}
		
	}
}
