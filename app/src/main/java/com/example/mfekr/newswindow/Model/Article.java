package com.example.mfekr.newswindow.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


@Entity(tableName = "favoritesArticles")
public class Article implements Parcelable
{

//    @ColumnInfo(name="source")
//    private Source source;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    private int id;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "urlToImage")
    private String urlToImage;
    @ColumnInfo(name = "date")
    private String publishedAt;
    @ColumnInfo(name = "content")
    private String content;
    public final static Parcelable.Creator<Article> CREATOR = new Creator<Article>() {


        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return (new Article[size]);
        }

    };

    protected Article(Parcel in) {
        //this.source = ((Source) in.readValue((Source.class.getClassLoader())));
        this.id = in.readInt();
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.urlToImage = ((String) in.readValue((String.class.getClassLoader())));
        this.publishedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Article() {
    }

    /**
     *
     * @param content
     * @param publishedAt
     * @param author
     * @param urlToImage
     * @param title
     * @param description
     * @param url
     */
//    @Ignore
//    public Article(String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
//        super();
//        //this.source = source;
//        this.author = author;
//        this.title = title;
//        this.description = description;
//        this.url = url;
//        this.urlToImage = urlToImage;
//        this.publishedAt = publishedAt;
//        this.content = content;
//    }

    public Article(int id, Source source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        super();
        this.id = id;
        //this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


//    public Source getSource() {
//        return source;
//    }
//
//
//    public void setSource(Source source) {
//        this.source = source;
//    }


    public String getAuthor() {
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
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


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getUrlToImage() {
        return urlToImage;
    }


    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }


    public String getPublishedAt() {
        return publishedAt;
    }


    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeValue(source);
        dest.writeInt(id);
        dest.writeValue(author);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(url);
        dest.writeValue(urlToImage);
        dest.writeValue(publishedAt);
        dest.writeValue(content);
    }

    public int describeContents() {
        return 0;
    }

}
