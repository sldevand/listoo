package sldevand.fr.listoo.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Element extends RealmObject implements Serializable {

    @PrimaryKey
    private String name;
    private String description;
    private Category category;
    private String uri;


    public Element() {
        super();
    }

    public Element(String name, Category category) {
        this();
        this.name = name;
        this.category = category;
    }

    public Element(String name, Category category, String description) {
        this(name, category);
        this.description = description;
    }

    public Element(String name, Category category, String description,String uri) {
        this(name, category,description);
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
