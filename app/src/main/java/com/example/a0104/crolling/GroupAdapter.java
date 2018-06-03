package com.example.a0104.crolling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>{
    private Context context;
    private List<GroupModel> groupLists;

    public GroupAdapter(Context context) {
        this.context = context;
        this.groupLists = new ArrayList<>();
    }

    public void addGroup(List<GroupModel> groupLists){
        this.groupLists = groupLists;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel groupModel = groupLists.get(position);
        holder.groupNameView.setText(groupModel.getGroupName());
        holder.keyView.setText(groupModel.getKey());
    }

    @Override
    public int getItemCount() {
        if(groupLists != null){
            return groupLists.size();
        }
        return 0;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{
        private TextView groupNameView, keyView;

        public GroupViewHolder(View itemView){
            super(itemView);

            groupNameView = itemView.findViewById(R.id.groupNameView);
            keyView = itemView.findViewById(R.id.keyView);
        }
    }
}
