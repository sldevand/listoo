package sldevand.fr.listoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sldevand.fr.listoo.R;
import sldevand.fr.listoo.model.Element;

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ViewHolder> {

    private OnElementChoosedListener onElementChoosedListener;
    private List<Element> mDataset;

    public ElementAdapter(List<Element> dataset) {
        mDataset = dataset;
    }

    public void setOnElementChoosedListener(OnElementChoosedListener listener) {
        this.onElementChoosedListener = listener;
    }

    @NonNull
    @Override
    public ElementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_element, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ElementAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnElementChoosedListener {
        void onChoosed(Integer position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView mImageView;
        private TextView mNameView;
        private TextView mDescriptionView;

        private ViewHolder(View v) {
            super(v);

            v.setOnClickListener((view) -> {
                Integer id = mDataset.get(getAdapterPosition()).getId();
                if (null != onElementChoosedListener)
                    onElementChoosedListener.onChoosed(id);
            });

            mImageView = v.findViewById(R.id.element_image);
            mNameView = v.findViewById(R.id.element_name);
            mDescriptionView = v.findViewById(R.id.element_description);
        }

        protected void setData(int position) {
            Element elt = mDataset.get(position);
            mNameView.setText(elt.getName());
            mDescriptionView.setText(elt.getDescription());
        }
    }
}
