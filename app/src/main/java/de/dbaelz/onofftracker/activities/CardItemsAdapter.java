/*
 * Copyright 2015 Daniel Bälz
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

package de.dbaelz.onofftracker.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.dbaelz.onofftracker.R;
import de.dbaelz.onofftracker.models.CardItem;

class CardItemsAdapter extends RecyclerView.Adapter<CardItemsAdapter.ViewHolder> {
    private List<CardItem> items;

    public CardItemsAdapter(List<CardItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_action, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardItem item = items.get(position);
        holder.title.setText(item.title);

        holder.screenOn.setText(String.format(holder.screenOn.getContext().getString(R.string.cardview_screenon), item.screenOn));
        holder.screenOff.setText(String.format(holder.screenOff.getContext().getString(R.string.cardview_screenon), item.screenOff));
        holder.unlocked.setText(String.format(holder.unlocked.getContext().getString(R.string.cardview_screenon), item.unlocked));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView screenOn;
        public TextView screenOff;
        public TextView unlocked;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cardview_title);
            screenOn = (TextView) itemView.findViewById(R.id.cardview_screenon);
            screenOff = (TextView) itemView.findViewById(R.id.cardview_screenoff);
            unlocked = (TextView) itemView.findViewById(R.id.cardview_unlocked);
        }
    }
}
