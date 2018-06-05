package com.example.a0104.crolling;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> implements OnGroupClickListener{
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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_layout, parent,false);
        return new GroupViewHolder(view,this);
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

    @Override
    public void onGroupClicked(int position) {
        GroupModel groupModel = groupLists.get(position);
        Intent intent = new Intent(context,GroupTable.class);
        intent.putExtra("groupName", groupModel.getGroupName());
        intent.putExtra("key", groupModel.getKey());
        context.startActivity(intent);
    }

    class GroupViewHolder extends RecyclerView.ViewHolder{
        private TextView groupNameView, keyView;

        public GroupViewHolder(View itemView, final OnGroupClickListener onGroupClickListener){
            super(itemView);
            groupNameView = itemView.findViewById(R.id.groupNameView);
            keyView = itemView.findViewById(R.id.keyView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        onGroupClickListener.onGroupClicked(position);
                    }
                }
            });
        }
    }
}
