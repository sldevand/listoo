package sldevand.fr.listoo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sldevand.fr.listoo.R;
import sldevand.fr.listoo.adapter.CategoryAdapter;
import sldevand.fr.listoo.model.Category;

public class CategoriesFragment extends Fragment {
    private static final String TAG = "CategoriesFragment";
    private static final String CATEGORIES_LIST_PARAM = "Categories";
    private List<Category> mCategories;
    private OnCategorySelectionListener mListener;

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
        RecyclerView mRecyclerView = v.findViewById(R.id.categories_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        CategoryAdapter mAdapter = new CategoryAdapter(mCategories);

        mAdapter.setOnCategoryChoosedListener(new CategoryAdapter.OnCategoryChoosedListener() {

            @Override
            public void onCategoryChoosed(String name) {
                mListener.onCategorySelected(name);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
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

    public interface OnCategorySelectionListener {
        void onCategorySelected(String name);
    }


}
