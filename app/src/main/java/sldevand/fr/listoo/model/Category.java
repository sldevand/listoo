package sldevand.fr.listoo.model;

import android.widget.TextView;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject implements Serializable {

    @PrimaryKey
    private String name;
    private String description;
    private String uri;

    public Category() {
        super();
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String description) {
        this(name);
        this.description = description;
    }

    public Category(String name, String description,String uri) {
        this(name,description);
        this.uri=uri;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
