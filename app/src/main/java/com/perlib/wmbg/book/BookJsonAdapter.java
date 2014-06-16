package com.perlib.wmbg.book;

// TODO: Auto-generated Javadoc
/**
 * The Class BookJsonAdapter.
 */
public class BookJsonAdapter {
	
	/** The data. */
	JsonBook[] data = new JsonBook[]{};
	
	/** The index searched. */
	String indexSearched;
	
	/** The error. */
	String error;
	
	/**
	 * Convert to book.
	 *
	 * @return the book
	 */
	public Book convertToBook()
	{
		Book convertedBook = new Book();
		if(data == null && error == null)
		{
			return null;
		}
		if(data.length < 1)
		{
			return null;
		}
		else if(data[0].getAuthor_data().length < 1)
		{
			convertedBook.setName(data[0].getTitle());
		}
		else
		{
			convertedBook.setName(data[0].getTitle());
			convertedBook.setAuthor(data[0].getAuthor_data()[0].getName());
		}
		return convertedBook;
	}
}
