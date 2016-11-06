package com.example.fukaokyouhei.a1107am9_homework;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    IServiceMethod aidl = null;
    Button match;
    EditText StringA;
    EditText StringB;
    TextView result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        match = (Button)findViewById(R.id.match);
        StringA = (EditText)findViewById(R.id.String1);
        StringB = (EditText)findViewById(R.id.String2);
        result = (TextView)findViewById(R.id.result);

        //MyServiceにbindする
        Intent i = new Intent(MainActivity.this, MyService.class);
        i.setAction(IServiceMethod.class.getName());
        i.setPackage("com.example.fukaokyouhei.1107am9_homework");
        bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);

        match.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    String a = StringA.getText().toString();
                    String b = StringB.getText().toString();
                    String rtext = aidl.CallServiceMethod(a,b);
                    result.setText(rtext);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // MyServiceからアンバイド
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        // サービスとの接続時に呼ばれる
        public void onServiceConnected(ComponentName name, IBinder ibinder)
        {
            // Ibinder インターフェースから、AIDLのインターフェースにキャストするメソッドらしい
            aidl = IServiceMethod.Stub.asInterface(ibinder);
        }

        // サービスとの切断時に呼ばれる
        public void onServiceDisconnected(ComponentName name)
        {
            aidl = null;
        }
    };

}
