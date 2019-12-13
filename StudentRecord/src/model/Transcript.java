package model;

import exceptions.CourseFullException;
import exceptions.GPATooLowException;
import exceptions.MissingPrereqException;
import exceptions.NoCoursesTakenException;
import model.exceptions.NoCookException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Transcript {

    private String name;
    private int year;
    private int id;
    private double gpa;
    private List<Course> currentCourses;
    private List<Course> pastCourses;

    public Transcript(String studentName, int academicYear, int studentID) {
        this.name = studentName;
        this.year = academicYear;
        this.id = studentID;
        currentCourses = new ArrayList<>();
        pastCourses = new ArrayList<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public int getAcademicYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public List<Course> getCurrentCourses() {
        return currentCourses;
    }

    public List<Course> getPastCourses() {
        return pastCourses;
    }

    // EFFECTS: computes cGPA
    //          if the list of past courses is empty, throws NoCoursesTakenException
    public double computeGPA() throws NoCoursesTakenException {
        //          HINTS:
        //          use the following formula to convert into a GPA
        //          GPA (4.0 scale) = (total percentage/20) - 1
        //          **Do you need a helper method?**

        if (pastCourses.size() > 0) {
            double percentage = (getTotalPercentage() / pastCourses.size());

            return Math.round(percentToGPA(percentage) * 1000) / 1000.0;
        }

        throw new NoCoursesTakenException("No courses taken!");
    }

    private double getTotalPercentage() {
        return pastCourses.stream()
                .map(Course::getGrade)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    // helper for computeGPA
    private double percentToGPA(double pcnt) {
        return (pcnt / 20) - 1;
    }

    // EFFECTS: promotes the student represented by the transcript
    //          to the next academic year if GPA is over 2.6 on a 4.0 scale, and return true
    //          else return false with no change to the year field
    //          if GPA is not over 2.6 on a 4.0 scale, throws GPATooLowException
    //          if no courses have been taken, throws NoCoursesTakenException
    public boolean promoteStudent() throws GPATooLowException, NoCoursesTakenException {
        try {
            if (computeGPA() <= 2.6) {
                throw new GPATooLowException("GPA too low");
            }
        } catch (NoCoursesTakenException e) {
            throw e;
        }
        year += 1;
        return true;
    }


    // MODIFIES: this
    // EFFECTS: adds the given course to the list of past courses and returns true,
    //          unless pastCourses contains given course, then does not add and returns false
    public boolean addToPastCourses(Course c) {
        if (pastCourses.contains(c)) {
            return false;
        }
        pastCourses.add(c);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: adds a course (c) into the record
    //          if the transcript is missing prerequisites, throws a MissingPrereqException
    //          if the course has no space for more students to register, throws a CourseFullException
    public boolean addCourse(Course course) throws MissingPrereqException, CourseFullException {
        // You have to realize that there are a number of cases that your code needs to consider. What if the course
        // you wish to add has no prerequisites? What if the course you want to add is already full?
        // Careful consideration of these cases will lead to code that is correct
        if (!pastCourses.containsAll(course.getPrereq())) {
            throw new MissingPrereqException("not all pre-requirements satisfied");
        }

        if (course.isCourseFull()) {
            throw new CourseFullException("Course full");
        }

        currentCourses.add(course);
        return true;
    }
}
