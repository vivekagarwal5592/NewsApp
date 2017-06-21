package vivz.newsapp;

/**
 * Created by user on 16-06-2017.
 */

public class NewsDetails {

    String author, url, title, description;

    public NewsDetails(String author, String url, String title, String description) {
        this.author = author;
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
