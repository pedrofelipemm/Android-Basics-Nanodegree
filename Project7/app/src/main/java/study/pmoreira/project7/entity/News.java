package study.pmoreira.project7.entity;

public class News {

    private String title;
    private String section;
    private long date;
    private String webUrl;

    public News(String title, String section, long date, String webUrl) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public long getDate() {
        return date;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
