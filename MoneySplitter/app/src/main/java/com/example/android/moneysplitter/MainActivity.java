package com.example.android.moneysplitter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends Activity {

    private ArrayAdapter<String> adapter;
    private EditText txtInput;
    private ArrayList<String> arrayList=new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ListView listView=(ListView)findViewById(R.id.listv);
        final String[] items={};

        arrayList=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,arrayList);
        listView.setAdapter(adapter);
        txtInput=(EditText)findViewById(R.id.txtinput);


        Button btAdd=(Button)findViewById(R.id.btadd);
        btAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String newItem=txtInput.getText().toString();
                arrayList.add(newItem);
                adapter.notifyDataSetChanged();
                txtInput.setText("");

            }
        });



        findViewById(R.id.createGrp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GroupCreation.class);
                intent.putExtra("array_list",arrayList);
                startActivity(intent);
            }
        });


    }
}
