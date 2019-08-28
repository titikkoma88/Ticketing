package ticket.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ApproveViewHolder extends RecyclerView.ViewHolder {

    TextView et_idticket;
    TextView et_iduser;
    TextView et_reported;
    TextView et_prosummary;

    public ApproveViewHolder (@NonNull final View itemView) {
        super(itemView);

        et_idticket = (TextView) itemView.findViewById(R.id.et_idticket);
        et_iduser = (TextView) itemView.findViewById(R.id.et_iduser);
        et_reported = (TextView) itemView.findViewById(R.id.et_reported);
        et_prosummary = (TextView) itemView.findViewById(R.id.et_prosummary);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeVendorActivity h = (HomeVendorActivity) itemView.getContext();

                String id = et_idticket.getText().toString();

                ApproveDetailFragment df = new ApproveDetailFragment();

                Bundle b = new Bundle();
                b.putString("idnya", id);

                df.setArguments(b);

                h.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameVendor, df)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
