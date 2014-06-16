package com.perlib.wmbg.book;

/**
 * Represents a book in the isbnDB api.
 */
public class JsonBook {
	
    /**
     * Instantiates a new json book.
     *
     */
    public JsonBook(String awards_text, String lcc_number, String urls_text,
			String book_id, String publisher_name, String summary,
			String title, Author[] author_data, String isbn13, String isbn10,
			String language, String dewey_decimal,
			String physical_description_text, String[] subject_ids,
			String publisher_id, String dewey_normal, String title_long,
			String marc_enc_level, String title_latin, String publisher_text,
			String notes, String edition_info) {
		super();
		this.awards_text = awards_text;
		this.lcc_number = lcc_number;
		this.urls_text = urls_text;
		this.book_id = book_id;
		this.publisher_name = publisher_name;
		this.summary = summary;
		this.title = title;
		this.author_data = author_data;
		this.isbn13 = isbn13;
		this.isbn10 = isbn10;
		this.language = language;
		this.dewey_decimal = dewey_decimal;
		this.physical_description_text = physical_description_text;
		this.subject_ids = subject_ids;
		this.publisher_id = publisher_id;
		this.dewey_normal = dewey_normal;
		this.title_long = title_long;
		this.marc_enc_level = marc_enc_level;
		this.title_latin = title_latin;
		this.publisher_text = publisher_text;
		this.notes = notes;
		this.edition_info = edition_info;
	}

	/**
	 * Instantiates a new json book.
	 */
	public JsonBook() {
		super();
		this.awards_text = "";
		this.lcc_number = "";
		this.urls_text = "";
		this.book_id = "";
		this.publisher_name = "";
		this.summary = "";
		this.title = "";
		this.author_data = new Author[]{};
		this.isbn13 = "";
		this.isbn10 = "";
		this.language = "";
		this.dewey_decimal = "";
		this.physical_description_text = "";
		this.subject_ids = new String[]{};
		this.publisher_id = "";
		this.dewey_normal = "";
		this.title_long = "";
		this.marc_enc_level = "";
		this.title_latin = "";
		this.publisher_text = "";
		this.notes = "";
		this.edition_info = "";
	}
    
    

	/** The awards_text. */
	private String awards_text;
	
	/** The lcc_number. */
	private String lcc_number;
	
	/** The urls_text. */
	private String urls_text;
	
	/** The book_id. */
	private String book_id;
	
	/** The publisher_name. */
	private String publisher_name;
	
	/** The summary. */
	private String summary;
	
	/** The title. */
	private String title;
	
	/** The author_data. */
	private Author[] author_data;
	
	/** The isbn13. */
	private String isbn13;
	
	/** The isbn10. */
	private String isbn10;
	
	/** The language. */
	private String language;
	
	/** The dewey_decimal. */
	private String dewey_decimal;
	
	/** The physical_description_text. */
	private String physical_description_text;
	
	/** The subject_ids. */
	private String[] subject_ids;
	
	/** The publisher_id. */
	private String publisher_id;
	
	/** The dewey_normal. */
	private String dewey_normal;
	
	/** The title_long. */
	private String title_long;
	
	/** The marc_enc_level. */
	private String marc_enc_level;
	
	/** The title_latin. */
	private String title_latin;
	
	/** The publisher_text. */
	private String publisher_text;
	
	/** The notes. */
	private String notes;
	
	/** The edition_info. */
	private String edition_info;
	
	/**
	 * Gets the awards_text.
	 *
	 * @return the awards_text
	 */
	public String getAwards_text() {
		return awards_text;
	}

	/**
	 * Sets the awards_text.
	 *
	 * @param awards_text the new awards_text
	 */
	public void setAwards_text(String awards_text) {
		this.awards_text = awards_text;
	}

	/**
	 * Gets the lcc_number.
	 *
	 * @return the lcc_number
	 */
	public String getLcc_number() {
		return lcc_number;
	}

	/**
	 * Sets the lcc_number.
	 *
	 * @param lcc_number the new lcc_number
	 */
	public void setLcc_number(String lcc_number) {
		this.lcc_number = lcc_number;
	}

	/**
	 * Gets the urls_text.
	 *
	 * @return the urls_text
	 */
	public String getUrls_text() {
		return urls_text;
	}

	/**
	 * Sets the urls_text.
	 *
	 * @param urls_text the new urls_text
	 */
	public void setUrls_text(String urls_text) {
		this.urls_text = urls_text;
	}

	/**
	 * Gets the book_id.
	 *
	 * @return the book_id
	 */
	public String getBook_id() {
		return book_id;
	}

	/**
	 * Sets the book_id.
	 *
	 * @param book_id the new book_id
	 */
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	/**
	 * Gets the publisher_name.
	 *
	 * @return the publisher_name
	 */
	public String getPublisher_name() {
		return publisher_name;
	}

	/**
	 * Sets the publisher_name.
	 *
	 * @param publisher_name the new publisher_name
	 */
	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the author_data.
	 *
	 * @return the author_data
	 */
	public Author[] getAuthor_data() {
		return author_data;
	}

	/**
	 * Sets the author_data.
	 *
	 * @param author_data the new author_data
	 */
	public void setAuthor_data(Author[] author_data) {
		this.author_data = author_data;
	}

	/**
	 * Gets the isbn13.
	 *
	 * @return the isbn13
	 */
	public String getIsbn13() {
		return isbn13;
	}

	/**
	 * Sets the isbn13.
	 *
	 * @param isbn13 the new isbn13
	 */
	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	/**
	 * Gets the isbn10.
	 *
	 * @return the isbn10
	 */
	public String getIsbn10() {
		return isbn10;
	}

	/**
	 * Sets the isbn10.
	 *
	 * @param isbn10 the new isbn10
	 */
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the dewey_decimal.
	 *
	 * @return the dewey_decimal
	 */
	public String getDewey_decimal() {
		return dewey_decimal;
	}

	/**
	 * Sets the dewey_decimal.
	 *
	 * @param dewey_decimal the new dewey_decimal
	 */
	public void setDewey_decimal(String dewey_decimal) {
		this.dewey_decimal = dewey_decimal;
	}

	/**
	 * Gets the physical_description_text.
	 *
	 * @return the physical_description_text
	 */
	public String getPhysical_description_text() {
		return physical_description_text;
	}

	/**
	 * Sets the physical_description_text.
	 *
	 * @param physical_description_text the new physical_description_text
	 */
	public void setPhysical_description_text(String physical_description_text) {
		this.physical_description_text = physical_description_text;
	}

	/**
	 * Gets the subject_ids.
	 *
	 * @return the subject_ids
	 */
	public String[] getSubject_ids() {
		return subject_ids;
	}

	/**
	 * Sets the subject_ids.
	 *
	 * @param subject_ids the new subject_ids
	 */
	public void setSubject_ids(String[] subject_ids) {
		this.subject_ids = subject_ids;
	}

	/**
	 * Gets the publisher_id.
	 *
	 * @return the publisher_id
	 */
	public String getPublisher_id() {
		return publisher_id;
	}

	/**
	 * Sets the publisher_id.
	 *
	 * @param publisher_id the new publisher_id
	 */
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	/**
	 * Gets the dewey_normal.
	 *
	 * @return the dewey_normal
	 */
	public String getDewey_normal() {
		return dewey_normal;
	}

	/**
	 * Sets the dewey_normal.
	 *
	 * @param dewey_normal the new dewey_normal
	 */
	public void setDewey_normal(String dewey_normal) {
		this.dewey_normal = dewey_normal;
	}

	/**
	 * Gets the title_long.
	 *
	 * @return the title_long
	 */
	public String getTitle_long() {
		return title_long;
	}

	/**
	 * Sets the title_long.
	 *
	 * @param title_long the new title_long
	 */
	public void setTitle_long(String title_long) {
		this.title_long = title_long;
	}

	/**
	 * Gets the marc_enc_level.
	 *
	 * @return the marc_enc_level
	 */
	public String getMarc_enc_level() {
		return marc_enc_level;
	}

	/**
	 * Sets the marc_enc_level.
	 *
	 * @param marc_enc_level the new marc_enc_level
	 */
	public void setMarc_enc_level(String marc_enc_level) {
		this.marc_enc_level = marc_enc_level;
	}

	/**
	 * Gets the title_latin.
	 *
	 * @return the title_latin
	 */
	public String getTitle_latin() {
		return title_latin;
	}

	/**
	 * Sets the title_latin.
	 *
	 * @param title_latin the new title_latin
	 */
	public void setTitle_latin(String title_latin) {
		this.title_latin = title_latin;
	}

	/**
	 * Gets the publisher_text.
	 *
	 * @return the publisher_text
	 */
	public String getPublisher_text() {
		return publisher_text;
	}

	/**
	 * Sets the publisher_text.
	 *
	 * @param publisher_text the new publisher_text
	 */
	public void setPublisher_text(String publisher_text) {
		this.publisher_text = publisher_text;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the edition_info.
	 *
	 * @return the edition_info
	 */
	public String getEdition_info() {
		return edition_info;
	}

	/**
	 * Sets the edition_info.
	 *
	 * @param edition_info the new edition_info
	 */
	public void setEdition_info(String edition_info) {
		this.edition_info = edition_info;
	}
    
}
