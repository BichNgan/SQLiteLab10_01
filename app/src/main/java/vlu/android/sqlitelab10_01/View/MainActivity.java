package vlu.android.sqlitelab10_01.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import vlu.android.sqlitelab10_01.R;

public class MainActivity extends AppCompatActivity {

    EditText edtMK, edtTK;
    Button btnInsert, btnUpdate;
    ListView lvKhoa;
    ArrayList<String> arrayListKhoa = new ArrayList<>();
    ArrayAdapter<String> adapter;


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

    }
    void addControl()
    {
        edtMK=(EditText) findViewById(R.id.edtMK);
        edtTK = (EditText) findViewById(R.id.edtTK);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnUpdate= (Button) findViewById(R.id.btnUpdate);
        lvKhoa = (ListView) findViewById(R.id.lvKhoa);
    }
}