package com.lexie.dataapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Random;


public class DataSetAdapter extends RecyclerView.Adapter<DataSetAdapter.MyViewHolder> {
    private Random random;

    public DataSetAdapter(int seed) {
        this.random = new Random(seed);
    }



    @Override
    public int getItemViewType(final int position) {
        return R.layout.data_list_layout;
    }

   /* private Context context;
    private List<DataSetModel> dataSetList;

    public DataSetAdapter(Context context, List<DataSetModel> dataSetList) {
        this.context = context;
        this.dataSetList = dataSetList;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  DataSetAdapter.MyViewHolder holder, int position) {
        holder.getView().setText(String.valueOf(random.nextInt()));

    }

    @Override
    public int getItemCount() {
        return 100;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView view;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.randomText);
        }

        public TextView getView() {
            return view;
        }


    }
}

