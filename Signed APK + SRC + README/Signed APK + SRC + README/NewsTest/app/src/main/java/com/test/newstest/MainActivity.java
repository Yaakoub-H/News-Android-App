    package com.test.newstest;

    import android.os.Bundle;
    import android.view.MenuItem;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;

    import com.google.android.material.navigation.NavigationView;

    import java.util.HashMap;
    import java.util.Objects;

    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        private DrawerLayout drawerLayout;
        private ActionBarDrawerToggle actionBarDrawerToggle;
        private NavigationView navView;

        private HashMap<Integer, Integer> all;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {

            int[][] layouts = { {R.id.en_news, R.id.en_news} , {R.id.ar_news, R.id.ar_news} , {R.id.contact, R.layout.contact}, {R.id.album, R.layout.album} };

            all = new HashMap<Integer, Integer>();
            for (int[] layout : layouts)
                this.all.put(layout[0], layout[1]);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            drawerLayout = findViewById(R.id.drawer_layout);
            navView = findViewById(R.id.nav_view);
            navView.setNavigationItemSelectedListener(this);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (actionBarDrawerToggle.onOptionsItemSelected(item))
                return true;

            return super.onOptionsItemSelected(item);
        }
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            int layoutId = all.get(itemId);
            Fragment fragment = null;

            // Check if the menu item corresponds to a known fragment
            if (layoutId == R.id.en_news || layoutId == R.id.ar_news) {
                // News fragment
                fragment = new News_Fr(layoutId);
            } else if (layoutId == R.layout.contact) {
                // Contact fragment
                fragment = new Contact_fr(layoutId);
            } else if (layoutId == R.layout.album) {
                // Album fragment
                fragment = new Album_Fr(layoutId);
            }

            // Replace the current fragment with the selected fragment
            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.main_fragment, fragment);
                transaction.commit();
            }

            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }





    }
