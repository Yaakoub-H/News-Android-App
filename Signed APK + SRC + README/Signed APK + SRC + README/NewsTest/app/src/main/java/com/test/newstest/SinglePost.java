package com.test.newstest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
public class SinglePost extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_post);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String date = extras.getString("date");
        String content = extras.getString("content");
        String lang = extras.getString("lang");
        String thumb = extras.getString("thumb");

        TextView title_view = findViewById(R.id.post_title);
        TextView date_view = findViewById(R.id.post_date);
        TextView content_view = findViewById(R.id.post_content);
        ImageView img_view = findViewById(R.id.post_thumb);

        InputStream fp = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        try {
            fp = getAssets().open(thumb);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bitmap img = BitmapFactory.decodeStream(fp);
        img_view.setImageBitmap(img);

        if(lang.equals("ar"))
            content_view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        title_view.setText(title);
        date_view.setText(date);
        content_view.setText(content);

    }
}
