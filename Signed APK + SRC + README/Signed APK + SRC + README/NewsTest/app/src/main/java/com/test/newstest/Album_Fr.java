package com.test.newstest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


public class Album_Fr extends Fragment {
    private int layout_id;

    public Album_Fr(int layout_id) {
        this.layout_id = R.layout.album;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album, container, false);
        ViewPager2 pager = view.findViewById(R.id.album_pager);
        pager.setAdapter(new AlbumPagerAdapter(getContext()));
        return view;
    }

}