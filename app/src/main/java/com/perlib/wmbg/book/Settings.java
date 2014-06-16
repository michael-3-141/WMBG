package com.perlib.wmbg.book;

import com.perlib.wmbg.R;
import com.google.gson.annotations.Expose;

import android.content.Context;

/**
 * The Class Settings.
 */
public class Settings {
	
	/** The email message. */
	@Expose private String emailMessage;
	
	/** The confirm delete. */
	@Expose private boolean confirmDelete;
	
	/** The swipe mode. */
	@Expose private int swipeMode;
	
	/** The cx. */
	Context cx;
	
	/** The Constant MODE_DELETE_ITEM. */
	public static final int MODE_DELETE_ITEM = 0;
	
	/** The Constant MODE_RETURN_ITEM. */
	public static final int MODE_RETURN_ITEM = 1;
	
	/** The Constant MODE_NOTHING. */
	public static final int MODE_NOTHING = 2;

	/**
	 * Instantiates a new settings.
	 *
	 * @param emailMessage the email message
	 * @param confirmDelete the confirm delete
	 * @param swipeMode the swipe mode
	 * @param cx the cx
	 */
	public Settings(String emailMessage,boolean confirmDelete,int swipeMode, Context cx) {
		super();
		this.emailMessage = emailMessage;
		this.confirmDelete = confirmDelete;
		this.swipeMode = swipeMode;
		this.cx = cx;
	}

	/**
	 * Instantiates a new settings.
	 *
	 * @param cx the cx
	 */
	public Settings(Context cx) {
		super();
		this.emailMessage = cx.getString(R.string.emailBodyA) + "@book@" + cx.getString(R.string.emailBodyB);
		this.confirmDelete = true;
		this.swipeMode = 1;
		this.cx = cx;
	}

	/**
	 * Gets the email message.
	 *
	 * @return the email message
	 */
	public String getEmailMessage() {
		return emailMessage;
	}

	/**
	 * Sets the email message.
	 *
	 * @param emailMessage the new email message
	 */
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	/**
	 * Checks if is confirm delete.
	 *
	 * @return true, if is confirm delete
	 */
	public boolean isConfirmDelete() {
		return confirmDelete;
	}

	/**
	 * Sets the confirm delete.
	 *
	 * @param confirmDelete the new confirm delete
	 */
	public void setConfirmDelete(boolean confirmDelete) {
		this.confirmDelete = confirmDelete;
	}

	/**
	 * Gets the swipe mode.
	 *
	 * @return the swipe mode
	 */
	public int getSwipeMode() {
		return swipeMode;
	}

	/**
	 * Sets the swipe mode.
	 *
	 * @param swipeMode the new swipe mode
	 */
	public void setSwipeMode(int swipeMode) {
		this.swipeMode = swipeMode;
	}
}
