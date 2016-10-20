package tcc.tcc.model;

import java.util.List;

/**
 * Created by FRSiqueira on 07/10/2016.
 */

public class Folders {
    private String path;
    private String name;
    private List<AudioBook> listAudioBooks;

    /**
     * GETTERS
     * AND
     * SETTERS
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AudioBook> getListAudioBooks() {
        return listAudioBooks;
    }

    public void setListAudioBooks(List<AudioBook> listAudioBooks) {
        this.listAudioBooks = listAudioBooks;
    }

    @Override
    public String toString() {
        return name;
    }
}
