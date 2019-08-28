package ticket.modernland.co.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void prosesLogin(View view) {

        EditText etUsername = (EditText) findViewById(R.id.et_username);
        EditText etPassword = (EditText) findViewById(R.id.et_password);
        Spinner spnApp = (Spinner) findViewById(R.id.spn_app);

        final String isiuser = etUsername.getText().toString();
        final String isipass = etPassword.getText().toString();
        final String pilihapp = spnApp.getSelectedItem().toString();

        if(isiuser.length() == 0 ){
            etUsername.setError("Email is required");
            return;
        }

        if(isipass.length() == 0 ) {
            etPassword.setError("Password is required");
            return;
        }

        OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", isiuser)
                .addFormDataPart("password", isipass)
                .addFormDataPart("app", pilihapp)
                .build();

        Request request = new Request.Builder()
                .post(body)
                .url(setting.IP + "proses_login.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Loading");
        pd.setMessage("Please Wait..");
        pd.setIcon(R.drawable.ic_playlist_add_check_black_24dp);
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Please try again : " +e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();
                try {
                    JSONObject j = new JSONObject(hasil);
                    boolean st = j.getBoolean("status");

                    if(st == false)
                    {
                        final String p = j.getString("pesan");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        p, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });
                    }
                    else {

                        final String lvl = j.getString("level");
                        String iduser = j.getString("id_user");

                        SharedPreferences.Editor sp
                                = getSharedPreferences("DATALOGIN",0).edit();

                        sp.putString("id_user",iduser);
                        sp.putString("user",isiuser);
                        sp.putString("password",isipass);
                        sp.putString("app",pilihapp);
                        sp.putString("level",lvl);

                        sp.commit();

                        if (lvl.equals("USER")){

                            Intent i = new Intent(getApplicationContext(),
                                    HomeUserActivity.class);

                            i.putExtra("id_user", iduser);
                            i.putExtra("user", isiuser);
                            i.putExtra("password", isipass);
                            i.putExtra("app",pilihapp);
                            i.putExtra("level",lvl);

                            startActivity(i);
                            finish();
                        }

                        else if (lvl.equals("VENDOR")){

                            Intent i = new Intent(getApplicationContext(),
                                    HomeVendorActivity.class);

                            i.putExtra("id_user", iduser);
                            i.putExtra("user", isiuser);
                            i.putExtra("password", isipass);
                            i.putExtra("app",pilihapp);
                            i.putExtra("level",lvl);

                            startActivity(i);
                            finish();

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}

