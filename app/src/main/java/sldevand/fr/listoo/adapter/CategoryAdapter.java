package sldevand.fr.listoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sldevand.fr.listoo.R;
import sldevand.fr.listoo.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static final String TAG = CategoryAdapter.class.getSimpleName();
    private OnCategoryChoosedListener onCategoryChoosedListener;
    private List<Category> mDataset;

    public CategoryAdapter(List<Category> dataset) {
        mDataset = dataset;
    }

    public void setOnCategoryChoosedListener(OnCategoryChoosedListener listener) {
        this.onCategoryChoosedListener = listener;
    }

    public void addCategory(Category category) {
        mDataset.add(category);
        notifyDataSetChanged();
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
        void onCategoryChoosed(String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView mImageView;
        private TextView mTextView;

        private ViewHolder(View v) {
            super(v);

            v.setOnClickListener((view) -> {
                Category cat = mDataset.get(getAdapterPosition());
                String name = cat.getName();
                if (null != onCategoryChoosedListener) {
                    onCategoryChoosedListener.onCategoryChoosed(name);
                }
            });

            mImageView = v.findViewById(R.id.category_image);
            mTextView = v.findViewById(R.id.category_title);
        }

        protected void setData(int position) {
            Category category = mDataset.get(position);
            mTextView.setText(category.getName());

            if (null != category.getUri() && !category.getUri().isEmpty()) {
                loadImage(category.getUri());
            }
        }

        private void loadImage(String uri){
            Picasso.get().load(uri)
                    .placeholder(android.R.drawable.gallery_thumb)
                    .error(android.R.drawable.ic_dialog_alert)
                    .fit()
                    .centerInside()
                    .into(mImageView);
        }
    }
}
