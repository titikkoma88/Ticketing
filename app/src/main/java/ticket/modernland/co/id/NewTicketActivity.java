package ticket.modernland.co.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        SharedPreferences sp = getSharedPreferences("DATALOGIN", 0);
        String user         = sp.getString("user", "");

        final TextView et_nama = (TextView) findViewById(R.id.et_nama);

        Spinner spnKategori = (Spinner) findViewById(R.id.spn_kategori);
        final Spinner spnSubkategori = (Spinner) findViewById(R.id.spn_subkategori);

        final String pilihkategori [] = {"Pilih Kategori Masalah", "DATA", "SYSTEM"};
        final String nokategori [] = {"Pilih Sub Kategori"};
        final String data [] = {"BUGS FORM", "REQUEST FORM", "REQUEST REPORT"};
        final String system [] = {"DATA ERROR"};

        //Toast.makeText(getApplicationContext(),
        //       "Welcome " + user, Toast.LENGTH_LONG).show();

        //1. Buka postman
        OkHttpClient postman = new OkHttpClient();

        //3. set cara kirim dan tujuan kirim
        Request request = new Request.Builder()
                .get()
                .url(setting.IP + "get_user.php?a=" + user)
                .build();

        //4. progress dialog
        final ProgressDialog pd = new ProgressDialog(NewTicketActivity.this);
        pd.setTitle("Please wait");
        pd.setCancelable(false);
        pd.setMessage("Loading ...");
        pd.show();

        //5. send
        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Please Try Again",
                                Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();

                try {
                    JSONObject jo = new JSONObject(hasil);

                    final String nama = jo.getString("nama");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_nama.setText(nama);

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,pilihkategori);
        spnKategori.setAdapter(adapter);

        spnKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = pilihkategori[position];

                if (position == 0){
                    spnSubkategori.setAdapter(new ArrayAdapter(NewTicketActivity.this,android.R.layout.simple_spinner_dropdown_item,nokategori));
                }

                if (position == 1){
                    spnSubkategori.setAdapter(new ArrayAdapter(NewTicketActivity.this,android.R.layout.simple_spinner_dropdown_item,data));
                }

                if (position == 2){
                    spnSubkategori.setAdapter(new ArrayAdapter(NewTicketActivity.this,android.R.layout.simple_spinner_dropdown_item,system));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void openCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 1234);
    }

    public void openGallery(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 9999);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234)
        {
            if(resultCode == RESULT_OK)
            {
                // ambil imageview menggunakan findviewbyid
                ImageView imgFoto = (ImageView)
                        findViewById(R.id.img_foto);

                Bitmap gambarnya
                        = (Bitmap) data.getExtras().get("data");

                imgFoto.setImageBitmap(gambarnya);
            }
        }
        else if(requestCode == 9999)
        {
            if(resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ImageView imgFoto = (ImageView)
                            findViewById(R.id.img_foto);

                    imgFoto.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void goHome(View view) {
        Intent i = new Intent(getApplicationContext(),
                HomeUserActivity.class);

        startActivity(i);
    }

    public void prosesSubmitNew(View view) {

        Spinner spnKategori = (Spinner) findViewById(R.id.spn_kategori);
        Spinner spnSubKategori = (Spinner) findViewById(R.id.spn_subkategori);
        EditText tSubjekMsl = (EditText) findViewById(R.id.et_subjekmas);
        EditText tDeskripsi = (EditText) findViewById(R.id.et_deskripsi);

        String isikategori = spnKategori.getSelectedItem().toString();
        String isisubkategori = spnSubKategori.getSelectedItem().toString();
        String isisubjek = tSubjekMsl.getText().toString();
        String isideskripsi = tDeskripsi.getText().toString();

        SharedPreferences sp = getSharedPreferences("DATALOGIN", 0);
        String user         = sp.getString("user", "");
        String app         = sp.getString("app", "");


        if (isikategori.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please select kategori",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (isisubkategori.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please select sub kategori",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (isisubjek.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please input subjek masalah",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (isideskripsi.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please input deskripsi masalah",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //1. Buka postman
        OkHttpClient postman = new OkHttpClient();

        //2. body
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("kategori", isikategori)
                .addFormDataPart("sub_kategori", isisubkategori)
                .addFormDataPart("problem_summary", isisubjek)
                .addFormDataPart("problem_detail", isideskripsi)
                .addFormDataPart("user", user)
                .addFormDataPart("app", app)
                .build();

        //3. set cara kirim dan tujuan kirim
        Request request = new Request.Builder()
                .post(body)
                .url(setting.IP + "proses_new_ticket.php")
                .build();

        //4. progress dialog
        final ProgressDialog pd = new ProgressDialog(NewTicketActivity.this);
        pd.setTitle("Please wait");
        pd.setCancelable(false);
        pd.setMessage("Loading ...");
        pd.show();

        //5. send
        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Please Try Again",
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
                    if(st == true)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "New Tikcet Succes",
                                        Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(),
                                        HomeUserActivity.class);

                                startActivity(i);
                            }
                        });
                    }
                    else {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
