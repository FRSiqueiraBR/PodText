package tcc.tcc.model;

/**
 * Created by FRSiqueira on 07/10/2016.
 */

public class AudioBook {

    private String name;
    private String part;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return name;
    }
}
