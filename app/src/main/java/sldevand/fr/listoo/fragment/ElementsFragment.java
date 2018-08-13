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
import sldevand.fr.listoo.adapter.ElementAdapter;
import sldevand.fr.listoo.model.Element;

public class ElementsFragment extends Fragment{
    private static final String TAG = "ElementsFragment";
    private static final String ELEMENTS_LIST_PARAM = "Elements";
    private ArrayList<Element> mElementsList;

    private OnElementSelectionListener mListener;

    public ElementsFragment() {
    }

    public static ElementsFragment newInstance(ArrayList<Element> elements) {
        ElementsFragment fragment = new ElementsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ELEMENTS_LIST_PARAM,  elements);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( null != this.getArguments()) {
            mElementsList = (ArrayList<Element>) this.getArguments().getSerializable(ELEMENTS_LIST_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_elements, container, false);
        RecyclerView mRecyclerView = v.findViewById(R.id.elements_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ElementAdapter mAdapter = new ElementAdapter(mElementsList);

        mAdapter.setOnElementChoosedListener(new ElementAdapter.OnElementChoosedListener() {
            @Override
            public void onChoosed(Integer position) {
                mListener.onElementSelected(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnElementSelectionListener) {
            mListener = (OnElementSelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnElementSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnElementSelectionListener {
        void onElementSelected(Integer id);
    }
}
