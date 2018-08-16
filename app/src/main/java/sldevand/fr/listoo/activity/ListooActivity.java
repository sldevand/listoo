package sldevand.fr.listoo.activity;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import sldevand.fr.listoo.R;
import sldevand.fr.listoo.fragment.CategoriesFragment;
import sldevand.fr.listoo.fragment.DetailFragment;
import sldevand.fr.listoo.fragment.ElementsFragment;
import sldevand.fr.listoo.model.Category;
import sldevand.fr.listoo.model.Element;
import sldevand.fr.listoo.util.PermissionsTool;
import sldevand.fr.listoo.util.Tools;


public class ListooActivity extends AppCompatActivity implements CategoriesFragment.OnCategorySelectionListener, ElementsFragment.OnElementSelectionListener, DetailFragment.OnElementInteractionListener {

    public final String TAG = "ListooActivity";
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listoo);
        //Permissions
        List<String> perms = new ArrayList<>();
        perms.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        PermissionsTool.requestPermissions(this, perms);


        realm = Realm.getDefaultInstance();
         mockDataRealm();

        if (null != findViewById(R.id.fragment_container)) {
            if (null != savedInstanceState) return;
            initCategoryFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsTool.permissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();

    }

    @Override
    public void onCategorySelected(String name) {
        initElementFragment(name);
    }

    @Override
    public void onElementSelected(String name) {
        initDetailFragment(name);
    }


    public void initCategoryFragment() {
        try {
            RealmResults<Category> elts = realm.where(Category.class).findAll();
            ArrayList<Category> categories = (ArrayList<Category>) realm.copyFromRealm(elts);

            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(categories);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, categoriesFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .commit();
        } catch (RealmException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), getString(R.string.cat_list_not_found));
        }
    }

    private void initElementFragment(String name) {
        try {
            RealmResults<Element> elts = realm.where(Element.class).equalTo("category.name", name).findAll();
            ArrayList<Element> elements = (ArrayList<Element>) realm.copyFromRealm(elts);

            ElementsFragment elementsFragment = ElementsFragment.newInstance(elements);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, elementsFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .commit();
        } catch (RealmException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), getString(R.string.elt_list_not_found));
        }
    }

    public void initDetailFragment(String name) {
        try {
            Element elt = realm.where(Element.class).equalTo("name", name).findFirst();
            assert elt != null;
            Element element = realm.copyFromRealm(elt);

            DetailFragment detailFragment = DetailFragment.newInstance(element);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .addToBackStack(null)
                    .commit();
        } catch (RealmException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), getString(R.string.elt_not_found));
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            realm.close();
            finish();
        }
    }

    public void mockDataRealm() {
        try {
            realm.beginTransaction();
            realm.deleteAll();
            Category java = new Category("Java", "Langage Polyvalent, fortement typé", "http://icons.iconarchive.com/icons/alecive/flatwoken/512/Apps-Java-icon.png");
            Category php = new Category("PHP", "Langage Web Back end", "https://byterevel.com/wp-content/uploads/2011/07/PHP-icon.jpeg");

            Element javaEE = new Element("JavaEE", java, "Pour créer des applications Java dans de grosses infrastructures");
            Element spring = new Element("Spring", java, "Framework Java à la mode en ce moment, facile à prendre en main");
            Element android = new Element("Android SDK", java, "Pour créer des applications Android");
            Element zend = new Element("Zend Framework", php, "Framework pour créer des sites de grande envergure (banques...)");
            Element symfony = new Element("Symfony", php, "Framework pour créer des sites de moyenne envergure");
            Element laravel = new Element("Laravel", php, "Framework pour créer des sites de petite envergure");
            List<Element> elements = Arrays.asList(javaEE, spring, android, zend, symfony, laravel);
            realm.insert(elements);

            realm.commitTransaction();

        } catch (RealmException e) {
            Tools.longSnackbar(findViewById(android.R.id.content), "exception on Realm");
            e.printStackTrace();
        }
    }


    @Override
    public void onInteraction(Uri uri) {

    }
}
