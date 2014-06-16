package com.perlib.wmbg.book;

// TODO: Auto-generated Javadoc
/**
 * The Class Author.
 */
public class Author {
	
	/**
	 * Instantiates a new author.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Author(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new author.
	 */
	public Author() {
		id = "";
		name = "";
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
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
}
