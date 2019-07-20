package com.androidtest.ui.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidtest.data.DataManager;
import com.androidtest.data.Interface.IDataManager;
import com.androidtest.ui.Adapter.ListAdapter;
import com.androidtest.data.Model.MovieItem;
import com.androidtest.R;
import com.androidtest.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviesFragment extends Fragment implements IDataManager, ListAdapter.ListAdapterInterface {

    private DataManager dataManager;

    private RecyclerView recyclerView;
    public ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MovieItem> adapterList;
    private ProgressDialog progressBar;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataManager = new DataManager();
        dataManager.init(getContext());
        dataManager.registerEventListener(this);
        adapterList = new ArrayList<>();
        adapter = new ListAdapter(getContext(), this);
        progressBar = new ProgressDialog(getContext());
        progressBar.setMessage("Loading...");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, null);

        recyclerView = view.findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        progressBar.show();

        dataManager.requestItems(Constants.MOVIES_FRAGMENT);

        return view;
    }

    @Override
    public void onDataManagerEvent(String eventType, Object... args) {

        switch (eventType)
        {
            case Constants.ON_SUCCESS:

                if(progressBar != null && progressBar.isShowing())
                    progressBar.dismiss();


                adapterList.addAll((ArrayList<MovieItem>) args[0]);

                adapter.addAll(adapterList);

                break;

            case Constants.ON_FAILURE:

                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();

                break;
        }

    }

    @Override
    public void onLongItemClick(String path) {


        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        ImageView image = dialog.findViewById(R.id.dialogImageView);
        Picasso.get().load(path).error(R.drawable.generic_movie).placeholder(R.drawable.generic_movie).into(image);
        dialog.show();


    }

    @Override
    public void loadRemainingItems() {

        if(dataManager != null)
            dataManager.loadItems(Constants.MOVIES_FRAGMENT);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(progressBar != null)
            progressBar.dismiss();
        progressBar = null;

        if(dataManager != null)
            dataManager.destroy();
        dataManager = null;

        if(adapter != null)
            adapter.destroy();
        adapter = null;

        if(adapterList != null)
            adapterList.clear();
        adapterList = null;

        recyclerView = null;
        layoutManager = null;
    }
}
