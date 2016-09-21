package tcc.tcc.model;

/**
 * Created by felip on 14/09/2016.
 */
public class FileItem {
    private String name;
    private String size;
    private String pages;

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
        return name + " - " + "P\u00e1ginas: " + pages;
    }
}

