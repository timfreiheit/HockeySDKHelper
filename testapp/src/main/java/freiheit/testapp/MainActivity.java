package freiheit.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import de.timfreiheit.hockey.utils.WarningExceptionHandler;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new CrazyLogThread()).start();

        findViewById(R.id.crash_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashMe();
            }
        });

        findViewById(R.id.send_warning).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendWarning();
            }
        });

    }

    private void crashMe(){
        throw new RuntimeException("Something really bad happened");
    }

    private void sendWarning(){
        Toast.makeText(this, "Send Warning Exception to Hockey", Toast.LENGTH_SHORT).show();
        try {
            // some less important code
            throw new IllegalArgumentException("Something happened");
        }catch (Exception e) {
            WarningExceptionHandler.saveException(e, "more information about this warning");
        }
    }

}
