package com.robinmglsk.newsstories;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkConnection();

    }

    private void checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {


        }else{
            findViewById(R.id.loader).setVisibility(View.GONE);
            TextView emptyText = findViewById(R.id.empty_list);
            emptyText.setText(getString(R.string.error_no_internet_connection));
        }
    }
}
