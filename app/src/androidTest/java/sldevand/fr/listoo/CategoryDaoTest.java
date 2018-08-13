package sldevand.fr.listoo;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import sldevand.fr.listoo.dao.CategoryDao;
import sldevand.fr.listoo.exception.DbException;
import sldevand.fr.listoo.model.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {
    private static CategoryDao dao;

    @BeforeClass
    public static void beforeClass() {

        Context appContext = InstrumentationRegistry.getTargetContext();
        dao = CategoryDao.getInstance(appContext);

    }

    @Test
    public void deleteAll() throws Exception {

        try {
             dao.deleteAll();
            Integer second = dao.count();
            assertTrue("count > 0 : " + second, second == 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertCategory() throws Exception {
        Category cat = new Category("r", "pour le test");
        try {
            Long nb = dao.insert(cat);
            assertTrue("An error occured on insertion : " + nb, nb != -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findCategoryByName() throws Exception {
        Category expected = new Category("test", "pour le test");
        Category notExpected = new Category("test2", "pour le test");
        try {
            Category cat = dao.findByName("test");
            assertEquals(expected, cat);
            assertNotEquals(notExpected, cat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
