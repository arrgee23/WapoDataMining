package trec;

import org.jsoup.Jsoup;

public class Report{
	public Report() {
		
	}
    public Report(String id, String url, String title, String author, String date, String source, String articleType,
			String content) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.author = author;
		this.date = date;
		this.source = source;
		this.articleType = articleType;
		this.content = content;
	}

	String id;
    String url;
    String title;
    String author;
    String date;
    String source;
    String articleType;
    String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if(id==null)
			this.id = "";
		else
			this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		if(url==null)
			this.url = "";
		else
			this.url = url;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		if(title==null)
			this.title = "";
		else
			this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		if(author==null)
			this.author="";
		else
			this.author = author;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		if(date==null)
			this.date = "";
		else
			this.date = date;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		if(source==null)
			this.source = "";
		else
			this.source = source;
	}
	
	public String getArticleType() {
		return articleType;
	}
	
	public void setArticleType(String articleType) {
		if(articleType == null)
			this.articleType = "";
		else
			this.articleType = articleType;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		if(content==null)
			this.content = "";
		else
			this.content = Jsoup.parse(content).text();
	}
}