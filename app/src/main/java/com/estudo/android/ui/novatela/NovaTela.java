package com.estudo.android.ui.novatela;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.estudo.android.R;
import com.estudo.android.databinding.FragmentNovaTelaBinding;

public class NovaTela extends Fragment {

    private NovaTelaViewModel mViewModel;
    private FragmentNovaTelaBinding binding;

    public static NovaTela newInstance() {
        return new NovaTela();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NovaTelaViewModel.class);
        // TODO: Use the ViewModel


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNovaTelaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button button2OnClick = binding.button2;
        button2OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar para outro fragmento
                NavController navController = NavHostFragment.findNavController(NovaTela.this);
                navController.navigate(R.id.blankFragment2);  // Use o ID correto do fragmento no arquivo de navegação
            }
        });

        return root;
    }

}