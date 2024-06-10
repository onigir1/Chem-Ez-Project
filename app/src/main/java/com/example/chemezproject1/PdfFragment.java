package com.example.chemezproject1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfFragment extends Fragment {

    private static final String ARG_PDF_FILE_NAME = "pdf_file_name";

    private String pdfFileName;

    public static PdfFragment newInstance(String pdfFileName) {
        PdfFragment fragment = new PdfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PDF_FILE_NAME, pdfFileName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);

        if (getArguments() != null) {
            pdfFileName = getArguments().getString(ARG_PDF_FILE_NAME);
        }

        PDFView pdfView = view.findViewById(R.id.pdfView);
        pdfView.fromAsset(pdfFileName).load();

        return view;
    }
}
