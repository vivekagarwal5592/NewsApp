package vivz.newsapp.Model;

/**
 * Created by user on 22-07-2017.
 */

public class NewsDetails {

    private int id;
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private String url;

    public NewsDetails(String title, int id, String author, String description, String imageUrl, String url) {
        this.title = title;
        this.id = id;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public NewsDetails(String title, String author, String description, String imageUrl, String url) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}