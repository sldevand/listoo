package sldevand.fr.listoo.model;

import android.content.ContentValues;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable {

    protected Integer id;

    public AbstractModel(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract ContentValues toContentValues();

}
