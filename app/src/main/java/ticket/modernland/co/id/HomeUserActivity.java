package ticket.modernland.co.id;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeUserActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        String level = getIntent().getStringExtra("level");

        Toast.makeText(getApplicationContext(),
                "Welcome " + level, Toast.LENGTH_LONG).show();

        final DrawerLayout drawer1 = (DrawerLayout)
                findViewById(R.id.drawer);

        NavigationView Nav1 = (NavigationView)
                findViewById(R.id.nav);

        Nav1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menuHome) {


                } else if (menuItem.getItemId() == R.id.menuMy) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameUser, new MyTicketFragment())
                            .addToBackStack(null)
                            .commit();
                    drawer1.closeDrawers();

                } else if (menuItem.getItemId() == R.id.menuList) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameUser, new ListTicketFragment())
                            .addToBackStack(null)
                            .commit();
                    drawer1.closeDrawers();

                } else if (menuItem.getItemId() == R.id.menuLogout) {

                    getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                            .edit().clear().commit();

                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(i);

                } else if (menuItem.getItemId() == R.id.menuExit) {

                    AlertDialog.Builder ab =
                            new AlertDialog.Builder(HomeUserActivity.this);

                    ab.setTitle("Confirmation");
                    ab.setIcon(R.drawable.ic_check_black_24dp);
                    ab.setMessage("Are you sure to exit?");
                    ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    });
                    ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ab.show();
                }
                return false;
            }
        });


        toggle = new ActionBarDrawerToggle(
                HomeUserActivity.this,
                drawer1,
                (R.string.open),
                (R.string.close));

        drawer1.addDrawerListener(toggle);

        toggle.syncState();

        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuHome) {

            Toast.makeText(getApplicationContext(),
                    "Program Created by Jie Piranha",
                    Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.menuMy) {

            //Intent c = new Intent(getApplicationContext(),
            //        NewTicketActivity.class);
            //startActivity(c);

        } else if (item.getItemId() == R.id.menuList) {

            Intent c = new Intent(getApplicationContext(),
                    NewTicketActivity.class);
            startActivity(c);

        } else if (item.getItemId() == R.id.menuLogout) {

            getSharedPreferences("DATALOGIN", MODE_PRIVATE)
                    .edit().clear().commit();

            Intent i = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.menuChange) {


        } else if (item.getItemId() == R.id.menuExit) {

            AlertDialog.Builder ab =
                    new AlertDialog.Builder(HomeUserActivity.this);

            ab.setTitle("Confirmation");
            ab.setIcon(R.drawable.ic_check_black_24dp);
            ab.setMessage("Are you sure to exit?");
            ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ab.show();

        }

        toggle.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        getMenuInflater().inflate(R.menu.menu_user, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void prosesNewTicket(View view) {

        Intent c = new Intent(getApplicationContext(),
                NewTicketActivity.class);
        startActivity(c);
    }
}
