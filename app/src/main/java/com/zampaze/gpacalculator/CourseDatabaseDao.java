package com.zampaze.gpacalculator;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Defines methods for using the CourseDetail class with Room.
 */
@Dao
public interface CourseDatabaseDao {

    @Insert
    void insert(CourseDetail course);

    @Delete
    void delete(CourseDetail course);

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param course new value to write
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(CourseDetail course);

    /**
     * Selects and returns the row that matches the key.
     *
     * @param key key to match
     */
    @Query("SELECT * from course_detail_table WHERE cId = :key")
    CourseDetail get(long key);

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM course_detail_table")
    void clear();

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM course_detail_table ORDER BY cId DESC")
    List<CourseDetail> getAllCourses();

    /**
     * Selects and returns the latest course.
     */
    @Query("SELECT * FROM course_detail_table ORDER BY cId DESC LIMIT 1")
    CourseDetail getLastEntered();

    /**
     * Selects and returns the course with given courseId.
     */
    @Query("SELECT * from course_detail_table WHERE cId = :key")
    CourseDetail getCourseWithId(Long key);
}
