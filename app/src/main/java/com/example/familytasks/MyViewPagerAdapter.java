package com.example.familytasks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.familytasks.Fragmentos.MisTareasFragment;
import com.example.familytasks.Fragmentos.TareasGeneralesFragment;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new TareasGeneralesFragment());
        fragments.add(new MisTareasFragment());
        fragmentTitles.add("Tareas Generales");
        fragmentTitles.add("Mis Tareas");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
