package ticket.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TicketViewHolder extends RecyclerView.ViewHolder {

    TextView et_idticket;
    TextView et_reported;
    TextView et_prosummary;

    public TicketViewHolder(@NonNull View itemView) {
        super(itemView);

        et_idticket = (TextView) itemView.findViewById(R.id.et_idticket);
        et_reported = (TextView) itemView.findViewById(R.id.et_reported);
        et_prosummary = (TextView) itemView.findViewById(R.id.et_prosummary);
    }
}
