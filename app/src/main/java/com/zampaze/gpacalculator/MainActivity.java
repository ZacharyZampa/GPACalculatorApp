package com.zampaze.gpacalculator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    CourseDatabase courseDB;
    ListView listView;
    ArrayAdapter<CourseDetail> arrayAdapter;
    TextView gpaTotal;

    public void initList() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<CourseDetail> cList = courseDB.courseDatabaseDao().getAllCourses();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayAdapter = new ArrayAdapter<CourseDetail>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, cList);
                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getApplicationContext(), "Hold to delete item",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                deleteCourse(cList.get(position));
                                return true;
                            }
                        });

                        arrayAdapter.notifyDataSetChanged();

                        // get GPA
                        double gpa = 0.0;
                        int creditTotal = 0;

                        if (cList.isEmpty()) {
                            gpaTotal.setText("4.0");
                            return;
                        }

                        for(CourseDetail course : cList)
                        {
                            gpa += AddCourseInfo.seekProgToGPA(course.cgrade) * course.credits;
                            creditTotal += course.credits;
                        }

                        gpa = gpa / creditTotal;
                        gpaTotal.setText(String.format("%.2f", gpa));
                    }
                });
            }
        });
    }

    public void clearCourses() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                courseDB.courseDatabaseDao().clear();
            }
        });
        initList();
    }

    public void deleteCourse(final CourseDetail item) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                courseDB.courseDatabaseDao().delete(item);
            }
        });
        initList();
    }


    // create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // when someone selects an item in the options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addCourse:
                Log.i("Item selected", "Add CourseDetail");
                Intent addCourseIntent = new Intent(MainActivity.this, AddCourseInfo.class);
                startActivity(addCourseIntent);
                return true;
            case R.id.clearCourses:
                Log.i("Item Selected", "Clear Courses");
                clearCourses();
                return true;
            case R.id.about:
                Log.i("Item selected", "About");
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpaTotal = findViewById(R.id.gpaTotalText);

        // clear the name from the action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        courseDB = CourseDatabase.getInstance(getApplicationContext());
        listView = findViewById(R.id.listView);

        initList();  // initialize list of courses
    }

    @Override
    protected void onResume() {
        super.onResume();

        initList();  // initialize list of courses
    }
}
