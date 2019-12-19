package com.lucas.abas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.lucas.abas.R;
import com.lucas.abas.activity.fragment.EmAltaFragment;
import com.lucas.abas.activity.fragment.HomeFragment;
import com.lucas.abas.activity.fragment.InscricoesFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        smartTabLayout = findViewById(R.id.viewPagerTab);

        //configurações da action bar
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("You Tube");

        //Configurar abas
        FragmentPagerAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                .add("Home", HomeFragment.class)
                .add("Inscricoes", InscricoesFragment.class)
                .add("Em Alta", EmAltaFragment.class)
                .create()

        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);


    }
}
