package ticket.modernland.co.id;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp =
                        getSharedPreferences("DATALOGIN",
                                MODE_PRIVATE);
                String level        =sp.getString("level","");

                if (sp.contains("user")) {

                    if (level.equals("USER")){

                        Intent i = new Intent(getApplicationContext(),
                                HomeUserActivity.class);

                        i.putExtra("level",
                                sp.getString("level", ""));

                        i.putExtra("user",
                                sp.getString("user", ""));

                        i.putExtra("app",
                                sp.getString("app", ""));

                        startActivity(i);
                        finish();
                    }

                    else if (level.equals("VENDOR")){

                        Intent i = new Intent(getApplicationContext(),
                                HomeVendorActivity.class);

                        i.putExtra("level",
                                sp.getString("level", ""));

                        i.putExtra("user",
                                sp.getString("user", ""));

                        i.putExtra("app",
                                sp.getString("app", ""));

                        startActivity(i);
                        finish();

                    }


                } else {

                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);

                    startActivity(i);
                    finish();
                }
            }
        }, 5000);
    }
}
