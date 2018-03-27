package fr.kous92.menusidebartest;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle_bar;
    private TextView textView;
    List<Fragment> activeCenterFragments = new ArrayList<Fragment>();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle_bar = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        textView = (TextView) findViewById(R.id.txt1);

        drawerLayout.addDrawerListener(toggle_bar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_menu);
        toggle_bar.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpDrawerContent(navigationView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Le bouton (en forme de burger va faire afficher le menu)
        if (toggle_bar.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Quand on choisit un élément du menu
    public void selectItemDrawer(MenuItem menuItem)
    {
        Fragment fragment = null;
        // Class classe_fragment;

        switch (menuItem.getItemId())
        {
            case R.id.nav_account:
            // classe_fragment = Account.class;
            fragment = new Account();
            break;

            case R.id.nav_settings:
            // classe_fragment = Settings.class;
            fragment = new Settings();
            break;

            case R.id.nav_about:
            // classe_fragment = About.class;
            fragment = new About();
            break;

            case R.id.nav_main:
            // classe_fragment = MainActivity.class;
            fragment = null;
            break;

            default:
            // classe_fragment = About.class;
            fragment = null;
            break;
        }

        System.out.println(">>> Cible: " + menuItem.getItemId());

        fragmentManager = getSupportFragmentManager();

        if (fragment != null)
        {
            // On cible le fragment
            try
            {
                // fragment = (Fragment) classe_fragment.newInstance();
                addCenterFragments(fragment);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            removeActiveCenterFragments();
        }

        // fragmentManager = getSupportFragmentManager();
        // fragmentTransaction = fragmentManager.beginTransaction();

        /*
        if (menuItem.getTitle() == "MainActivity")
        {
            // fragmentTransaction.remove(fragment);
            // fragmentManager.beginTransaction().remove(fragment).commit();

            try
            {
                if (fragmentManager.getFragments() != null)
                {

                    if (fragmentManager.getBackStackEntryCount() > 0)
                    {
                        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++)
                        {
                            fragmentManager.popBackStack();
                        }

                        fragmentManager.beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.linearLayout1)).commit();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.linearLayout1, fragment);
            activeCenterFragments.add(fragment);
            fragmentTransaction.commit();

            // fragmentManager.beginTransaction().replace(R.id.linearLayout1, fragment).commit();
        }
        */

        // getActivity().getSupportFragmentManager().popBackStack();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawers();
    }

    private void setUpDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    private void removeActiveCenterFragments()
    {
        if (activeCenterFragments.size() > 0)
        {
            fragmentTransaction = fragmentManager.beginTransaction();

            for (Fragment activeFragment : activeCenterFragments)
            {
                System.out.println(">>> Cible à supprimer: " + activeFragment.toString());
                fragmentTransaction.remove(activeFragment);
            }

            activeCenterFragments.clear();
            fragmentTransaction.commit();
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void addCenterFragments(Fragment fragment)
    {
        System.out.println(">>> Cible: " + fragment.toString());
        textView.setVisibility(View.INVISIBLE);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.linearLayout1, fragment);
        activeCenterFragments.add(fragment);
        fragmentTransaction.commit();
    }
}
