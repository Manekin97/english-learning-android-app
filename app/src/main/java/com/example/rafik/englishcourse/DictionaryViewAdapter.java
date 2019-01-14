package com.example.rafik.englishcourse;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class DictionaryViewAdapter extends RecyclerView.Adapter<DictionaryViewAdapter.DictionaryViewHolder> {
    private HashMap<String, String> words;
    private Context mContext;

    public DictionaryViewAdapter(Context context, HashMap<String, String> words){
        this.words = words;
        this.mContext = context;
    }

    public void setData(HashMap<String, String> words) {
        words.forEach((key, value) -> {
            this.words.put(key, value);
        });

        notifyDataSetChanged();
    }

    public void addWord(String word, String translation){
        this.words.put(word, translation);
        notifyDataSetChanged();
    }

    private void deleteWord(String word){
        ((DictionaryActivity) mContext).onDeleteButtonClick(word);
        this.words.remove(word);
        notifyDataSetChanged();
    }

    public void replaceTranslation(String word, String newTranslastion){
        this.words.replace(word, newTranslastion);
        notifyDataSetChanged();
    }

    public static class DictionaryViewHolder extends RecyclerView.ViewHolder {

        TextView key;
        TextView value;
        ImageButton deleteButton;
        ImageButton editButton;

        public DictionaryViewHolder(View view) {
            super(view);

            key = (TextView) view.findViewById(R.id.textView);
            value = (TextView) view.findViewById(R.id.textView2);
            deleteButton = (ImageButton) view.findViewById(R.id.delete_word_button);
            editButton = (ImageButton) view.findViewById(R.id.edit_button);
        }
    }

    public DictionaryViewAdapter(HashMap<String, String> words) {
        this.words = words;
    }

    @Override
    public DictionaryViewAdapter.DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_view_item, parent, false);

        DictionaryViewHolder viewHolder = new DictionaryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DictionaryViewHolder holder, final int position) {
        holder.key.setText((String) words.keySet().toArray()[position]);
        holder.value.setText((String) words.values().toArray()[position]);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = holder.key.getText().toString();
                deleteWord(word);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = holder.key.getText().toString();

                EditWordDialog dialog = new EditWordDialog();

                Bundle args = new Bundle();
                args.putString("oldWord", word);
                dialog.setArguments(args);

                dialog.show(((DictionaryActivity)mContext).getSupportFragmentManager(), "editWordDialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
