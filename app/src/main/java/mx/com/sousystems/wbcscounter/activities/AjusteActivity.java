package mx.com.sousystems.wbcscounter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.com.sousystems.wbcscounter.R;

public class AjusteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuste);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
