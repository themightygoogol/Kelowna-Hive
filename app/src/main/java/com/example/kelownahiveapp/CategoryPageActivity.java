package com.example.kelownahiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kelownahiveapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryPageActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryItem> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("Events", R.drawable.ic_event));
        categoryList.add(new CategoryItem("Services", R.drawable.ic_services));
        categoryList.add(new CategoryItem("Lost & Found", R.drawable.ic_lostfound));
        categoryList.add(new CategoryItem("Miscellaneous", R.drawable.ic_misc));

        categoryAdapter = new CategoryAdapter(categoryList, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onCategoryClick(String categoryName) {
        Toast.makeText(this, "Selected: " + categoryName, Toast.LENGTH_SHORT).show();
    }
}
