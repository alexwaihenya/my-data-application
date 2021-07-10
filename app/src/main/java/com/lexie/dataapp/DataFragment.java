package com.lexie.dataapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DataFragment extends Fragment {

    private RecyclerView recyclerView;
    private DataSetAdapter dataSetAdapter;



    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_data, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dataSetAdapter = new DataSetAdapter(100);
        recyclerView.setAdapter(dataSetAdapter);

        return view;
    }
}