package ticket.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketViewHolder> {

    public ArrayList<Ticket> data;

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View x = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_ticket, viewGroup,
                        false);

        TicketViewHolder t = new TicketViewHolder(x);

        return t;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder ticketViewHolder, int i) {

        Ticket t = data.get(i);
        ticketViewHolder.et_reported.setText(t.reported);
        ticketViewHolder.et_prosummary.setText(t.problem_summary);
        ticketViewHolder.et_idticket.setText(t.id_ticket+ "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

