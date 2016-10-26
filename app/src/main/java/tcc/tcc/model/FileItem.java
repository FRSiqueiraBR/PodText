package tcc.tcc.model;

import java.util.Date;

/**
 * Created by felip on 14/09/2016.
 */
public class FileItem {
    private String name;
    private String size;
    private String pages;
    private Date date;

    /**
     * GETTERS
     * AND
     * SETTERS
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * To String
     */
    @Override
    public String toString() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

