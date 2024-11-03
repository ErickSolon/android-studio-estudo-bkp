package com.estudo.android.ui.notifications;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.estudo.android.R;
import com.estudo.android.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private CameraPreview mPreview;
    private Camera mCamera;
    private static final int CAMERA_PERMISSION_CODE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        // notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        try {
            String chve = getArguments().getString("chave");
            if (chve != null)
                textView.setText(chve);
        } catch (NullPointerException e) {
            textView.setText("OI! teste123");
        }

        binding.buttonCapture.setOnClickListener(view -> {
            if (checkCameraPermission()) {
                openCamera(container.getContext());
            } else {
                requestCameraPermission();
            }
        });

        return root;
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera(requireContext());
            } else {
                Toast.makeText(requireContext(), "Permissão de câmera negada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera(Context context) {
        if (checkCameraHardware(context)) {
            mCamera = getCameraInstance();
            mPreview = new CameraPreview(context, mCamera);
            FrameLayout preview = binding.cameraPreview;
            preview.addView(mPreview);
        } else {
            Toast.makeText(context, "Dispositivo sem câmera", Toast.LENGTH_SHORT).show();
        }
    }

    /** Obtém uma instância segura da câmera */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseCamera();
        binding = null;
    }
}
