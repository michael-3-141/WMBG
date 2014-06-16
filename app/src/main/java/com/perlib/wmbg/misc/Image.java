package com.perlib.wmbg.misc;

import java.io.File;

public class Image {
	
	private String url;
	
	private File file;
	
	private boolean mode;
	
	public static boolean MODE_URL = true;
	
	public static boolean MODE_FILE = false;

	public Object getValue()
	{
		if(mode == MODE_FILE)return file;
		else return url;
	}
	
	public void setValue(Object newValue)
	{
		if(mode == MODE_FILE)
		{
			if(!(newValue instanceof File))throw new IllegalArgumentException();
			file = (File) newValue;
		}
		else
		{
			if(!(newValue instanceof String))throw new IllegalArgumentException();
			url = (String) newValue;
		}
	}

	public boolean getMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

	public Image(String url, File file, boolean mode) {
		this.url = url;
		this.file = file;
		this.mode = mode;
	}
	
	public Image(String url) {
		this.url = url;
		this.mode = MODE_URL;
	}
	
	public Image(File file) {
		this.file = file;
		this.mode = MODE_FILE;
	}
	
	
}
