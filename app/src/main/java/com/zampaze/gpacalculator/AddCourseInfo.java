package com.zampaze.gpacalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseInfo extends AppCompatActivity {

    CourseDatabase courseDB;
    SeekBar courseGradeBar;

    private String seekProgToGrade(int progress) {
        String letterGrade = "Error";

        if (progress == 11) {
            letterGrade = "A";
        } else if (progress == 10) {
            letterGrade = "A-";
        } else if (progress == 9) {
            letterGrade = "B+";
        } else if (progress == 8) {
            letterGrade = "B";
        } else if (progress == 7) {
            letterGrade = "B-";
        } else if (progress == 6) {
            letterGrade = "C+";
        } else if (progress == 5) {
            letterGrade = "C";
        } else if (progress == 4) {
            letterGrade = "C-";
        } else if (progress == 3) {
            letterGrade = "D+";
        } else if (progress == 2) {
            letterGrade = "D";
        } else if (progress == 1) {
            letterGrade = "D-";
        } else if (progress == 0) {
            letterGrade = "F";
        }

        return letterGrade;
    }

    public static double seekProgToGPA(double progress) {
        double classGPA = -1.0;

        if (progress == 11) {
            classGPA = 4.00;
        } else if (progress == 10) {
            classGPA = 3.70;
        } else if (progress == 9) {
            classGPA = 3.30;
        } else if (progress == 8) {
            classGPA = 3.00;
        } else if (progress == 7) {
            classGPA = 2.70;
        } else if (progress == 6) {
            classGPA = 2.30;
        } else if (progress == 5) {
            classGPA = 2.00;
        } else if (progress == 4) {
            classGPA = 1.70;
        } else if (progress == 3) {
            classGPA = 1.30;
        } else if (progress == 2) {
            classGPA = 1.00;
        } else if (progress == 1) {
            classGPA = 0.70;
        } else if (progress == 0) {
            classGPA = 0;
        }

        return classGPA;
    }

    public void addCourseInformation(View view) {
        // get name of course
        EditText courseNameText = findViewById(R.id.courseName);
        String courseName = courseNameText.getText().toString();

        // get credit amount
        EditText courseCreditsText = findViewById(R.id.courseCredits);
        String courseCreditsString = courseCreditsText.getText().toString();
        int courseCredits = 0;
        try {
            courseCredits = Integer.parseInt(courseCreditsString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // get grade of course
        SeekBar courseGradeBar = findViewById(R.id.courseGrade);
        int courseGrade = courseGradeBar.getProgress();

        // make CourseDetail object
        final CourseDetail newCourse = new CourseDetail(courseName, courseCredits, courseGrade);

        // add to database
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                courseDB.courseDatabaseDao().insert(newCourse);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_info);

        courseDB = CourseDatabase.getInstance(getApplicationContext());

        courseGradeBar = findViewById(R.id.courseGrade);

        final TextView gradeBox = findViewById(R.id.gradeView);

        if (courseGradeBar != null) {
            courseGradeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                     gradeBox.setText(seekProgToGrade(seekBar.getProgress()));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }
}
