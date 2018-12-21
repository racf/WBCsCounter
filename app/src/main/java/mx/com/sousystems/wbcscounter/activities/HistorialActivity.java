package mx.com.sousystems.wbcscounter.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import mx.com.sousystems.wbcscounter.R;

public class HistorialActivity extends AppCompatActivity {
    DatePicker dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dp = findViewById(R.id.datePicker);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp.setOnDateChangedListener(dateChangedListener);
        }
    }

    private DatePicker.OnDateChangedListener dateChangedListener =
            new DatePicker.OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    Toast.makeText(HistorialActivity.this, "picked date is " + datePicker.getDayOfMonth() +
                            " / " + (datePicker.getMonth()+1) +
                            " / " + datePicker.getYear(), Toast.LENGTH_SHORT).show();

                }

            };


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
