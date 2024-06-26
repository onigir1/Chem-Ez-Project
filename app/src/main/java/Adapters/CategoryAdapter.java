package Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chemezproject1.DbQuery;
import com.example.chemezproject1.R;
import com.example.chemezproject1.TestActivity;

import java.util.List;

import Models.CategoryModel;

public class CategoryAdapter extends BaseAdapter {

    private List<CategoryModel> cat_list;

    public CategoryAdapter(List<CategoryModel> cat_list) {
        this.cat_list = cat_list;
    }

    @Override
    public int getCount() {
        return cat_list.size();
    }

    @Override
    public Object getItem(int i) {
        return cat_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View myView;

        if (view == null) {
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layout, viewGroup, false);
        } else {
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbQuery.g_selected_cat_index = i;
                Intent intent = new Intent(v.getContext(), TestActivity.class);
                intent.putExtra("CAT_INDEX", i);
                v.getContext().startActivity(intent);
            }
        });

        TextView catName = myView.findViewById(R.id.catName);
        TextView noOfTests = myView.findViewById(R.id.no_of_tests);
        ImageView catImage = myView.findViewById(R.id.catImage);

        CategoryModel category = cat_list.get(i);
        catName.setText(category.getName());
        noOfTests.setText(String.valueOf(category.getNoOfTests()) + " Tests");
        catImage.setImageResource(category.getImageResId());

        return myView;
    }
}
