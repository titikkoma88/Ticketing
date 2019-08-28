package ticket.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AssigmentAdapter extends RecyclerView.Adapter<AssigmentViewHolder> {

    public ArrayList<Assigment> data;

    @NonNull
    @Override
    public AssigmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View x = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_assigment, viewGroup,
                        false);

        AssigmentViewHolder r = new AssigmentViewHolder(x);

        return r;
    }

    @Override
    public void onBindViewHolder(@NonNull AssigmentViewHolder assigmentViewHolder, int i) {

        Assigment r = data.get(i);
        assigmentViewHolder.et_reported.setText(r.reported);
        assigmentViewHolder.et_prosummary.setText(r.problem_summary);
        assigmentViewHolder.et_iduser.setText(r.id_user);
        assigmentViewHolder.et_idticket.setText(r.id_ticket+ "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
