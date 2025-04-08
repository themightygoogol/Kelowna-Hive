package com.example.kelownahiveapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kelownahiveapp.R;
import com.example.kelownahiveapp.category.CategoryPageActivity;

public class SportsFragment extends Fragment {

    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnOpenCategory = view.findViewById(R.id.btnOpenCategoryPage);
        btnOpenCategory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryPageActivity.class);
            startActivity(intent);
        });
    }
}
