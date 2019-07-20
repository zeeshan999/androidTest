package com.androidtest.ui.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtest.data.Model.MovieItem;
import com.androidtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<MovieItem> list;
    private  Context mContext;
    private final ListAdapterInterface listener;



    public interface ListAdapterInterface {
        void onLongItemClick(String item);
        void loadRemainingItems();
    }

    public ListAdapter(Context context,ListAdapterInterface listener) {
        list = new ArrayList<>();
        this.listener = listener;
        mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_layout, viewGroup, false);

        viewHolder = new MovieItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {


        final MovieItemViewHolder dataHolder = (MovieItemViewHolder) viewHolder;
        dataHolder.name.setText(mContext.getString(R.string.title)  + list.get(i).getOriginalTitle());
        dataHolder.overview.setText(mContext.getString(R.string.overview) + list.get(i).getOverView());
        dataHolder.releasedDate.setText(mContext.getString(R.string.release_date) + list.get(i).getReleaseDate());
        Picasso.get().load(list.get(i).getPosterPath()).error(R.drawable.generic_movie).placeholder(R.drawable.generic_movie).into(dataHolder.image);

        dataHolder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                listener.onLongItemClick(list.get(i).getPosterPath());

                return false;
            }
        });

        if(i == list.size() - 1)
            listener.loadRemainingItems();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(ArrayList<MovieItem> adapterList) {

        int perviouslyAddedItems = list.size();
        list.addAll(adapterList.subList(perviouslyAddedItems, adapterList.size()));
        notifyItemRangeChanged(perviouslyAddedItems, adapterList.size());
    }


    class MovieItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView overview;
        private TextView releasedDate;
        private ImageView image;

        public MovieItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            overview = itemView.findViewById(R.id.overView);
            releasedDate = itemView.findViewById(R.id.release_date);
            image = itemView.findViewById(R.id.itemImage);

        }

    }


    public void destroy() {

        if(list != null)
            list.clear();
        list = null;

    }

}
