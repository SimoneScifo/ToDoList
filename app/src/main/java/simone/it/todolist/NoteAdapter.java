package simone.it.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import simone.it.todolist.DatabaseHandler;

import static java.security.AccessController.getContext;

/**
 * Created by Simone on 20/02/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {



    private ArrayList<Note> dataSet = new ArrayList<>();
    private int position;

    public void addNote(Note item) {
        dataSet.add(0, item);
        notifyItemInserted(0);

    }

    public void updateNote(Note item, int position) {
        dataSet.set(position, item);
        notifyItemChanged(position);
    }

    public ArrayList<Note> getNotes() {
        return dataSet;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Note getNote(int position) {
        return dataSet.get(position);
    }


    public void setDataSet(ArrayList<Note> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }
    public void removeNote(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    @Override
    public NoteAdapter.NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteVH(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteVH holder, int position) {
        Note note = dataSet.get(position);
        holder.titleTV.setText(note.getTitle());
        holder.body.setText(note.getBody());
        holder.expiration_dateTV.setText(note.getExpiration_date());
    }



    public class NoteVH extends RecyclerView.ViewHolder  {
        TextView titleTV, body, expiration_dateTV;

        public NoteVH(View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.titleTV);
            body = (TextView) itemView.findViewById(R.id.body_tv);
            expiration_dateTV = (TextView) itemView.findViewById(R.id.expiration_dateTV);
            setPosition(getAdapterPosition());
        }
    }
}
