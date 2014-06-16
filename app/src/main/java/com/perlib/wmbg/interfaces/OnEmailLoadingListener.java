package com.perlib.wmbg.interfaces;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving onEmailLoading events.
 * The class that is interested in processing a onEmailLoading
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addOnEmailLoadingListener<code> method. When
 * the onEmailLoading event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OnEmailLoadingEvent
 */
public interface OnEmailLoadingListener {
	
	/**
	 * On email loading completed.
	 *
	 * @param email the email
	 */
	void OnEmailLoadingCompleted(String email);
}
