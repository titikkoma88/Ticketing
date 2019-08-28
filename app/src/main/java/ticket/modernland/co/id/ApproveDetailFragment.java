package ticket.modernland.co.id;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ApproveDetailFragment extends Fragment {


    public ApproveDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_approve_detail, container, false);

        final String id = getArguments().getString("idnya");

        final TextView tIdTiket = (TextView) x.findViewById(R.id.tIdTiket);
        final TextView tUser = (TextView) x.findViewById(R.id.tUser);
        final TextView tTanggal = (TextView) x.findViewById(R.id.tTanggal);
        final TextView tKategori = (TextView) x.findViewById(R.id.tKategori);
        final TextView tSubKategori = (TextView) x.findViewById(R.id.tSubKategori);
        final TextView tDeskripsi = (TextView) x.findViewById(R.id.tDeskripsi);

        OkHttpClient postman = new OkHttpClient();

        Request r = new Request.Builder()
                .get()
                .url(setting.IP + "get_ticket.php?a=" + id)
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait ...");
        pd.setTitle("Loading Ticket ...");
        pd.setIcon(R.drawable.ic_check_black_24dp);
        pd.setCancelable(true);
        pd.show();

        Toast.makeText(getActivity(),
                "Approve Ticket No " + id,
                Toast.LENGTH_LONG).show();

        postman.newCall(r).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Please Try Again",
                                Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String hasil = response.body().string();

                try {
                    JSONObject jo = new JSONObject(hasil);

                    final String id_ticket = jo.getString("id_ticket");
                    final String tanggal = jo.getString("tanggal");
                    final String nama = jo.getString("nama");
                    final String nama_kategori = jo.getString("nama_kategori");
                    final String nama_sub_kategori = jo.getString("nama_sub_kategori");
                    final String problem_summary = jo.getString("problem_summary");
                    final String problem_detail = jo.getString("problem_detail");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tIdTiket.setText(id_ticket);
                            tTanggal.setText(tanggal);
                            tUser.setText(nama);
                            tKategori.setText(nama_kategori);
                            tSubKategori.setText(nama_sub_kategori);
                            tDeskripsi.setText(problem_detail);

                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Button bApprove = (Button) x.findViewById(R.id.bApprove);
        bApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient postman = new OkHttpClient();

                SharedPreferences sp = getActivity()
                        .getSharedPreferences("DATALOGIN", 0);

                String id_user      = sp.getString("id_user", "");

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("id_ticket", id)
                        .addFormDataPart("id_user", id_user)
                        .build();

                Request request = new Request.Builder()
                        .post(body)
                        .url(setting.IP + "proses_approve_ticket.php")
                        .build();

                final ProgressDialog pd = new ProgressDialog(
                        getActivity()
                );
                pd.setMessage("Please Wait");
                pd.setTitle("Loading Approve...");
                pd.setIcon(R.drawable.ic_check_black_24dp);
                pd.show();

                postman.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),
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

                            if(st == false)
                            {
                                final String p = j.getString("pesan");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),
                                                p, Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    }
                                });
                            }
                            else {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Sukses Approve Ticket",
                                                Toast.LENGTH_LONG).show();

                                        getActivity().getSupportFragmentManager()
                                                .popBackStackImmediate();

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
        });

        Button bCancel = (Button) x.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStackImmediate();

            }
        });

        return x;
    }

}
