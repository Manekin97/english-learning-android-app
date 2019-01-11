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

public class EditWordDialog extends AppCompatDialogFragment {
    private EditText editTextWord;
    private EditText editTextTranslation;
    private EditWordDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_word_dialog, null);

        String oldWord = getArguments().getString("oldWord");

        builder.setView(view)
                .setTitle("Edytuj tłumaczenie")
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String translation = editTextTranslation.getText().toString();

                        listener.onSaveButtonClick(oldWord, translation);
                    }
                });

        editTextWord = view.findViewById(R.id.new_word);
        editTextWord.setText(oldWord);
        editTextWord.setEnabled(false);

        editTextTranslation = view.findViewById(R.id.new_word_translation);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            this.listener = (EditWordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement EditWordDialogListener");
        }
    }

    public interface EditWordDialogListener {
        void onSaveButtonClick(String word, String translation);
    }
}
