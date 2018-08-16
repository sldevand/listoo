package sldevand.fr.listoo;

import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.realm.Realm;
import sldevand.fr.listoo.model.Category;
import sldevand.fr.listoo.model.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryDaoTest {


    private static Realm realm;


    @BeforeClass
    public static void beforeClass() {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


    @Test
    public void stage1_insertOne() {
        Category equides = new Category("equides", "Quatre pattes longue criniere");

        realm.beginTransaction();
        realm.insert(new Element("cheval", equides, "Quatre pattes longue criniere, hennit"));
        realm.commitTransaction();

        assertNotNull(equides);


    }

    @Test
    public void stage2_previousInsertIsFetchable() {
        Element elt = realm.where(Element.class).equalTo("name", "cheval").findFirst();
        assertNotNull(elt);
        assertEquals("cheval", elt.getName());
    }

    @AfterClass
    public static void tearDown(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
}
