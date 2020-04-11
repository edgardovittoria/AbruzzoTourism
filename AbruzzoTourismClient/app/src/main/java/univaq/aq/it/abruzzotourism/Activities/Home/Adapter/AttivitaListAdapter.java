package univaq.aq.it.abruzzotourism.Activities.Home.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import univaq.aq.it.abruzzotourism.Activities.Prenotazione.DettagliAttivitaActivity;
import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.domain.Attivita;
import univaq.aq.it.abruzzotourism.domain.UserDetails;

public class AttivitaListAdapter extends BaseAdapter {
    List<Attivita> attivita;
    UserDetails user;
    Context context;
    DettagliAttivitaActivity dettagliActivity = new DettagliAttivitaActivity();
    private static LayoutInflater inflater=null;

    public AttivitaListAdapter(Activity activity, List<Attivita> listAttivita, UserDetails user) {
        attivita=listAttivita;
        this.user = user;
        context=activity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return attivita.size();
    }

    @Override
    public Object getItem(int position) {
        return attivita.get(position);
    }

    @Override
    public long getItemId(int position) {
        return attivita.get(position).getIDAttivita();
    }

    public class Holder
    {
        TextView tv_nomeattivita;
        TextView tv_descrizioneattivita;
        ImageView im_attivita;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final DettagliAttivitaActivity dettagliAttivitaActivity = new DettagliAttivitaActivity();
        Holder holder=new Holder();
        final View view;
        view = inflater.inflate(R.layout.card_view_home, null);

        holder.tv_nomeattivita=(TextView) view.findViewById(R.id.tv_nomeattivita);
        holder.tv_descrizioneattivita=(TextView) view.findViewById(R.id.tv_descrizioneattivita);
        holder.im_attivita=(ImageView) view.findViewById(R.id.im_attivita);

        holder.tv_nomeattivita.setText(attivita.get(position).getNomeAttivita());
        holder.tv_descrizioneattivita.setText(attivita.get(position).getDescrizione());
        byte[] bytes = Base64.decode(attivita.get(position).getImage(),0);


        Bitmap image1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.im_attivita.setImageBitmap(image1);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DettagliAttivitaActivity.class);
                attivita.get(position).setImage("");//evita l'errore parcel size
                i.putExtra("attivita", attivita.get(position));
                context.startActivity(i);
            }
        });
        return view;
    }


}
