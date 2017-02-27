package study.pmoreira.project6.entity;

public class Book {

    private String author;
    private String title;
    private String language;

    public Book(String author, String title, String language) {
        this.author = author;
        this.title = title;
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }
}
