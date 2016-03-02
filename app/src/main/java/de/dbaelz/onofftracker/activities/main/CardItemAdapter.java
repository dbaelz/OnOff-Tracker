/*
 * Copyright 2016 Daniel Bälz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dbaelz.onofftracker.activities.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.activities.chart.ChartActivity;
import de.dbaelz.onofftracker.models.ActionsInterval;

class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.ViewHolder> implements CardItemListener {
    private List<ActionsInterval> items;
    private Context context;
    private DateTimeFormatter fmt = DateTimeFormat.shortDate();

    public CardItemAdapter(List<ActionsInterval> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_action, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActionsInterval item = items.get(position);
        String title = item.title;
        if (DateTimeComparator.getDateOnlyInstance().compare(item.startDate, item.endDate) != 0) {
            title = String.format(holder.title.getContext().getString(R.string.cardview_title),
                    item.title,
                    fmt.print(item.startDate),
                    fmt.print(item.endDate));

        }
        holder.title.setText(title);
        holder.screenOn.setText(String.format(holder.screenOn.getContext().getString(R.string.cardview_screenon), item.screenOn));
        holder.screenOff.setText(String.format(holder.screenOff.getContext().getString(R.string.cardview_screenoff), item.screenOff));
        holder.unlocked.setText(String.format(holder.unlocked.getContext().getString(R.string.cardview_unlocked), item.unlocked));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(context, ChartActivity.class);
        intent.putExtra(ChartActivity.START_DATE, items.get(position).startDate);
        intent.putExtra(ChartActivity.END_DATE, items.get(position).endDate);
        context.startActivity(intent);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView screenOn;
        public TextView screenOff;
        public TextView unlocked;

        public ViewHolder(View itemView, final CardItemListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });
            title = (TextView) itemView.findViewById(R.id.cardview_title);
            screenOn = (TextView) itemView.findViewById(R.id.cardview_screenon);
            screenOff = (TextView) itemView.findViewById(R.id.cardview_screenoff);
            unlocked = (TextView) itemView.findViewById(R.id.cardview_unlocked);
        }
    }
}
