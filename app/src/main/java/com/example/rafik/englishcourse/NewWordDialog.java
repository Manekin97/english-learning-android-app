package com.example.rafik.englishcourse;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NewWordDialog extends AppCompatDialogFragment {
    private EditText editTextWord;
    private EditText editTextTranslation;
    private NewWordDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_word_dialog, null);

        builder.setView(view)
                .setTitle("Dodaj nowe słowo.")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String word = editTextWord.getText().toString();
                        String translation = editTextTranslation.getText().toString();

                        listener.onAddButtonClick(word, translation);
                    }
                });

        editTextWord = view.findViewById(R.id.new_word);
        editTextTranslation = view.findViewById(R.id.new_word_translation);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            this.listener = (NewWordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement NewWordDialogListener");
        }
    }

    public interface NewWordDialogListener {
        void onAddButtonClick(String word, String translation);
    }
}
