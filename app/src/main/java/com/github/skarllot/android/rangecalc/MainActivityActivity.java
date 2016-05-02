package com.github.skarllot.android.rangecalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivityActivity extends AppCompatActivity {

    private static final int FRACTION_DIGITS = 4;

    private EditText txtValue1;
    private EditText txtValue2;
    private TextView lbl0Value;
    private TextView lbl25Value;
    private TextView lbl50Value;
    private TextView lbl75Value;
    private TextView lbl100Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtValue1 = (EditText) findViewById(R.id.txtValue1);
        txtValue2 = (EditText) findViewById(R.id.txtValue2);
        lbl0Value = (TextView) findViewById(R.id.lblValue0);
        lbl25Value = (TextView) findViewById(R.id.lblValue25);
        lbl50Value = (TextView) findViewById(R.id.lblValue50);
        lbl75Value = (TextView) findViewById(R.id.lblValue75);
        lbl100Value = (TextView) findViewById(R.id.lblValue100);

        TextWatcher calculateOnChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateRange();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        txtValue1.addTextChangedListener(calculateOnChange);
        txtValue2.addTextChangedListener(calculateOnChange);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;

        return false;
    }

    @Override
    protected void onDestroy() {
        lbl100Value = null;
        lbl75Value = null;
        lbl50Value = null;
        lbl25Value = null;
        lbl0Value = null;
        txtValue2 = null;
        txtValue1 = null;

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClearValue1(View view) {
        txtValue1.setText("");
        txtValue1.requestFocus();
    }

    public void onClearValue2(View view) {
        txtValue2.setText("");
        txtValue2.requestFocus();
    }

    public void calculateRange() {
        String value1 = txtValue1.getText().toString();
        String value2 = txtValue2.getText().toString();

        if (value1.length() == 0 ||
                value1.equals("-") ||
                value1.equals(".") ||
                value1.equals(",")) {
            value1 = "0";
        }
        if (value2.length() == 0 ||
                value2.equals("-") ||
                value2.equals(".") ||
                value2.equals(",")) {
            value2 = "0";
        }

        double iVal1, iVal2;
        try {
            iVal1 = Double.parseDouble(value1);
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivityActivity.this,
                    R.string.err_invalid_value1, Toast.LENGTH_SHORT).show();
            txtValue1.setText("");
            return;
        }
        try {
            iVal2 = Double.parseDouble(value2);
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivityActivity.this,
                    R.string.err_invalid_value2, Toast.LENGTH_SHORT).show();
            txtValue2.setText("");
            return;
        }

        double max, min, range;
        if (iVal1 > iVal2) {
            max = iVal1;
            min = iVal2;
        } else {
            max = iVal2;
            min = iVal1;
        }
        range = max - min;

        double p0, p25, p50, p75, p100;
        p0 = min;
        p25 = min + (.25 * range);
        p50 = min + (.5 * range);
        p75 = min + (.75 * range);
        p100 = max;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(FRACTION_DIGITS);
        df.setMinimumFractionDigits(FRACTION_DIGITS);


        lbl0Value.setText(df.format(p0));
        lbl25Value.setText(df.format(p25));
        lbl50Value.setText(df.format(p50));
        lbl75Value.setText(df.format(p75));
        lbl100Value.setText(df.format(p100));
    }
}
