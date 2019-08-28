package ticket.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ApproveAdapter extends RecyclerView.Adapter<ApproveViewHolder> {

    public ArrayList<Approve> data;

    @NonNull
    @Override
    public ApproveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View x = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve, viewGroup,
                        false);

        ApproveViewHolder r = new ApproveViewHolder(x);

        return r;
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveViewHolder approveViewHolder, int i) {

        Approve r = data.get(i);
        approveViewHolder.et_reported.setText(r.reported);
        approveViewHolder.et_prosummary.setText(r.problem_summary);
        approveViewHolder.et_iduser.setText(r.id_user);
        approveViewHolder.et_idticket.setText(r.id_ticket+ "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
