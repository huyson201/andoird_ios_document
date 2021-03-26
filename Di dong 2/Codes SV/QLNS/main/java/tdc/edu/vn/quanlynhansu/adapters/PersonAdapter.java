package tdc.edu.vn.quanlynhansu.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tdc.edu.vn.quanlynhansu.QuanLyNhanSuActivity;
import tdc.edu.vn.quanlynhansu.R;
import tdc.edu.vn.quanlynhansu.data_models.Person;

public class PersonAdapter extends ArrayAdapter<Person> {
    private Activity context;
    private int layoutID;
    private ArrayList<Person> members;

    public PersonAdapter(Activity context, int layoutID, ArrayList<Person> members) {
        super(context, layoutID, members);
        this.context = context;
        this.layoutID = layoutID;
        this.members = members;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        view = context.getLayoutInflater().inflate(R.layout.listview_item, parent, false);
        // Get components from layout
        ImageView degreeImage = view.findViewById(R.id.degreeImage);
        TextView lblName = view.findViewById(R.id.lblName);
        TextView lblHoppies = view.findViewById(R.id.lblHoppies);
        CheckBox chkPerson = view.findViewById(R.id.chkChosen);

        chkPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    members.get(position).setCheck(true);
                }
                else {
                    members.get(position).setCheck(false);
                }
            }
        });

        // Get person from datasource
        Person person = members.get(position);

        // Fill data from the person to the view
        if (person.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.CAODANG)) {
            degreeImage.setBackground(context.getResources().getDrawable(R.mipmap.college));
        }
        else if (person.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.DAIHOC)) {
            degreeImage.setBackground(context.getResources().getDrawable(R.mipmap.university));
        }
        else if (person.getDegree().equalsIgnoreCase(QuanLyNhanSuActivity.TRUNGCAP)) {
            degreeImage.setBackground(context.getResources().getDrawable(R.mipmap.midium));
        }
        else {
            degreeImage.setBackground(context.getResources().getDrawable(R.mipmap.none));
        }

        lblName.setText(person.getName());
        lblHoppies.setText(person.getHobbies());

        return view;
    }
}
