package study.pmoreira.miwok;

public class Word {

    private String defaultTransaltion;
    private String miwokTransaltion;

    public Word(String defaultTransaltion, String miwokTransaltion) {
        this.defaultTransaltion = defaultTransaltion;
        this.miwokTransaltion = miwokTransaltion;
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
}
