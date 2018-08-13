package sldevand.fr.listoo.model;

import android.content.ContentValues;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Category extends AbstractModel{


    private String name;
    private String description;

    public Category(){
        super();
    }

    public Category(String name) {
        this();
        this.name = name;
    }

    public Category(Integer id,String name) {
        this(name);
        this.id = id;
    }


    public Category(String name, String description) {
        this(name);
        this.description = description;
    }

    public Category(Integer id, String name, String description) {
        this(name, description);
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
        Category category = (Category) o;
        return Objects.equals(name, category.name) &&
                Objects.equals(description, category.description);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public ContentValues toContentValues(){

        ContentValues cv = new ContentValues();
        cv.put("id",id);
        cv.put("name",name);
        cv.put("description",description);
        return cv;
    }


}
