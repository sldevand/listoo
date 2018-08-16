package sldevand.fr.listoo.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import sldevand.fr.listoo.R;
import sldevand.fr.listoo.model.Category;

public class AddCategoryFragment extends DialogFragment {

    private static final String TAG = AddCategoryFragment.class.getSimpleName();
    private static final int PICK_IMAGE = 1;
    NoticeDialogListener mListener;
    private ImageView imageView;
    private Uri imagePath;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View v = inflater.inflate(R.layout.form_add_categories, null);

        imageViewInit(v);
        return dialogBuild(v);
    }

    public Dialog dialogBuild(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(R.string.form_add_title);
        builder.setView(v);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            mListener.onDialogPositiveClick(this.getCategory(v));
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        return builder.create();
    }

    public void setNoticeDialogListener(NoticeDialogListener listener) {
        this.mListener = listener;
    }

    private Category getCategory(View v) {
        TextView nameTv = v.findViewById(R.id.category_name_form);
        TextView descTv = v.findViewById(R.id.category_desc_form);
        String name = nameTv.getText().toString();
        String desc = descTv.getText().toString();

        if (name.isEmpty() || desc.isEmpty()) return null;
        return new Category(name, desc, imagePath.toString());

    }

    public void imageViewInit(View v){
        imageView = v.findViewById(R.id.category_image_form);
        imageView.setOnClickListener(v1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            imagePath = data.getData();
            Picasso.get().load(imagePath)
                    .placeholder(android.R.drawable.gallery_thumb)
                    .error(android.R.drawable.ic_dialog_alert)
                    .fit()
                    .centerInside()
                    .into(imageView);
        }
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(Category category);
    }
}
