package mx.com.sousystems.wbcscounter.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import mx.com.sousystems.wbcscounter.R;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationDrawer();
        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_counter:
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.menu_patient:
                        drawerLayout.closeDrawers();
                        Intent intentPaciente = new Intent(getApplicationContext(), PacienteActivity.class);
                        startActivity(intentPaciente);
                        return true;
                    case R.id.menu_record:
                        drawerLayout.closeDrawers();
                        Intent intentHistorial = new Intent(getApplicationContext(), HistorialActivity.class);
                        startActivity(intentHistorial);
                        return true;
                    case R.id.menu_setting:
                        drawerLayout.closeDrawers();
                        Intent intentAjuste = new Intent(getApplicationContext(), AjusteActivity.class);
                        startActivity(intentAjuste);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * Funcion para cargar  el icono del Drawer en la barra tool
     */
    private void setNavigationDrawer(){
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_dark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                 drawerLayout.openDrawer(Gravity.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
