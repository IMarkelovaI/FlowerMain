package com.example.floweraplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.floweraplication.Fragments.DodFllowFragment;
import com.example.floweraplication.Fragments.FlowFragment;
import com.example.floweraplication.Fragments.HomeFragment;
import com.example.floweraplication.R;
import com.example.floweraplication.databinding.ActivityUserBinding;
import com.example.floweraplication.models.ModelPurpose;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();

    FlowFragment flowFragment = new FlowFragment();
    DodFllowFragment dodFllowFragment = new DodFllowFragment();

    public ArrayList <ModelPurpose> purposeArrayList;
    //public ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //вью пэйджер 8:22
        //setupViewPagerAdapter(binding.viewPager);

        bottomNavigationView = binding.bottomNavigation;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int itemId = item.getItemId();

                if (itemId ==R.id.home)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                }
                else if (itemId ==R.id.add_flowers)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,dodFllowFragment).commit();
                }
                else if (itemId ==R.id.flowers)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,flowFragment).commit();
                }
                return true;
            }
        });
        //binding.bottomNavigationView.setBackground(null);
    }

    /*private void setupViewPagerAdapter(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);

        purposeArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Purpose");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                purposeArrayList.clear();

                ModelPurpose modelAll = new ModelPurpose("1","Все");
                purposeArrayList.add(modelAll);
                viewPagerAdapter.addFragment(FlowFragment.newInstance(""+modelAll.getId(),""+modelAll.getName()), modelAll.getName());
                viewPagerAdapter.notifyDataSetChanged();

                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPurpose model = ds.getValue(ModelPurpose.class);
                    purposeArrayList.add(model);
                    viewPagerAdapter.addFragment(FlowFragment.newInstance(""+model.getId(),""+model.getName()),modelAll.getName());
                    viewPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewPager.setAdapter(viewPagerAdapter);
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<FlowFragment> fragmentList = new ArrayList<>();
        private ArrayList<String> fragmentPurposeList = new ArrayList<>();
        private Context context;

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
            super(fm, behavior);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        private void addFragment(FlowFragment fragment,String purpose){
            fragmentList.add(fragment);
            fragmentPurposeList.add(purpose);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentPurposeList.get(position);
        }
    }*/

    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null) {
            //not logged in, goto main screen
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        else {
            //togged in, get user info
            //set in textview of toolbar
        }
    }

    /*private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }*/

}
