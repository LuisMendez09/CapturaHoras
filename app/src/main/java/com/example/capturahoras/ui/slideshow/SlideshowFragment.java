package com.example.capturahoras.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.arch.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.capturahoras.R;
import com.example.capturahoras.databinding.FragmentSlideshowBinding;
import com.example.capturahoras.ui.gallery.GalleryViewModel;

public class SlideshowFragment extends Fragment {

    //private FragmentSlideshowBinding binding;
    private SlideshowViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;*/
        galleryViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
/*
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}