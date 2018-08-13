package sldevand.fr.listoo.model;

import android.content.ContentValues;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class Element extends AbstractModel {

    private String name;
    private String description;
    private Integer categoryId;

    public Element() {
        super();
    }

    public Element(String name, Integer categoryId) {
        this();
        this.name = name;
        this.categoryId = categoryId;
    }

    public Element(Integer id, String name, Integer categoryId) {
        this(name, categoryId);
        this.id = id;
    }

    public Element(String name, Integer categoryId, String description) {
        this(name, categoryId);
        this.description = description;
    }

    public Element(Integer id, String name, Integer categoryId, String description) {
        this(name, categoryId, description);
        this.id = id;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(name, element.name) &&
                Objects.equals(description, element.description);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(name, description);
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }

    @Override
    public ContentValues toContentValues() {

        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("description", description);
        cv.put("categoryId", categoryId);
        return cv;
    }

}
