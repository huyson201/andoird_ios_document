package tdc.edu.vn.quanlynhansu;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.quanlynhansu.adapters.PersonAdapter;
import tdc.edu.vn.quanlynhansu.data_models.Person;
import tdc.edu.vn.quanlynhansu.database_layer.PersonDatabase;

public class QuanLyNhanSuActivity extends AppCompatActivity {
    public static String DAIHOC = "University";
    public static String CAODANG = "College";
    public static String TRUNGCAP = "Training";
    public static String KHONGBANGCAP = "None";

    private ArrayList<Person> listMembers = new ArrayList<Person>();
    //private ArrayAdapter<Person> adapter;
    private PersonAdapter adapter;
    private PersonDatabase DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quan_ly_nhan_su_layout);

        DAO = new PersonDatabase(this);

        //Get views from layout
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radGroup);
        final CheckBox chkRead = (CheckBox) findViewById(R.id.chkRead);
        final CheckBox chkTravel = (CheckBox) findViewById(R.id.chkTravel);
        final EditText edtHoppies = (EditText) findViewById(R.id.edtHobbies);
        ListView listView = (ListView) findViewById(R.id.listPerson);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        //Events processing
        //adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, listMembers);
        DAO.getPersons(listMembers);
        adapter = new PersonAdapter(this, R.layout.listview_item, listMembers);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtName.getText().toString().isEmpty()) {
                    Person nhanSu = new Person();
                    nhanSu.setName(edtName.getText().toString());
                    int id = radioGroup.getCheckedRadioButtonId();
                    switch (id) {
                        case R.id.radTraining:
                            nhanSu.setDegree(TRUNGCAP);
                            break;
                        case R.id.radCollege:
                            nhanSu.setDegree(CAODANG);
                            break;
                        case R.id.radUniversity:
                            nhanSu.setDegree(DAIHOC);
                            break;
                        default:
                            nhanSu.setDegree(KHONGBANGCAP);
                            break;
                    }
                    String hoppies = "";
                    if (chkRead.isChecked()) {
                        hoppies += chkRead.getText() + ";";
                    }
                    if (chkTravel.isChecked()) {
                        hoppies += chkTravel.getText() + ";";
                    }
                    hoppies += edtHoppies.getText();
                    nhanSu.setHobbies(hoppies);
                    listMembers.add(nhanSu);
                    // Bao cho adapter biet du lieu thay doi
                    adapter.notifyDataSetChanged();

                    edtName.setText("");
                    radioGroup.clearCheck();
                    chkRead.setChecked(false);
                    chkTravel.setChecked(false);
                    edtHoppies.setText("");
                    edtName.requestFocus();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    // On menu click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.saveMN:
                save();
                break;
            case R.id.removeMN:
                remove();
                break;
            case R.id.updateMN:
                update();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Methodes of Option Menu
    public void save() {
        //Toast.makeText(this, "save()", Toast.LENGTH_SHORT).show();
        DAO.savePersons(listMembers);
    }

    public void remove() {
        //Toast.makeText(this, "remove()", Toast.LENGTH_SHORT).show();
        for (int i = 0; i<listMembers.size(); ++i) {
            if (listMembers.get(i).getCheck()) {
                DAO.removePerson(listMembers.get(i));
                listMembers.remove(i);
                i--;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void update() {
        //Toast.makeText(this, "update()", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        //Get views from layout
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radGroup);
        final CheckBox chkRead = (CheckBox) dialog.findViewById(R.id.chkRead);
        final CheckBox chkTravel = (CheckBox) dialog.findViewById(R.id.chkTravel);
        final EditText edtHoppies = (EditText) dialog.findViewById(R.id.edtHobbies);
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        dialog.setTitle("Update the person");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Show dialog
        dialog.show();
    }
}
