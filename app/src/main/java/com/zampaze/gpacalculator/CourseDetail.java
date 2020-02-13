package com.zampaze.gpacalculator;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_detail_table")
public class CourseDetail {

    @PrimaryKey(autoGenerate = true)
    long cId = 0;

    @ColumnInfo(name = "credits")
    int credits = 0;

    @ColumnInfo(name = "name")
    String cname = "none";

    @ColumnInfo(name = "grade")
    double cgrade = 0.0;

    @Override
    public String toString() {
        return "Name: " + cname + "\t|\tCredits: " + credits + "\t|\tGrade: " + intGPAToLetter(cgrade);
    }

    // constructor
    @Ignore
    public CourseDetail(String cname, int credits, double cgrade) {
        this.cname = cname;
        this.credits = credits;
        this.cgrade = cgrade;
    }

    public CourseDetail(long cId, String cname, int credits, double cgrade) {
        this.cId = cId;
        this.cname = cname;
        this.credits = credits;
        this.cgrade = cgrade;
    }

    @Ignore
    // Method converts letter grade to Miami University GPA scale
    public String intGPAToLetter(double gpa) {
        String letterGrade = "Error";

        if (gpa == 11) {
            letterGrade = "A";
        } else if (gpa == 10) {
            letterGrade = "A-";
        } else if (gpa == 9) {
            letterGrade = "B+";
        } else if (gpa == 8) {
            letterGrade = "B";
        } else if (gpa == 7) {
            letterGrade = "B-";
        } else if (gpa == 6) {
            letterGrade = "C+";
        } else if (gpa == 5) {
            letterGrade = "C";
        } else if (gpa == 4) {
            letterGrade = "C-";
        } else if (gpa == 3) {
            letterGrade = "D+";
        } else if (gpa == 2) {
            letterGrade = "D";
        } else if (gpa == 1) {
            letterGrade = "D-";
        } else if (gpa == 0) {
            letterGrade = "F";
        }

        return letterGrade;
    }
}
