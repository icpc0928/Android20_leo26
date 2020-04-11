package tw.org.iii.leo.leo26;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBind;
    private TextView mesg;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
          MyService.LocalBinder binder = (MyService.LocalBinder)iBinder;
          myService = binder.getService();
          isBind=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind=false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mesg = findViewById(R.id.mesg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBind){
            unbindService(mConnection);
        }

    }

    public void test1(View view) {
        if(isBind && myService != null){
            int lottery = myService.lottery();
            Log.v("leo","lottery : "+ lottery);
            mesg.setText("lottery : "+myService.i+" : "+ lottery);
        }
    }
}
