package com.example.user.exchangeratesassignment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.engine.api.apimodel.Item;
import com.example.user.exchangeratesassignment.R;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class RatesListAdapter extends RecyclerView.Adapter<RatesListAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Item> itemList;
    private List<Item> itemListFiltered;
    private ItemClickListener listener;
    private String currencyType;
    private String cashType;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView distance;
        public TextView sell;
        public TextView buy;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            distance = view.findViewById(R.id.distance);
            sell = view.findViewById(R.id.sell);
            buy = view.findViewById(R.id.buy);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected item and view in callback
                    listener.onContactSelected(itemListFiltered.get(getAdapterPosition()), view);
                }
            });
        }
    }


    public RatesListAdapter(Context context, List<Item> items, String currencyType,
                            String cashType, ItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.itemList = items;
        this.currencyType = currencyType;
        this.cashType = cashType;
        this.itemListFiltered = items;
    }

    public void configureCashType(String cashType) {
        this.cashType = cashType;
        notifyDataSetChanged();
    }


    public void configureCurrencyType(String currencyType) {
        this.currencyType = currencyType;
        notifyDataSetChanged();
    }

    public void setData(List<Item> items) {
        this.itemList.clear();
        this.itemListFiltered.clear();
        this.itemList.addAll(items);
        this.itemListFiltered.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rates_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Item item = itemListFiltered.get(position);
        holder.title.setText(item.getTitle());
        holder.distance.setText(item.getTitle());
        JsonElement sell = item.getCurrency(currencyType, cashType, Item.SELL);
        if (sell != null) {
            holder.sell.setText(String.valueOf(sell));
        } else {
            holder.sell.setText("__");
        }
        JsonElement buy = item.getCurrency(currencyType, cashType, Item.BUY);
        if (buy != null) {
            holder.buy.setText(String.valueOf(buy));
        } else {
            holder.buy.setText("__");

        }
    }

    @Override
    public int getItemCount() {
        return itemListFiltered.size();
    }


    // overrided for filtering but hasn't any usage
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemListFiltered = itemList;
                } else {
                    List<Item> filteredList = new ArrayList<>();
                    for (Item row : itemList) {
                        filteredList.add(row);

                    }

                    itemListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemListFiltered = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ItemClickListener {
        void onContactSelected(Item item, View view);
    }
}