/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 5/04/19 10:07 AM
 *
 */

package com.anuraj.project.collabowf.operator_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anuraj.project.collabowf.LoginActivity;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.SplashScreen;
import com.anuraj.project.collabowf.image_util.ImageLoader;
import com.anuraj.project.collabowf.operator_activity.TeamMoreDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<TeamDetails> MainImageUploadInfoList;
    ImageLoader imageLoader;

    public RecyclerViewAdapter(Context context, List<TeamDetails> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;

        imageLoader = new ImageLoader(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TeamDetails teamDetails = MainImageUploadInfoList.get(position);

        holder.TeamIdText.setText(teamDetails.getId());

        holder.TeamNameText.setText(teamDetails.getName());


        //imageLoader.DisplayImage(teamDetails.getPropic(), holder.propic);
        Glide.with(context).load(teamDetails.getPropic()).into(holder.propic);


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Get the intent to launch the specified
                    TextView oper = view.findViewById(R.id.operator_id);
                    oper.getText();
                    Intent i = new Intent(context, TeamMoreDetailsActivity.class);
                    i.putExtra("OperatorId",oper.getText());
                    context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TeamIdText;
        public TextView TeamNameText;
        public ImageView propic;

        public ViewHolder(View itemView) {

            super(itemView);

            TeamIdText = (TextView) itemView.findViewById(R.id.operator_id);

            TeamNameText = (TextView) itemView.findViewById(R.id.operator_name);

            propic = (ImageView) itemView.findViewById(R.id.pro_pic);
        }
    }
}