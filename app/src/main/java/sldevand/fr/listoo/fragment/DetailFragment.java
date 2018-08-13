package sldevand.fr.listoo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sldevand.fr.listoo.R;
import sldevand.fr.listoo.model.Element;

public class DetailFragment extends Fragment {

    private static final String ARG_ELEMENT = "element";

    private Element mElement;

    private OnElementInteractionListener mListener;

    public DetailFragment() {}

    public static DetailFragment newInstance(Element element) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ELEMENT, element);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mElement = (Element) getArguments().getSerializable(ARG_ELEMENT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView nameTv = v.findViewById(R.id.detail_name);
        TextView descriptionTv = v.findViewById(R.id.detail_description);

        if(null != mElement) {

            nameTv.setText(mElement.getName());
            descriptionTv.setText(mElement.getDescription());
        }
        return v;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnElementInteractionListener) {
            mListener = (OnElementInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnElementInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnElementInteractionListener {
        void onInteraction(Uri uri);
    }
}
