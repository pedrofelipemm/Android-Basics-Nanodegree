package study.pmoreira.project4.model;

public class Artist {

    private String name;
    private String description;

    public Artist(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
