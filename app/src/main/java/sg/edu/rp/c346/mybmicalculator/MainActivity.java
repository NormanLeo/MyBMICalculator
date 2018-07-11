package sg.edu.rp.c346.mybmicalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBMI, tvSypnosis;

    @Override
    protected void onPause() {
        super.onPause();

        Calendar now = Calendar.getInstance();
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);
        float w = Float.parseFloat(etWeight.getText().toString());
        float h = Float.parseFloat(etHeight.getText().toString());
        float output = w/(h*h);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Date", datetime);
        prefEdit.putFloat("BMI", output);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String msg = prefs.getString("Date", "");
        float BMI = prefs.getFloat("BMI", 0);
        tvDate.setText("Last Calculated Date:"+msg);
        tvBMI.setText("Last Calculated BMI:"+BMI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalc);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvSypnosis = findViewById(R.id.textViewSypnosis);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText("Last Calculated Date:"+datetime);
                if (etWeight != null) {
                    float w = Float.parseFloat(etWeight.getText().toString());
                    if (etHeight != null) {
                        float h = Float.parseFloat(etHeight.getText().toString());

                        float output = w/(h*h);
                        tvBMI.setText("Last Calculated BMI:"+output);
                        if (output < 18.5) {
                            tvSypnosis.setText("You are underweight");
                        }
                        else if (output <= 24.9) {
                            tvSypnosis.setText("Your BMI is normal");
                        }
                        else if (output <= 29.9) {
                            tvSypnosis.setText("You are overweight");
                        }
                        else if (output >= 30){
                            tvSypnosis.setText("You are obese");
                        }
                        else {
                            tvSypnosis.setText("");
                        }
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Input!", Toast.LENGTH_SHORT);
                    }
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Input!", Toast.LENGTH_SHORT);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(null);
                etHeight.setText(null);
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();
            }
        });
    }
}
