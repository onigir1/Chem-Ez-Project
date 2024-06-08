package com.example.chemezproject1;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MaterialFragment extends Fragment {

    private TextView material1B;
    private TextView material2B;
    private TextView material3B;

    private Dialog progressDialog;
    private TextView dialogText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_material, container, false);

        // Setup toolbar title
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Learning Materials");

        // Initialize progress dialog
        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading....");

        // Initialize views
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        // Find TextViews by their IDs
        material1B = view.findViewById(R.id.material1B);
        material2B = view.findViewById(R.id.material2B);
        material3B = view.findViewById(R.id.material3B);

        // Set click listeners for each TextView
        material1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for material1B
                // Example: navigate to another fragment, perform an action, etc.
            }
        });

        material2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for material2B
                // Example: navigate to another fragment, perform an action, etc.
            }
        });

        material3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for material3B
                // Example: navigate to another fragment, perform an action, etc.
            }
        });
    }


}
