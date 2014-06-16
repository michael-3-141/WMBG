package com.perlib.wmbg.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a book search in the Google Book API, with built in feature to convert to WMBG's book class.
 */
public class GoogleBooksBook {

	/** The kind. */
	private String kind;
	
	/** The total items. */
	private Integer totalItems;
	
	/** The items. */
	private List<Item> items = new ArrayList<Item>();
	
	/** The additional properties. */
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	/**
	 * Converts the Google Book to WMBG's book format.
	 *
	 * @return the converted book. Returns null if there warn't any book results.
	 */
	public Book toBook(){
		if(items.size() < 1){
			return null;
		}
		else if(!items.get(0).getKind().equals("books#volume"))
		{
			return null;
		}
		else{
			VolumeInfo book = items.get(0).getVolumeInfo();
			Book result = new Book();
			result.setName(book.getTitle());
			if(book.getAuthors().size() >= 1)result.setAuthor(book.getAuthors().get(0));
			if(book.getImageLinks().getSmallThumbnail() != null && book.getImageLinks().getSmallThumbnail() != "")result.setThumbnailUrl(book.getImageLinks().getSmallThumbnail());
			return result;
		}
	}

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind the new kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Gets the total items.
	 *
	 * @return the total items
	 */
	public Integer getTotalItems() {
		return totalItems;
	}

	/**
	 * Sets the total items.
	 *
	 * @param totalItems the new total items
	 */
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Gets the additional properties.
	 *
	 * @return the additional properties
	 */
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	/**
	 * Sets the additional property.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	/**
	 * The Class AccessInfo.
	 */
	public class AccessInfo {

		/** The country. */
		private String country;
		
		/** The viewability. */
		private String viewability;
		
		/** The embeddable. */
		private Boolean embeddable;
		
		/** The public domain. */
		private Boolean publicDomain;
		
		/** The text to speech permission. */
		private String textToSpeechPermission;
		
		/** The epub. */
		private Epub epub;
		
		/** The pdf. */
		private Pdf pdf;
		
		/** The web reader link. */
		private String webReaderLink;
		
		/** The access view status. */
		private String accessViewStatus;
		
		/** The quote sharing allowed. */
		private Boolean quoteSharingAllowed;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the country.
		 *
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}

		/**
		 * Sets the country.
		 *
		 * @param country the new country
		 */
		public void setCountry(String country) {
			this.country = country;
		}

		/**
		 * Gets the viewability.
		 *
		 * @return the viewability
		 */
		public String getViewability() {
			return viewability;
		}

		/**
		 * Sets the viewability.
		 *
		 * @param viewability the new viewability
		 */
		public void setViewability(String viewability) {
			this.viewability = viewability;
		}

		/**
		 * Gets the embeddable.
		 *
		 * @return the embeddable
		 */
		public Boolean getEmbeddable() {
			return embeddable;
		}

		/**
		 * Sets the embeddable.
		 *
		 * @param embeddable the new embeddable
		 */
		public void setEmbeddable(Boolean embeddable) {
			this.embeddable = embeddable;
		}

		/**
		 * Gets the public domain.
		 *
		 * @return the public domain
		 */
		public Boolean getPublicDomain() {
			return publicDomain;
		}

		/**
		 * Sets the public domain.
		 *
		 * @param publicDomain the new public domain
		 */
		public void setPublicDomain(Boolean publicDomain) {
			this.publicDomain = publicDomain;
		}

		/**
		 * Gets the text to speech permission.
		 *
		 * @return the text to speech permission
		 */
		public String getTextToSpeechPermission() {
			return textToSpeechPermission;
		}

		/**
		 * Sets the text to speech permission.
		 *
		 * @param textToSpeechPermission the new text to speech permission
		 */
		public void setTextToSpeechPermission(String textToSpeechPermission) {
			this.textToSpeechPermission = textToSpeechPermission;
		}

		/**
		 * Gets the epub.
		 *
		 * @return the epub
		 */
		public Epub getEpub() {
			return epub;
		}

		/**
		 * Sets the epub.
		 *
		 * @param epub the new epub
		 */
		public void setEpub(Epub epub) {
			this.epub = epub;
		}

		/**
		 * Gets the pdf.
		 *
		 * @return the pdf
		 */
		public Pdf getPdf() {
			return pdf;
		}

		/**
		 * Sets the pdf.
		 *
		 * @param pdf the new pdf
		 */
		public void setPdf(Pdf pdf) {
			this.pdf = pdf;
		}

		/**
		 * Gets the web reader link.
		 *
		 * @return the web reader link
		 */
		public String getWebReaderLink() {
			return webReaderLink;
		}

		/**
		 * Sets the web reader link.
		 *
		 * @param webReaderLink the new web reader link
		 */
		public void setWebReaderLink(String webReaderLink) {
			this.webReaderLink = webReaderLink;
		}

		/**
		 * Gets the access view status.
		 *
		 * @return the access view status
		 */
		public String getAccessViewStatus() {
			return accessViewStatus;
		}

		/**
		 * Sets the access view status.
		 *
		 * @param accessViewStatus the new access view status
		 */
		public void setAccessViewStatus(String accessViewStatus) {
			this.accessViewStatus = accessViewStatus;
		}

		/**
		 * Gets the quote sharing allowed.
		 *
		 * @return the quote sharing allowed
		 */
		public Boolean getQuoteSharingAllowed() {
			return quoteSharingAllowed;
		}

		/**
		 * Sets the quote sharing allowed.
		 *
		 * @param quoteSharingAllowed the new quote sharing allowed
		 */
		public void setQuoteSharingAllowed(Boolean quoteSharingAllowed) {
			this.quoteSharingAllowed = quoteSharingAllowed;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class Epub.
	 */
	public class Epub {

		/** The is available. */
		private Boolean isAvailable;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the checks if is available.
		 *
		 * @return the checks if is available
		 */
		public Boolean getIsAvailable() {
			return isAvailable;
		}

		/**
		 * Sets the checks if is available.
		 *
		 * @param isAvailable the new checks if is available
		 */
		public void setIsAvailable(Boolean isAvailable) {
			this.isAvailable = isAvailable;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class ImageLinks.
	 */
	public class ImageLinks {

		/** The small thumbnail. */
		private String smallThumbnail;
		
		/** The thumbnail. */
		private String thumbnail;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the small thumbnail.
		 *
		 * @return the small thumbnail
		 */
		public String getSmallThumbnail() {
			return smallThumbnail;
		}

		/**
		 * Sets the small thumbnail.
		 *
		 * @param smallThumbnail the new small thumbnail
		 */
		public void setSmallThumbnail(String smallThumbnail) {
			this.smallThumbnail = smallThumbnail;
		}

		/**
		 * Gets the thumbnail.
		 *
		 * @return the thumbnail
		 */
		public String getThumbnail() {
			return thumbnail;
		}

		/**
		 * Sets the thumbnail.
		 *
		 * @param thumbnail the new thumbnail
		 */
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class IndustryIdentifier.
	 */
	public class IndustryIdentifier {

		/** The type. */
		private String type;
		
		/** The identifier. */
		private String identifier;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * Sets the type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * Gets the identifier.
		 *
		 * @return the identifier
		 */
		public String getIdentifier() {
			return identifier;
		}

		/**
		 * Sets the identifier.
		 *
		 * @param identifier the new identifier
		 */
		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class Item.
	 */
	public class Item {

		/** The kind. */
		private String kind;
		
		/** The id. */
		private String id;
		
		/** The etag. */
		private String etag;
		
		/** The self link. */
		private String selfLink;
		
		/** The volume info. */
		private VolumeInfo volumeInfo;
		
		/** The sale info. */
		private SaleInfo saleInfo;
		
		/** The access info. */
		private AccessInfo accessInfo;
		
		/** The search info. */
		private SearchInfo searchInfo;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the kind.
		 *
		 * @return the kind
		 */
		public String getKind() {
			return kind;
		}

		/**
		 * Sets the kind.
		 *
		 * @param kind the new kind
		 */
		public void setKind(String kind) {
			this.kind = kind;
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
		 * Gets the etag.
		 *
		 * @return the etag
		 */
		public String getEtag() {
			return etag;
		}

		/**
		 * Sets the etag.
		 *
		 * @param etag the new etag
		 */
		public void setEtag(String etag) {
			this.etag = etag;
		}

		/**
		 * Gets the self link.
		 *
		 * @return the self link
		 */
		public String getSelfLink() {
			return selfLink;
		}

		/**
		 * Sets the self link.
		 *
		 * @param selfLink the new self link
		 */
		public void setSelfLink(String selfLink) {
			this.selfLink = selfLink;
		}

		/**
		 * Gets the volume info.
		 *
		 * @return the volume info
		 */
		public VolumeInfo getVolumeInfo() {
			return volumeInfo;
		}

		/**
		 * Sets the volume info.
		 *
		 * @param volumeInfo the new volume info
		 */
		public void setVolumeInfo(VolumeInfo volumeInfo) {
			this.volumeInfo = volumeInfo;
		}

		/**
		 * Gets the sale info.
		 *
		 * @return the sale info
		 */
		public SaleInfo getSaleInfo() {
			return saleInfo;
		}

		/**
		 * Sets the sale info.
		 *
		 * @param saleInfo the new sale info
		 */
		public void setSaleInfo(SaleInfo saleInfo) {
			this.saleInfo = saleInfo;
		}

		/**
		 * Gets the access info.
		 *
		 * @return the access info
		 */
		public AccessInfo getAccessInfo() {
			return accessInfo;
		}

		/**
		 * Sets the access info.
		 *
		 * @param accessInfo the new access info
		 */
		public void setAccessInfo(AccessInfo accessInfo) {
			this.accessInfo = accessInfo;
		}

		/**
		 * Gets the search info.
		 *
		 * @return the search info
		 */
		public SearchInfo getSearchInfo() {
			return searchInfo;
		}

		/**
		 * Sets the search info.
		 *
		 * @param searchInfo the new search info
		 */
		public void setSearchInfo(SearchInfo searchInfo) {
			this.searchInfo = searchInfo;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class Pdf.
	 */
	public class Pdf {

		/** The is available. */
		private Boolean isAvailable;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the checks if is available.
		 *
		 * @return the checks if is available
		 */
		public Boolean getIsAvailable() {
			return isAvailable;
		}

		/**
		 * Sets the checks if is available.
		 *
		 * @param isAvailable the new checks if is available
		 */
		public void setIsAvailable(Boolean isAvailable) {
			this.isAvailable = isAvailable;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class SaleInfo.
	 */
	public class SaleInfo {

		/** The country. */
		private String country;
		
		/** The saleability. */
		private String saleability;
		
		/** The is ebook. */
		private Boolean isEbook;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the country.
		 *
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}

		/**
		 * Sets the country.
		 *
		 * @param country the new country
		 */
		public void setCountry(String country) {
			this.country = country;
		}

		/**
		 * Gets the saleability.
		 *
		 * @return the saleability
		 */
		public String getSaleability() {
			return saleability;
		}

		/**
		 * Sets the saleability.
		 *
		 * @param saleability the new saleability
		 */
		public void setSaleability(String saleability) {
			this.saleability = saleability;
		}

		/**
		 * Gets the checks if is ebook.
		 *
		 * @return the checks if is ebook
		 */
		public Boolean getIsEbook() {
			return isEbook;
		}

		/**
		 * Sets the checks if is ebook.
		 *
		 * @param isEbook the new checks if is ebook
		 */
		public void setIsEbook(Boolean isEbook) {
			this.isEbook = isEbook;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class SearchInfo.
	 */
	public class SearchInfo {

		/** The text snippet. */
		private String textSnippet;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		/**
		 * Gets the text snippet.
		 *
		 * @return the text snippet
		 */
		public String getTextSnippet() {
			return textSnippet;
		}

		/**
		 * Sets the text snippet.
		 *
		 * @param textSnippet the new text snippet
		 */
		public void setTextSnippet(String textSnippet) {
			this.textSnippet = textSnippet;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}

	/**
	 * The Class VolumeInfo.
	 */
	public class VolumeInfo {

		/** The title. */
		private String title;
		
		/** The authors. */
		private List<String> authors = new ArrayList<String>();
		
		/** The publisher. */
		private String publisher;
		
		/** The published date. */
		private String publishedDate;
		
		/** The description. */
		private String description;
		
		/** The industry identifiers. */
		private List<IndustryIdentifier> industryIdentifiers = new ArrayList<IndustryIdentifier>();
		
		/** The page count. */
		private Integer pageCount;
		
		/** The print type. */
		private String printType;
		
		/** The categories. */
		private List<String> categories = new ArrayList<String>();
		
		/** The average rating. */
		private Double averageRating;
		
		/** The ratings count. */
		private Integer ratingsCount;
		
		/** The content version. */
		private String contentVersion;
		
		/** The image links. */
		private ImageLinks imageLinks;
		
		/** The language. */
		private String language;
		
		/** The preview link. */
		private String previewLink;
		
		/** The info link. */
		private String infoLink;
		
		/** The canonical volume link. */
		private String canonicalVolumeLink;
		
		/** The additional properties. */
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
		 * Gets the authors.
		 *
		 * @return the authors
		 */
		public List<String> getAuthors() {
			return authors;
		}

		/**
		 * Sets the authors.
		 *
		 * @param authors the new authors
		 */
		public void setAuthors(List<String> authors) {
			this.authors = authors;
		}

		/**
		 * Gets the publisher.
		 *
		 * @return the publisher
		 */
		public String getPublisher() {
			return publisher;
		}

		/**
		 * Sets the publisher.
		 *
		 * @param publisher the new publisher
		 */
		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}

		/**
		 * Gets the published date.
		 *
		 * @return the published date
		 */
		public String getPublishedDate() {
			return publishedDate;
		}

		/**
		 * Sets the published date.
		 *
		 * @param publishedDate the new published date
		 */
		public void setPublishedDate(String publishedDate) {
			this.publishedDate = publishedDate;
		}

		/**
		 * Gets the description.
		 *
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Sets the description.
		 *
		 * @param description the new description
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * Gets the industry identifiers.
		 *
		 * @return the industry identifiers
		 */
		public List<IndustryIdentifier> getIndustryIdentifiers() {
			return industryIdentifiers;
		}

		/**
		 * Sets the industry identifiers.
		 *
		 * @param industryIdentifiers the new industry identifiers
		 */
		public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
			this.industryIdentifiers = industryIdentifiers;
		}

		/**
		 * Gets the page count.
		 *
		 * @return the page count
		 */
		public Integer getPageCount() {
			return pageCount;
		}

		/**
		 * Sets the page count.
		 *
		 * @param pageCount the new page count
		 */
		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}

		/**
		 * Gets the prints the type.
		 *
		 * @return the prints the type
		 */
		public String getPrintType() {
			return printType;
		}

		/**
		 * Sets the prints the type.
		 *
		 * @param printType the new prints the type
		 */
		public void setPrintType(String printType) {
			this.printType = printType;
		}

		/**
		 * Gets the categories.
		 *
		 * @return the categories
		 */
		public List<String> getCategories() {
			return categories;
		}

		/**
		 * Sets the categories.
		 *
		 * @param categories the new categories
		 */
		public void setCategories(List<String> categories) {
			this.categories = categories;
		}

		/**
		 * Gets the average rating.
		 *
		 * @return the average rating
		 */
		public Double getAverageRating() {
			return averageRating;
		}

		/**
		 * Sets the average rating.
		 *
		 * @param averageRating the new average rating
		 */
		public void setAverageRating(Double averageRating) {
			this.averageRating = averageRating;
		}

		/**
		 * Gets the ratings count.
		 *
		 * @return the ratings count
		 */
		public Integer getRatingsCount() {
			return ratingsCount;
		}

		/**
		 * Sets the ratings count.
		 *
		 * @param ratingsCount the new ratings count
		 */
		public void setRatingsCount(Integer ratingsCount) {
			this.ratingsCount = ratingsCount;
		}

		/**
		 * Gets the content version.
		 *
		 * @return the content version
		 */
		public String getContentVersion() {
			return contentVersion;
		}

		/**
		 * Sets the content version.
		 *
		 * @param contentVersion the new content version
		 */
		public void setContentVersion(String contentVersion) {
			this.contentVersion = contentVersion;
		}

		/**
		 * Gets the image links.
		 *
		 * @return the image links
		 */
		public ImageLinks getImageLinks() {
			return imageLinks;
		}

		/**
		 * Sets the image links.
		 *
		 * @param imageLinks the new image links
		 */
		public void setImageLinks(ImageLinks imageLinks) {
			this.imageLinks = imageLinks;
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
		 * Gets the preview link.
		 *
		 * @return the preview link
		 */
		public String getPreviewLink() {
			return previewLink;
		}

		/**
		 * Sets the preview link.
		 *
		 * @param previewLink the new preview link
		 */
		public void setPreviewLink(String previewLink) {
			this.previewLink = previewLink;
		}

		/**
		 * Gets the info link.
		 *
		 * @return the info link
		 */
		public String getInfoLink() {
			return infoLink;
		}

		/**
		 * Sets the info link.
		 *
		 * @param infoLink the new info link
		 */
		public void setInfoLink(String infoLink) {
			this.infoLink = infoLink;
		}

		/**
		 * Gets the canonical volume link.
		 *
		 * @return the canonical volume link
		 */
		public String getCanonicalVolumeLink() {
			return canonicalVolumeLink;
		}

		/**
		 * Sets the canonical volume link.
		 *
		 * @param canonicalVolumeLink the new canonical volume link
		 */
		public void setCanonicalVolumeLink(String canonicalVolumeLink) {
			this.canonicalVolumeLink = canonicalVolumeLink;
		}

		/**
		 * Gets the additional properties.
		 *
		 * @return the additional properties
		 */
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		/**
		 * Sets the additional property.
		 *
		 * @param name the name
		 * @param value the value
		 */
		public void setAdditionalProperty(String name, Object value) {
			this.additionalProperties.put(name, value);
		}

	}
}
