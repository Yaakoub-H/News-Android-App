package com.test.newstest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;

public class AlbumPagerAdapter extends RecyclerView.Adapter<AlbumPagerAdapter.ViewHolder> {

    private String[] thumbs = {"thumb7.jpg", "thumb10.jpg", "thumb9.jpg", "thumb2.jpg"};
    private Context ctx;
    private ImageView fullScreenImageView;

    public AlbumPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        InputStream fp = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        try {
            fp = ctx.getAssets().open(thumbs[position]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bitmap img = BitmapFactory.decodeStream(fp);
        holder.imgs.setImageBitmap(img);
        holder.imgs.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                    // create a new ImageView to display the image in full screen mode
                    fullScreenImageView = new ImageView(ctx);
                    fullScreenImageView.setImageBitmap(img);

                    ViewGroup rootView = ((Activity) ctx).getWindow().getDecorView().findViewById(android.R.id.content);
                    fullScreenImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    rootView.addView(fullScreenImageView);
                    fullScreenImageView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (fullScreenImageView != null) {
                                rootView.removeView(fullScreenImageView);
                                fullScreenImageView = null;
                            }
                            return true;
                        }
                    });
            }
        });

    }

    @Override
    public int getItemCount() {
        return thumbs.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.current_image);
        }
    }
}
