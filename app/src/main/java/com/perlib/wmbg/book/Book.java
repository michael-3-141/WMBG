package com.perlib.wmbg.book;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * A WMBG book. Used in all the app to represent a book.
 */
public class Book implements Parcelable {
	
	/** The name. */
	@Expose private String name;
	
	/** The author. */
	@Expose private String author;
	
	/** The name of the person the book is lended to. */
	@Expose private String lendedTo;
	
	/** The email of the person the book is lended to. */
	@Expose private String email;
	
	/** The date the book was lended. */
	@Expose private long dateLended;
	
	/** The url of a thumbnail image of the book. */
	@Expose private String thumbnailUrl;
	
	//@Expose private boolean isLocal;
	
	/**
	 * Instantiates a new book.
	 *
	 */
	public Book(String name, String author, String lendedTo, String email, long dateLended, String thumbnailUrl) {
		this.name = name;
		this.author = author;
		this.lendedTo = lendedTo;
		this.email = email;
		this.dateLended = dateLended;
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * Instantiates a new book.
	 *
	 */
	public Book(String name, String author, String lendedTo, String email, long dateLended)
	{
		this.name = name;
		this.author = author;
		this.lendedTo = lendedTo;
		this.email = email;
		this.dateLended = dateLended;
	}
	
	/**
	 * Instantiates a new book.
	 */
	public Book()
	{
		this.name = "";
		this.author = "";
		this.lendedTo = "";
		this.email = "";
	}
	
	/**
	 * Instantiates a new book from a parcel (Part of the implemenation of {@link Parcelable}).
	 *
	 * @param dest the dest
	 */
	private Book(Parcel dest)
	{
		this.name = dest.readString();
		this.author = dest.readString();
		this.lendedTo = dest.readString();
		this.email = dest.readString();
		this.dateLended = dest.readLong();
		this.thumbnailUrl = dest.readString();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	

	/**
	 * Gets the lended to.
	 *
	 * @return the lended to
	 */
	public String getLendedTo() {
		return lendedTo;
	}

	/**
	 * Sets the lended to.
	 *
	 * @param lendedTo the new lended to
	 */
	public void setLendedTo(String lendedTo) {
		this.lendedTo = lendedTo;
	}

	/**
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(this.name);
		dest.writeString(this.author);
		dest.writeString(this.lendedTo);
		dest.writeString(this.email);
		dest.writeLong(this.dateLended);
		dest.writeString(this.thumbnailUrl);
	}
	
	/** The Constant CREATOR. */
	public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>()
	{
		public Book createFromParcel(Parcel in)
		{
			return new Book(in);
		}
		
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the date lended.
	 *
	 * @return the date lended
	 */
	public long getDateLended() {
		return dateLended;
	}

	/**
	 * Sets the date lended.
	 *
	 * @param dateLended the new date lended
	 */
	public void setDateLended(long dateLended) {
		this.dateLended = dateLended;
	}
	
	/**
	 * Checks if is lended.
	 *
	 * @return true, if is lended
	 */
	public boolean isLended()
	{
		return this.lendedTo.length() > 0;
	}

	/**
	 * Gets the thumbnail url.
	 *
	 * @return the thumbnail url
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 * Sets the thumbnail url.
	 *
	 * @param thumbnailUrl the new thumbnail url
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

    /**
     * Returns the book. (Clears lended to name and lended to email)
     */
    public void returnBook(){
        setLendedTo("");
        setEmail("");
        setDateLended(-1);
    }
}
