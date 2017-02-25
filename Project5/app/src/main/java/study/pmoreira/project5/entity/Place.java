package study.pmoreira.project5.entity;

public class Place {

    private String name;
    private String description;

    public Place(String name, String description) {
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
