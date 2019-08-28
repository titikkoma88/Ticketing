package ticket.modernland.co.id;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static ticket.modernland.co.id.R.id.rv2;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTicketFragment extends Fragment {


    public MyTicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_list_ticket, container, false);

        final RecyclerView rv2 = (RecyclerView) x.findViewById(R.id.rv2);

        SharedPreferences sp = getActivity()
                .getSharedPreferences("DATALOGIN", 0);

        String id_user      = sp.getString("id_user", "");
        String app          = sp.getString("app", "");

        final LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv2.setLayoutManager(lm);

        // 1. postman
        OkHttpClient postman = new OkHttpClient();

        // 2. request
        Request request = new Request.Builder()
                .get()
                .url(setting.IP + "mylist_ticket.php?a=" + id_user + "&" + "b=" + app)
                .build();

        // 3. progress dialog
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait");
        pd.setTitle("Loading ...");
        pd.setIcon(R.drawable.ic_check_black_24dp);
        pd.setCancelable(false);
        pd.show();

        // 4. send
        postman.newCall(request).enqueue(new Callback() {
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
                    JSONArray j = new JSONArray(hasil);

                    final TicketAdapter adapter = new TicketAdapter();
                    adapter.data = new ArrayList<Ticket>();
                    for (int i = 0;i < j.length();i++)
                    {
                        JSONObject jo = j.getJSONObject(i);
                        Ticket r = new Ticket();

                        r.reported = jo.getString("reported");
                        r.problem_summary = jo.getString("problem_summary");
                        r.id_ticket = jo.getString("id_ticket");

                        adapter.data.add(r);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            rv2.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return x;
    }

}