package univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import univaq.aq.it.abruzzotourism.R;
import univaq.aq.it.abruzzotourism.Activities.ProfiloTurista.RegistroPrenotazioniFragment.OnListFragmentInteractionListener;
import univaq.aq.it.abruzzotourism.PrenotazioneItem.PrenotazioneItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PrenotazioneItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyPrenotazioniRecyclerViewAdapter extends RecyclerView.Adapter<MyPrenotazioniRecyclerViewAdapter.ViewHolder> {

    private final List<PrenotazioneItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPrenotazioniRecyclerViewAdapter(List<PrenotazioneItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prenotazioni, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getContent());
        holder.mDetails.setText(mValues.get(position).getDetails());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDetails;
        public PrenotazioneItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDetails = (TextView) view.findViewById(R.id.details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
