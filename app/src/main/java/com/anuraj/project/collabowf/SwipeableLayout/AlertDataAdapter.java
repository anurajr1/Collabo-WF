/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 11:48 AM
 *
 */

package com.anuraj.project.collabowf.SwipeableLayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;

import java.util.List;

public class AlertDataAdapter extends RecyclerView.Adapter<AlertDataAdapter.AlertViewHolder> {
    public List<Alert> alerts;

    public class AlertViewHolder extends RecyclerView.ViewHolder {
        private TextView name, nationality, club, rating, age;

        public AlertViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            nationality = (TextView) view.findViewById(R.id.nationality);
            club = (TextView) view.findViewById(R.id.club);
//            rating = (TextView) view.findViewById(R.id.rating);
//            age = (TextView) view.findViewById(R.id.age);
        }
    }

    public AlertDataAdapter(List<Alert> alerts) {
        this.alerts = alerts;
    }

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_row, parent, false);

        return new AlertViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlertViewHolder holder, int position) {
        Alert alert = alerts.get(position);
        holder.name.setText(alert.getName());
        holder.nationality.setText(alert.getNationality());
        holder.club.setText(alert.getClub());
//        holder.rating.setText(alert.getRating().toString());
//        holder.age.setText(alert.getAge().toString());
    }

    @Override
    public int getItemCount() {
        return alerts.size();
    }
}
