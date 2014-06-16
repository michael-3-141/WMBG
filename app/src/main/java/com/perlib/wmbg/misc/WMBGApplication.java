package com.perlib.wmbg.misc;

import android.app.Application;

// TODO: Auto-generated Javadoc
/**
 * The Class WMBGApplication.
 */
public class WMBGApplication extends Application {
	
	/** The instance. */
	private static WMBGApplication instance; 
	
	/**
	 * Instantiates a new WMBG application.
	 */
	public WMBGApplication() {
		super();
		WMBGApplication.instance = this;
	}

	/**
	 * Gets the single instance of WMBGApplication.
	 *
	 * @return single instance of WMBGApplication
	 */
	public static WMBGApplication getInstance() {
		return instance;
	}
}
