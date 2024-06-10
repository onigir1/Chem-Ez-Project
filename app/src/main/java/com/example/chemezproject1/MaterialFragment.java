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
import com.github.barteksc.pdfviewer.PDFView;




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
        // Find PDFViews by their IDs
        PDFView material1B = view.findViewById(R.id.material1B);
        PDFView material2B = view.findViewById(R.id.material2B);
        PDFView material3B = view.findViewById(R.id.material3B);

        // Set click listeners for each PDFView
        material1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("pdftest1.pdf");
            }
        });

        material2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("pdftest2.pdf");
            }
        });

        material3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("pdftest3.pdf");
            }
        });
    }


    private void openPdf(String pdfFileName) {
        // Create a new fragment to display the PDF
        PdfFragment pdfFragment = PdfFragment.newInstance(pdfFileName);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, pdfFragment)
                .addToBackStack(null)
                .commit();
    }
}
