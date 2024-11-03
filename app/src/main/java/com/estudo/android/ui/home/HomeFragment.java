package com.estudo.android.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.estudo.android.MainActivity2;
import com.estudo.android.MapsFragment;
import com.estudo.android.R;
import com.estudo.android.databinding.FragmentDashboardBinding;
import com.estudo.android.databinding.FragmentHomeBinding;
import com.estudo.android.ui.dashboard.DashboardFragment;
import com.estudo.android.ui.dashboard.DashboardViewModel;
import com.estudo.android.ui.notifications.NotificationsFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final Button idButton = binding.idButton;
        idButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mensagem = new AlertDialog.Builder(view.getContext());
                mensagem.setIcon(R.drawable.ic_notifications_black_24dp);
                mensagem.setTitle("Título aqui!");
                mensagem.setMessage("oi, como vai vc?");
                mensagem.setNeutralButton("OK!", null);
                mensagem.show();
            }
        });

        final Button button = binding.button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(view.getContext(), MainActivity2.class);
                intent.putExtra("chave", "1234567890");
                startActivity(intent);
                */

                /*
                Bundle bundle = new Bundle();
                bundle.putString("chave", "1234567890");

                NotificationsFragment notificationsFragment = new NotificationsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                notificationsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, notificationsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                */

                Bundle bundle = new Bundle();
                bundle.putString("chave", "1234567890");
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(R.id.navigation_notifications, bundle);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}