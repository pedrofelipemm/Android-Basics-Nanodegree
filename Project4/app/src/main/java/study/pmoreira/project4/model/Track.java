package study.pmoreira.project4.model;

public class Track {

    private String name;
    private int length;
    private Artist artist;
    private int coverId;
    private int coverThumbId;

    public Track(String name, int length, Artist artist, int coverId, int coverThumbId) {
        this.name = name;
        this.length = length;
        this.artist = artist;
        this.coverId = coverId;
        this.coverThumbId = coverThumbId;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Artist getArtist() {
        return artist;
    }

    public int getCoverId() {
        return coverId;
    }

    public int getCoverThumbId() {
        return coverThumbId;
    }
}
