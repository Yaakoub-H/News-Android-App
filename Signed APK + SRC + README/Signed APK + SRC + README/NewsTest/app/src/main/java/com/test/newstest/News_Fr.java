package com.test.newstest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class News_Fr extends Fragment {

    private View view;
    private int layout_id;
    private String news_lang;
    public News_Fr(){
        super(R.layout.news);
        this.layout_id = R.layout.news;
        this.news_lang = "en";
    }
    public News_Fr(int layout_id){
        this.news_lang = "en";

        if(layout_id == R.id.ar_news)
            news_lang = "ar";

        if(layout_id == R.id.en_news || layout_id == R.id.ar_news)
            layout_id = R.layout.news;

        this.layout_id = layout_id;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            newsList();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout_id, container, false);
    }
    private void newsList() throws IOException, JSONException {
        View root = getView();
        if(root != null) {
        String file = String.format("%s.json", news_lang);
            RelativeLayout container = getView().findViewById(R.id.news_container);
            InputStream stream = getContext().getAssets().open(file);
            byte[] buff = new byte[stream.available()];
            stream.read(buff);
            String data = new String(buff);
            JSONObject obj = new JSONObject(data);
            JSONArray articles = obj.getJSONArray("articles");

            int last = 0;
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String date = article.getString("date");
                String content = article.getString("content");
                String thumb = article.getString("thumb");

                InputStream fp = getContext().getAssets().open(thumb);
                Bitmap img = BitmapFactory.decodeStream(fp);


                LinearLayout post_container = new LinearLayout(getContext());
                RelativeLayout.LayoutParams post_container_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                RelativeLayout.LayoutParams post_thumb_params = new RelativeLayout.LayoutParams(convertDpToPixel(180), convertDpToPixel(180));

                TextView title_text = new TextView(getContext());
                RelativeLayout.LayoutParams text_view_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                title_text.setText(String.format("%s\n%s", title, date));
                title_text.setTextSize(20);
                title_text.setPadding(20, 20, 20, 20);
                title_text.setId(View.generateViewId());

                ImageView post_thumb = new ImageView(getContext());
                post_thumb.setScaleType(ImageView.ScaleType.FIT_XY);

                post_thumb.setImageBitmap(img);
                post_thumb.setLayoutParams(post_thumb_params);

                if (news_lang.equals("ar"))
                    post_container.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                if (last != 0)
                    post_container_params.addRule(RelativeLayout.BELOW, last);

                post_container.setPadding(10, 10, 10, 10);

                title_text.setLayoutParams(text_view_params);
                title_text.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);// depends if rtl or ltr.

                post_container.setLayoutParams(post_container_params);
                post_container.setId(View.generateViewId());

                post_container.addView(post_thumb);
                post_container.addView(title_text);
                container.addView(post_container);

                post_container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getContext(), SinglePost.class);
                        intent.putExtra("title", title);
                        intent.putExtra("date", date);
                        intent.putExtra("content", content);
                        intent.putExtra("lang", news_lang);
                        intent.putExtra("thumb", thumb);
                        startActivity(intent);
                    }
                });
                last = post_container.getId();
            }
        }
    }


    private int convertDpToPixel(int dp){
        return dp * ((int) getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    }
