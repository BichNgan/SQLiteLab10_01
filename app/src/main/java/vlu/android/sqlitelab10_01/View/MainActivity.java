package vlu.android.sqlitelab10_01.View;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import vlu.android.sqlitelab10_01.Controller.KhoaHandler;
import vlu.android.sqlitelab10_01.Model.Khoa;
import vlu.android.sqlitelab10_01.R;

public class MainActivity extends AppCompatActivity {

    private static final String DB_NAME = "qlsv";
    private static final int DB_VERSION =1;
    private static final String TABLE_NAME = "Khoa";
    private static final String MKHOA_COL = "makhoa";
    private static final String TENKHOA_COL = "tenkhoa";
    private static final String PATH = "/data/data/vlu.android.sqlitelab10_01/database/qlsv.db";

    EditText edtMK, edtTK;
    Button btnInsert, btnUpdate;
    ListView lvKhoa;
    ArrayList<String> arrayListKhoa = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ArrayList<Khoa> lsKhoa = new ArrayList<>();
    KhoaHandler khoaHandler;
    SQLiteDatabase sqLiteDatabase;

    //--------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //-------------------------
        addControl();
        //-----------------------
        khoaHandler = new KhoaHandler(getApplicationContext(),DB_NAME,null,DB_VERSION);
        khoaHandler.onCreate(sqLiteDatabase);
        khoaHandler.initData();
        //----------------------
        lsKhoa = khoaHandler.loadKhoaTable();
        arrayListKhoa = createDataForListView(lsKhoa);
        adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayListKhoa);
        lvKhoa.setAdapter(adapter);
        //------------------------------
        addEvent();

    }
    void addControl()
    {
        edtMK=(EditText) findViewById(R.id.edtMK);
        edtTK = (EditText) findViewById(R.id.edtTK);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        lvKhoa = (ListView) findViewById(R.id.lvKhoa);
    }

    ArrayList<String> createDataForListView(ArrayList<Khoa> lsKhoa)
    {
        ArrayList<String> arrayListLV = new ArrayList<>();
        for (Khoa k:lsKhoa)
        {
            String st = k.getMaKhoa() + " " +k.getTenKhoa();
            arrayListLV.add(st);
        }
        return arrayListLV;
    }

    void addEvent()
    {
        lvKhoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Khoa k = lsKhoa.get(i);
                edtMK.setText(k.getMaKhoa());
                edtTK.setText(k.getTenKhoa());
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makhoa = edtMK.getText().toString();
                String tenkhoa = edtTK.getText().toString();
                Khoa k = new Khoa(makhoa,tenkhoa);

                int flag=0;
                for(int i=0;i<lsKhoa.size();i++)
                    if(k.getMaKhoa().equals(lsKhoa.get(i).getMaKhoa()))
                    {
                        flag=1;
                        break;
                    }

               if(flag==1)//co khoa k roi
               {
                   Toast.makeText(getApplicationContext(),"Khoa da ton tai",Toast.LENGTH_LONG).show();
               }
               else
               {
                   khoaHandler.addRecordIntoKhoaTable(k);
                   lsKhoa=khoaHandler.loadKhoaTable();
                   arrayListKhoa = createDataForListView(lsKhoa);
                   adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayListKhoa);
                   lvKhoa.setAdapter(adapter);

               }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mk = edtMK.getText().toString();
                String tk = edtTK.getText().toString();
                if(mk!=null && tk!=null)
                {
                    Khoa k = new Khoa(mk,tk);
                    int flag=0;
                    for(int i=0;i<lsKhoa.size();i++)
                        if(k.getMaKhoa().equals(lsKhoa.get(i).getMaKhoa()))
                        {
                            flag=1;
                            break;
                        }
                    if(flag==0)
                    {
                        Toast.makeText(getApplicationContext(),"Khoa nay khong ton tai",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        khoaHandler.updateRecord(k);
                        lsKhoa=khoaHandler.loadKhoaTable();
                        arrayListKhoa = createDataForListView(lsKhoa);
                        adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayListKhoa);
                        lvKhoa.setAdapter(adapter);

                    }


                }
                else
                    Toast.makeText(getApplicationContext(),"Du lieu ko hop le",Toast.LENGTH_LONG).show();
            }
        });
    }
}