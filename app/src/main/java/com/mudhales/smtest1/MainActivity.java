package com.mudhales.smtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.smtest1.fragment.ListDataFragment;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installListener();
        // Added fragment for list data by SM
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new ListDataFragment()).commit();

    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    private void installListener() {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");
                    NetworkInfo.State state = info.getState();
                   // Log.d("BroadcastReceiver", info.toString() + " " + state.toString());
                    if (state != NetworkInfo.State.CONNECTED) {
                        showMessage(getString(R.string.no_internet));
                    }
                }
            };
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }
    private void showMessage(String strMessage){
        Snackbar snackbar = Snackbar
                .make(this.getCurrentFocus(), strMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}