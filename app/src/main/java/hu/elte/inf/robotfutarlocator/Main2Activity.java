package hu.elte.inf.robotfutarlocator;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketState;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT;
    ArrayAdapter mArrayAdapter;
    WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                if (mBluetoothAdapter.isDiscovering())
                {
                    Toast.makeText(context, "Scanning is in progress", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(context, "Start scanning devices... " + ws.getState().name(), Toast.LENGTH_LONG).show();

                    mArrayAdapter.clear();
                    mBluetoothAdapter.startDiscovery();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            ws = new WebSocketFactory().createSocket("wss://robikapa-backend.herokuapp.com/ws");
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception
                {
                }


                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception
                {
                    Log.i("websocket", "connected, loggin in");
                    ws.sendText("{\"action\":\"login.robot\"}");
                }


                @Override
                public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception
                {
                }


                @Override
                public void onDisconnected(WebSocket websocket,
                                           WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                                           boolean closedByServer) throws Exception
                {
                }


                @Override
                public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onTextMessage(WebSocket websocket, String text) throws Exception
                {
                }


                @Override
                public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception
                {
                }


                @Override
                public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onError(WebSocket websocket, WebSocketException cause) throws Exception
                {
                }


                @Override
                public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception
                {
                }


                @Override
                public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception
                {
                }


                @Override
                public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception
                {

                }


                @Override
                public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception
                {
                }


                @Override
                public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception
                {
                }


                @Override
                public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception
                {
                }

                @Override
                public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {

                }
            });
            ws.connectAsynchronously();
            // Send a ping per 60 seconds.
            ws.setPingInterval(10000);

        } catch (IOException ex) {

        }



        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};

        mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mArrayAdapter);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth device is not found", duration).show();
            return;
        } else
        {
            Toast.makeText(context, "Ok, bluetooth is supperted", duration).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        mBluetoothAdapter.startDiscovery();
    }

    public void refreshDevices(View view) {
        // Do something in response to button click
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress() + "\n" + "RSSI: " + rssi + "dBm");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
            {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                pb.setVisibility(View.VISIBLE);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                pb.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    public void onDestroy() {
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception ex) {}

        ws.disconnect();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
