package com.example.android.moneysplitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alice on 31/8/16.
 */
public class GroupCreation extends Activity {

    LinearLayout linearMain;
    CheckBox checkBox;
    String payee;
    ArrayList<Integer> checkList = new ArrayList<Integer>();
    int x;
    ArrayList<String> arr;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_creation);

        Bundle b = getIntent().getExtras();


        if (b != null) {
            arr = (ArrayList<String>) b.getStringArrayList("array_list");
            System.out.println(arr);
            Spinner s = (Spinner) findViewById(R.id.dropList);
            s.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr));

            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(GroupCreation.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    payee = parent.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            linearMain = (LinearLayout) findViewById(R.id.linear_main);

            for (int i = 0; i < arr.size(); i++) {
                checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setText(arr.get(i));
                checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
                linearMain.addView(checkBox);
            }
        }

        findViewById(R.id.split_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String w = "";
                int n = arr.size();
                double a[][] = new double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        a[i][j] = 0;
                    }
                }


                for (int i = 0; i < arr.size(); i++) {
                    if (payee.equals(arr.get(i))) {
                        x = i;
                    }

                }


                EditText amount = (EditText) findViewById(R.id.editText);
                String money = amount.getText().toString();
                double amtd = Double.parseDouble(money);
                int row = x;


                int c = 0;


                for (int i = 0; i < linearMain.getChildCount(); i++) {
                    v = linearMain.getChildAt(i);
                    if (v instanceof CheckBox) {
                        if (((CheckBox) v).isChecked()) {
                            int checkBoxId = ((CheckBox) v).getId();
                            checkList.add(checkBoxId);
                            c = c + 1;
                        }
                    }
                }
                double share = (double) amtd / c;


                for (int i = 0; i < checkList.size(); i++) {

                    a[row][checkList.get(i)] = a[row][checkList.get(i)] + share;
                }


                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i != j) {
                            if (a[i][j] > a[j][i]) {
                                a[i][j] = a[i][j] - a[j][i];
                                a[j][i] = 0;
                            } else {
                                a[j][i] = a[j][i] - a[i][j];
                                a[i][j] = 0;
                            }
                        }
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            a[i][j] = 0;
                        }
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (a[i][j] != 0) {
                            TextView result_pri = (TextView) findViewById(R.id.result);
                            String one = arr.get(j).toString();
                            one.toUpperCase();
                            String two = arr.get(i).toString();
                            two.toUpperCase();
                            int fl = (int) a[i][j];
                            String three = String.valueOf(fl);
                            w = w + one + " owes " + two + " Rs." + three + "\n";

                            result_pri.setText(w);

                        }
                    }
                }

                w = "";
                checkList.clear();


            }


        });


    }

    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ON_CLICK", "CheckBox ID: " + button.getId() + " Text: " + button.getText().toString());
            }
        };
    }

    public void Back(View v) {
        Intent i = new Intent(GroupCreation.this, MainActivity.class);
        startActivity(i);
        finish();
    }


}

