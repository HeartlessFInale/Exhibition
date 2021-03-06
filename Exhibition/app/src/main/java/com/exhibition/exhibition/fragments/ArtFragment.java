package com.exhibition.exhibition.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exhibition.exhibition.R;
import com.exhibition.exhibition.adapters.SearchAdapter;
import com.exhibition.exhibition.models.Art;
import com.exhibition.exhibition.models.ArtResult;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtFragment extends Fragment {
    SearchAdapter searchAdapter;
    List<Art> artList = new ArrayList<>();
    RecyclerView recyclerView;

    public ArtFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        searchAdapter = new SearchAdapter(getActivity());
        searchAdapter.setArtResults(artList);
    }

    public void updateSearch(List<Art> arts) {
        if (searchAdapter != null) {
            artList.clear();
            artList.addAll(arts);
            searchAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_art, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchAdapter);
        return view;
    }

}
