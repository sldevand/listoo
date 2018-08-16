package sldevand.fr.listoo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import sldevand.fr.listoo.R;
import sldevand.fr.listoo.adapter.CategoryAdapter;
import sldevand.fr.listoo.model.Category;
import sldevand.fr.listoo.util.Tools;

public class CategoriesFragment extends Fragment implements AddCategoryFragment.NoticeDialogListener {
    private static final String TAG = CategoriesFragment.class.getSimpleName();
    private static final String CATEGORIES_LIST_PARAM = "Categories";
    private List<Category> mCategories;
    private OnCategorySelectionListener mListener;
    private CategoryAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public CategoriesFragment() {
    }

    public static CategoriesFragment newInstance(ArrayList<Category> categories) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORIES_LIST_PARAM, categories);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategories = new ArrayList<>();
        if (null != this.getArguments()) {
            mCategories = (List<Category>) this.getArguments().getSerializable(CATEGORIES_LIST_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        initRecyclerView(v);
        initFabAdd(v);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategorySelectionListener) {
            mListener = (OnCategorySelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategorySelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void initRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.categories_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CategoryAdapter(mCategories);
        mAdapter.setOnCategoryChoosedListener(name -> mListener.onCategorySelected(name));

        mRecyclerView.setAdapter(mAdapter);
    }

    public void initFabAdd(View v) {
        FloatingActionButton fab_add = v.findViewById(R.id.categories_add_fab);
        fab_add.setOnClickListener(view -> addCategoryDialog());
    }

    public void addCategoryDialog() {
        AddCategoryFragment dialog = new AddCategoryFragment();
        dialog.setNoticeDialogListener(this);
        dialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "AddCategoryFragment");

    }

    @Override
    public void onDialogPositiveClick(Category category) {


        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.insert(category);
            realm.commitTransaction();
            mAdapter.addCategory(category);
        } catch (RuntimeException e) {
            e.printStackTrace();
            Tools.longSnackbar(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), e.getMessage());
        }
    }

    public interface OnCategorySelectionListener {
        void onCategorySelected(String name);
    }
}
