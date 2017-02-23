package study.pmoreira.miwok;

public class Word {

    private static final int NO_IMAGE_PROVIDED = -1;

    private String defaultTransaltion;
    private String miwokTransaltion;
    private int imageResourceId = NO_IMAGE_PROVIDED;

    public Word(String defaultTransaltion, String miwokTransaltion) {
        this.defaultTransaltion = defaultTransaltion;
        this.miwokTransaltion = miwokTransaltion;
    }

    public Word(String defaultTransaltion, String miwokTransaltion, int imageResourceId) {
        this(defaultTransaltion, miwokTransaltion);
        this.imageResourceId = imageResourceId;
    }

    public String getDefaultTransaltion() {
        return defaultTransaltion;
    }

    public void setDefaultTransaltion(String defaultTransaltion) {
        this.defaultTransaltion = defaultTransaltion;
    }

    public String getMiwokTransaltion() {
        return miwokTransaltion;
    }

    public void setMiwokTransaltion(String miwokTransaltion) {
        this.miwokTransaltion = miwokTransaltion;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
