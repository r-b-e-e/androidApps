package com.rakeshbalancompany.inclass06;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by rakeshbalan on 10/5/2015.
 */
public class PreviewActivity extends Activity {

    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_main);

        tv = (TextView) findViewById(R.id.appNamePreview);
        iv = (ImageView) findViewById(R.id.imageViewPreview);
        App a = (App) this.getIntent().getExtras().getSerializable("url");
        tv.setText(a.getAppName());
        Picasso.with(this).load(a.getImageUrl()).error(R.drawable.ic_launcher).into(iv);
    }

    }
