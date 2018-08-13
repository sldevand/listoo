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
import sldevand.fr.listoo.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private OnCategoryChoosedListener onCategoryChoosedListener;
    private List<Category> mDataset;

    public CategoryAdapter(List<Category> dataset) {
        mDataset = dataset;
    }

    public void setOnCategoryChoosedListener(OnCategoryChoosedListener listener) {
        this.onCategoryChoosedListener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnCategoryChoosedListener {
        void onCategoryChoosed(Integer position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView mImageView;
        private TextView mTextView;

        private ViewHolder(View v) {
            super(v);

            v.setOnClickListener((view) -> {
                Integer id = mDataset.get(getAdapterPosition()).getId();
                if (null != onCategoryChoosedListener) {
                    onCategoryChoosedListener.onCategoryChoosed(id);
                }
            });

            mImageView = v.findViewById(R.id.category_image);
            mTextView = v.findViewById(R.id.category_title);
        }

        protected void setData(int position) {
            mTextView.setText(mDataset.get(position).getName());
        }
    }
}
