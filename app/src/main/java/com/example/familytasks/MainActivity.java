package com.example.familytasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.familytasks.Fragmentos.MisTareasFragment;
import com.example.familytasks.Fragmentos.TareasGeneralesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
FirebaseUser currentUser;
FloatingActionButton fab;
ViewPager2 viewPager;
TabLayout tabLayout;
MyViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new MyViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
                if (position == 1) {
                    MisTareasFragment misTareasFragment = (MisTareasFragment) adapter.createFragment(position);
                    if (misTareasFragment != null) {
                        misTareasFragment.cargarDatos();
                    }
                } else if (position == 0) {
                    TareasGeneralesFragment tareasGeneralesFragment = (TareasGeneralesFragment) adapter.createFragment(position);
                    if(tareasGeneralesFragment != null){
                        tareasGeneralesFragment.cargarDatos();
                    }
                }
            }
        });
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            currentUser = mAuth.getCurrentUser();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        fab = findViewById(R.id.miFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    public void mandarTexto(String mensaje) {
        Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_fab);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleMenuItemClick(item.getItemId());
                return true;
            }
        });
        popupMenu.show();
    }
    private void handleMenuItemClick(int itemId) {
        int id = itemId;
        if(id == R.id.item1){
            startActivity(new Intent(MainActivity.this,nueva_tarea.class));
        }else if (id == R.id.item2) {
            startActivity(new Intent(MainActivity.this,Configuracion.class));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_fab,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }
}