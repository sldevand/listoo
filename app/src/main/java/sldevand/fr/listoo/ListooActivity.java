package sldevand.fr.listoo;

import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import sldevand.fr.listoo.dao.CategoryDao;
import sldevand.fr.listoo.dao.ElementDao;
import sldevand.fr.listoo.fragment.CategoriesFragment;
import sldevand.fr.listoo.fragment.DetailFragment;
import sldevand.fr.listoo.fragment.ElementsFragment;
import sldevand.fr.listoo.model.Category;
import sldevand.fr.listoo.model.Element;
import sldevand.fr.listoo.util.Tools;


public class ListooActivity extends AppCompatActivity implements CategoriesFragment.OnCategorySelectionListener, ElementsFragment.OnElementSelectionListener, DetailFragment.OnElementInteractionListener {

    public final String TAG = "ListooActivity";

    private CategoryDao categoryDao;
    private ElementDao elementDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listoo);
        elementDao = ElementDao.getInstance(this);
        categoryDao = CategoryDao.getInstance(this);
        //mockData();

        if (null != findViewById(R.id.fragment_container)) {
            if (null != savedInstanceState) return;
            initCategoryFragment();
        }
    }

    @Override
    public void onCategorySelected(Integer id) {
        initElementFragment(id);
    }

    @Override
    public void onElementSelected(Integer id) {
        initDetailFragment(id);
    }

    public void initCategoryFragment() {
        try {
            ArrayList<Category> categories = (ArrayList<Category>) categoryDao.findAll();
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(categories);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, categoriesFragment)
                    .addToBackStack(null)
                    .commit();
        } catch (SQLException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), getString(R.string.cat_list_not_found));
        }
    }

    private void initElementFragment(Integer id) {
        try {
            ArrayList<Element> elements = (ArrayList<Element>) elementDao.findByCategoryId(id);
            ElementsFragment elementsFragment = ElementsFragment.newInstance(elements);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, elementsFragment)
                    .addToBackStack(null)
                    .commit();
        } catch (SQLException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), getString(R.string.elt_list_not_found));
        }
    }

    public void initDetailFragment(Integer id) {
        try {
            Element element = elementDao.findById(id);
            DetailFragment detailFragment = DetailFragment.newInstance(element);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        } catch (SQLException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), getString(R.string.elt_not_found));
        }
    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    public void mockData() {
        try {

            elementDao.deleteAll();
            categoryDao.deleteAll();
            categoryDao.insert(new Category("PHP", "Langage Web Back end"));
            categoryDao.insert(new Category("Java", "Langage Polyvalent, fortement typé"));

            Category php = categoryDao.findByName("PHP");
            Element zend = new Element("Zend Framework", php.getId(), "Framework pour créer des sites de grande envergure (banques...)");
            Element symfony = new Element("Symfony", php.getId(), "Framework pour créer des sites de moyenne envergure");
            Element laravel = new Element("Laravel", php.getId(), "Framework pour créer des sites de petite envergure");
            elementDao.insert(zend);
            elementDao.insert(symfony);
            elementDao.insert(laravel);


            Category java = categoryDao.findByName("Java");
            Element javaEE = new Element("JavaEE", java.getId(), "Pour créer des applications Java dans de grosses infrastructures");
            Element spring = new Element("Spring", java.getId(), "Framework Java à la mode en ce moment, facile à prendre en main");
            Element android = new Element("Android SDK", java.getId(), "Pour créer des applications Android");
            elementDao.insert(javaEE);
            elementDao.insert(spring);
            elementDao.insert(android);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onInteraction(Uri uri) {

    }
}
