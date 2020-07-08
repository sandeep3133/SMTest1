package com.mudhales.smtest1.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.smtest1.MainActivity;
import com.mudhales.smtest1.R;
import com.mudhales.smtest1.adapter.CountryDescriptionAdapter;

public class ListDataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    SwipeRefreshLayout swipeContainer;
    RecyclerView rvItems;
    CountryDescriptionAdapter rowDataAdapter;
    ProgressDialog progressDialog;
    private ListDataViewModel listViewModel;

    public static ListDataFragment newInstance() {
        return new ListDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_data_fragment, container, false);
        setUpUI();
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        listViewModel = ViewModelProviders.of(this).get(ListDataViewModel.class);
//        // TODO: Use the ViewModel
//    }

    // To set up view by SM 201912111345
    private void setUpUI() {
        swipeContainer = view.findViewById(R.id.swipeContainer);
        rvItems = view.findViewById(R.id.rvItems);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getActivity().getString(R.string.Loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvItems.setLayoutManager(layoutManager);
        rvItems.setNestedScrollingEnabled(false);
        rvItems.setHasFixedSize(true);
        swipeContainer.setOnRefreshListener(this);
        initialization();
    }

    // To initialise view model by SM 201912111345
    private void initialization() {
        // View Model
        listViewModel = ViewModelProviders.of(this).get(ListDataViewModel.class);
        loadLoginData();
    }

    @Override
    public void onRefresh() {
        checkLoginData();
    }

    // To fetch data from server by SM 201912111355
    private void loadLoginData() {
        progressDialog.show();
        listViewModel.getListResponseLiveData().observe(this, response -> {
            if (response != null) {
                showMessage("Success");
                ((MainActivity)getActivity()).setActionBarTitle(response.getTitle());
                if (response.getCountryDescription() != null) {
                    if (rowDataAdapter != null)
                        rowDataAdapter.refreshList(response.getCountryDescription());
                    else {
                        rowDataAdapter = new CountryDescriptionAdapter(getActivity(), response.getCountryDescription());
                        rvItems.setAdapter(rowDataAdapter);
                    }
                }

            } else {
                showMessage("Failed. try again.");
            }

            progressDialog.cancel();
        });
    }// To fetch data from server by SM 201912111355

    private void checkLoginData() {
        swipeContainer.setRefreshing(true);
        listViewModel.getListResponseLiveData().observe(this, response -> {
            if (response != null) {
                showMessage("Success");
                ((MainActivity)getActivity()).setActionBarTitle(response.getTitle());
                if (response.getCountryDescription() != null) {
                    if (rowDataAdapter != null)
                        rowDataAdapter.refreshList(response.getCountryDescription());
                    else {
                        rowDataAdapter = new CountryDescriptionAdapter(getActivity(), response.getCountryDescription());
                        rvItems.setAdapter(rowDataAdapter);
                    }
                }

            } else {
                showMessage("Failed. try again.");
            }

            swipeContainer.setRefreshing(false);
        });
    }
    private void showMessage(String strMessage){
        Snackbar snackbar = Snackbar
                .make(view, strMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}