package tdc.edu.vn.quanlynhansu.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import  tdc.edu.vn.quanlynhansu.QuanLyNhanSuActivity;
import tdc.edu.vn.quanlynhansu.R;
import tdc.edu.vn.quanlynhansu.data_models.Person;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Activity context;
    private int layoutId;
    private ArrayList<Person> persons;

    public RecyclerViewAdapter(Activity context, int layoutId, ArrayList<Person> persons) {
        this.context = context;
        this.layoutId = layoutId;
        this.persons = persons;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = context.getLayoutInflater();
        CardView cardView = (CardView) inflater.inflate(i, viewGroup, false);
        return new MyViewHolder(cardView, persons);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        // Get person from datasource
        Person person = persons.get(i);
        viewHolder.setPosition(i);

        if(!person.getCheck()) viewHolder.select.setChecked(false);

        // Fill data from the person to the view
        if (person.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.CAODANG)) {
            viewHolder.imageView.setBackground(context.getResources().getDrawable(R.mipmap.college));
        }
        else if (person.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.DAIHOC)) {
            viewHolder.imageView.setBackground(context.getResources().getDrawable(R.mipmap.university));
        }
        else if (person.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.TRUNGCAP)) {
            viewHolder.imageView.setBackground(context.getResources().getDrawable(R.mipmap.midium));
        }
        else {
            viewHolder.imageView.setBackground(context.getResources().getDrawable(R.mipmap.none));
        }

        viewHolder.lblName.setText(person.getName()) ;
        viewHolder.lblHobbies.setText(person.getHobbies());




    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView lblName;
        private TextView lblHobbies;
        private CheckBox select;
        private int position;

        public MyViewHolder(@NonNull View itemView, final ArrayList<Person> persons) {
            super(itemView);
            imageView = itemView.findViewById(R.id.degreeImage);
            lblName = itemView.findViewById(R.id.lblName);
            lblHobbies = itemView.findViewById(R.id.lblHoppies);
            select = itemView.findViewById(R.id.chkChosen);
            select.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (((CheckBox)v).isChecked()) {
                        persons.get(position).setCheck(true);
                    }
                    else {
                        persons.get(position).setCheck(false);
                    }
                }
            });

        }

        public void setPosition(int pos){
            position = pos;
        }
    }
}
