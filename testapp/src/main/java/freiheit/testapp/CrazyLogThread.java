package freiheit.testapp;

import android.util.Log;

/**
 *
 * Created by timfreiheit on 29.03.15.
 */
public class CrazyLogThread implements Runnable {
    @Override
    public void run() {
        int counter = 0;
        while(true){
            try{
                Thread.sleep(20);
            }catch (Exception e){}
            Log.d("CrazyLog","time: "+(counter++));
        }
    }
}
