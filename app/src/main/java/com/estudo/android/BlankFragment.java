package com.estudo.android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.estudo.android.databinding.FragmentBlankBinding;

import java.util.ArrayList;

public class BlankFragment extends Fragment {

    private FragmentBlankBinding binding;
    private SQLiteDatabase sqLiteDatabase;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BlankFragment() {}

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        this.Banco();
        this.Inserir();
    }

    public void Banco() {
        try {
            sqLiteDatabase = getActivity().openOrCreateDatabase("dados.db", Context.MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Pessoas (id INTEGER PRIMARY KEY, nome VARCHAR(100))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SelectPessoas(ListView listView) {
        try {
            sqLiteDatabase = getActivity().openOrCreateDatabase("dados.db", Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Pessoas", null);
            ArrayList<String> stringArrayList = new ArrayList<>();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    stringArrayList
            );

            listView.setAdapter(arrayAdapter);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                stringArrayList.add(cursor.getString(1));
                cursor.moveToNext();
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Inserir() {
        try {
            sqLiteDatabase = getActivity().openOrCreateDatabase("dados.db", Context.MODE_PRIVATE, null);
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement("INSERT INTO Pessoas (id, nome) VALUES (?, ?)");
            sqLiteStatement.bindLong(1, 1);
            sqLiteStatement.bindString(2, "Érick");
            sqLiteStatement.executeInsert();

            sqLiteStatement.bindLong(1, 2);
            sqLiteStatement.bindString(2, "Laura");
            sqLiteStatement.executeInsert();

            sqLiteStatement.bindLong(1, 3);
            sqLiteStatement.bindString(2, "Lauro");
            sqLiteStatement.executeInsert();

            sqLiteStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView ist_view = binding.istView;
        this.SelectPessoas(ist_view);

        return root;
    }
}
