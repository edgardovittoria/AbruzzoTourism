package univaq.aq.it.abruzzotourism.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import univaq.aq.it.abruzzotourism.Activities.Prenotazione.DettagliAttivitaActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    private List<Attivita> attivita;
    private LayoutInflater mInflanter;
    UserDetails user;
    Context context;

    public SearchRecyclerAdapter(Context context, List<Attivita> attivita, UserDetails user){
        this.mInflanter = LayoutInflater.from(context);
        this.attivita = attivita;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflanter.inflate(R.layout.card_view_home, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_nomeattivita.setText(this.attivita.get(position).getNomeAttivita());
        holder.tv_descrizioneattivita.setText(this.attivita.get(position).getDescrizione());
        byte[] bytes = Base64.decode(this.attivita.get(position).getImage(),0);
        Bitmap image1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.im_attivita.setImageBitmap(image1);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DettagliAttivitaActivity.class);
                i.putExtra("attivita", attivita.get(position));
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.attivita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nomeattivita;
        TextView tv_descrizioneattivita;
        ImageView im_attivita;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            this.tv_nomeattivita = itemView.findViewById(R.id.tv_nomeattivita);
            this.tv_descrizioneattivita = itemView.findViewById(R.id.tv_descrizioneattivita);
            this.im_attivita = itemView.findViewById(R.id.im_attivita);
            this.cardView = itemView.findViewById(R.id.card_view);
        }

    }


}

