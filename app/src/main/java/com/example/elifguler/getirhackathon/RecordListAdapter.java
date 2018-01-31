package com.example.elifguler.getirhackathon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by elifguler on 31.01.2018.
 */

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordListAdapterViewHolder> {
    private List<Record> recordData;

    public void setRecordData(List<Record> recordData) {
        this.recordData = recordData;
        notifyDataSetChanged();
    }

    @Override
    public RecordListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.record_list_item, parent, false);

        return new RecordListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordListAdapterViewHolder holder, int position) {
        Record record = recordData.get(position);
        if (record != null) {
            holder.idTextView.setText(record.id.id);
            holder.keyTextView.setText(record.id.key);
            holder.valueTextView.setText(record.id.value);
            holder.createdAtTextView.setText(record.id.createdAt);
            holder.totalCountTextView.setText("" + record.totalCount);
        }
    }

    @Override
    public int getItemCount() {
        if (recordData == null) {
            return 0;
        }

        return recordData.size();
    }

    public class RecordListAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView keyTextView;
        TextView valueTextView;
        TextView createdAtTextView;
        TextView totalCountTextView;


        public RecordListAdapterViewHolder(View view) {
            super(view);

            idTextView = view.findViewById(R.id.id_tv);
            keyTextView = view.findViewById(R.id.key_tv);
            valueTextView = view.findViewById(R.id.value_tv);
            createdAtTextView = view.findViewById(R.id.createdAt_tv);
            totalCountTextView = view.findViewById(R.id.totalCount_tv);
        }
    }
}

