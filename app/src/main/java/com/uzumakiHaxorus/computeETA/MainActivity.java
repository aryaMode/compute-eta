package com.uzumakiHaxorus.computeETA;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

import static java.lang.StrictMath.round;


public class MainActivity extends AppCompatActivity {
    EditText txtSpeed,txtSize,txtDownloaded;
    TextView txtDisplay;
    int pos;
    Spinner spnSpeed,spnSize,spnDownloaded;
    double speedUnit,sizeUnit;
    double time,speed,size,downloaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allocateMemory();
        setEvents();
        addItemsInSpinner();
    }

    private void addItemsInSpinner() {
        String speedUnits[]=getResources().getStringArray(R.array.SpeedUnits);
        String sizeUnits[]=getResources().getStringArray(R.array.SizeUnits);
        String downloadedUnits[]=getResources().getStringArray(R.array.DownloadedUnits);
        ArrayAdapter<String> sizeAdapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,sizeUnits);
        ArrayAdapter<String> speedAdapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,speedUnits);
        ArrayAdapter<String> downloadedAdapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,downloadedUnits);
        spnSpeed.setAdapter(speedAdapter);
        spnSize.setAdapter(sizeAdapter);
        spnDownloaded.setAdapter(downloadedAdapter);
        spnSpeed.setSelection(4);
        spnSize.setSelection(1);
        spnDownloaded.setSelection(0);
    }

    private void setEvents() {
        spnSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 0:
                        speedUnit=Math.pow(2,43);
                        break;
                    case 1:
                        speedUnit=Math.pow(2,40);
                        break;
                    case 2:
                        speedUnit=Math.pow(2,33);
                        break;
                    case 3:
                        speedUnit=Math.pow(2,30);
                        break;
                    case 4:
                        speedUnit=Math.pow(2,23);
                        break;
                    case 5:
                        speedUnit=Math.pow(2,20);
                        break;
                    case 6:
                        speedUnit=Math.pow(2,13);
                        break;
                    case 7:
                        speedUnit=Math.pow(2,10);
                        break;
                    case 8:
                        speedUnit=Math.pow(2,3);
                        break;
                    case 9:
                        speedUnit=1;
                        break;
                }
                displayTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {
                    case 0:
                        sizeUnit=Math.pow(2,43);
                        break;
                    case 1:
                        sizeUnit=Math.pow(2,33);
                        break;
                    case 2:
                        sizeUnit=Math.pow(2,23);
                        break;
                    case 3:
                        sizeUnit=Math.pow(2,13);
                        break;
                    case 4:
                        sizeUnit=Math.pow(2,3);
                        break;
                }
                displayTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnDownloaded.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextWatcher watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txtDownloaded.getText().toString().equals("."))
                    txtDownloaded.setText("0.");
                if(txtSize.getText().toString().equals("."))
                    txtSize.setText("0.");
                if(txtSpeed.getText().toString().equals("."))
                    txtSpeed.setText("0.");
                displayTime();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        txtSpeed.addTextChangedListener(watcher);
        txtDownloaded.addTextChangedListener(watcher);
        txtSize.addTextChangedListener(watcher);


    }

    private void displayTime()
    {
        if(!txtSpeed.getText().toString().equals("") && !txtSize.getText().toString().equals(""))
        {

            size=Double.parseDouble(txtSize.getText().toString())*sizeUnit;
            speed=Double.parseDouble(txtSpeed.getText().toString())*speedUnit;
            switch(pos) {
                case 0:
                    downloaded = !txtDownloaded.getText().toString().equals("") ? Double.parseDouble(txtDownloaded.getText().toString()) * size / 100.0 : 0;
                    break;
                case 1:
                    downloaded = !txtDownloaded.getText().toString().equals("") ? Double.parseDouble(txtDownloaded.getText().toString()) * Math.pow(2, 43) : 0;
                    break;
                case 2:
                    downloaded = !txtDownloaded.getText().toString().equals("") ? Double.parseDouble(txtDownloaded.getText().toString()) * Math.pow(2, 33) : 0;
                    break;
                case 3:
                    downloaded = !txtDownloaded.getText().toString().equals("") ? Double.parseDouble(txtDownloaded.getText().toString()) * Math.pow(2, 23) : 0;
                    break;
                case 4:
                    downloaded = !txtDownloaded.getText().toString().equals("") ? Double.parseDouble(txtDownloaded.getText().toString()) * Math.pow(2, 13) : 0;
                    break;
                case 5:
                    downloaded = !txtDownloaded.getText().toString().equals("") ? Double.parseDouble(txtDownloaded.getText().toString()) * Math.pow(2, 3) : 0;
                    break;
            }
            time = (size-downloaded)/speed;
            if(speed==0 && size!=0)
                txtDisplay.setText("ETA:\nEndless...");
            else
                txtDisplay.setText(getFormatTime(time));
        }
        else
        {
            txtDisplay.setText("");
        }
    }

    private static String getFormatTime(double time)
    {
        int year= (int) (time/3600/24/365);
        time%=(3600*24*365);
        int week= (int) (time/3600/7/24);
        time%=3600*7*24;
        int day= (int) (time/3600/24);
        time%=3600*24;
        int hour= (int) (time/3600);
        time%=3600;
        int minute= (int) (time/60);
        time%=60;
        float second=(float) time;
        String format="";
        format+=year>0?year+(year==1?" year ":" years "):"";
        format+=week>0?week+(week==1?" week ":" weeks "):"";
        format+=day>0?day+(day==1?" day ":" days "):"";
        format+=hour>0?hour+(hour==1?" hour ":" hours "):"";
        format+=minute>0?minute+(minute==1?" minute ":" minutes "):"";
        format+=second>0?second+(second==1?" second ":" seconds "):"";

        if(format.equals(""))
        {
            format="0 seconds";
        }
        if(year>=2147483647) {
            format = "Too much time...";
        }
        format="ETA:\n"+format;

        return format;
    }

    private void allocateMemory() {
        txtSpeed=(EditText)findViewById(R.id.txtSpeed);
        txtSize=(EditText)findViewById(R.id.txtSize);
        txtDownloaded=(EditText)findViewById(R.id.txtDownloaded);
        txtDisplay=(TextView)findViewById(R.id.txtDisplay);
        spnSpeed=(Spinner)findViewById(R.id.spnSpeed);
        spnSize=(Spinner)findViewById(R.id.spnSize);
        spnDownloaded=(Spinner)findViewById(R.id.spnDownloaded);
    }
}
