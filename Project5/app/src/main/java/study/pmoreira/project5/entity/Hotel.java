package study.pmoreira.project5.entity;

public class Hotel extends Place {

    private int imageId;

    public Hotel(String name, String description, int imageId) {
        super(name, description);
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }
}
