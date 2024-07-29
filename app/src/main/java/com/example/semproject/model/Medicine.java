package com.example.semproject.model;

public class Medicine {

    private String id;
    private String medicineName;
    private int day;
    private int month;
    private int year;
    private int noOfTimesPerDay;
    private int noOfDoses;
    private String timingList;
    private String reminderAlertType;

    public Medicine() {
    }

    public Medicine(String medicineName, int day, int month, int year, int noOfTimesPerDay, int noOfDoses, String timingList, String reminderAlertType) {
        this.medicineName = medicineName;
        this.day = day;
        this.month = month;
        this.year = year;
        this.noOfTimesPerDay = noOfTimesPerDay;
        this.noOfDoses = noOfDoses;
        this.timingList = timingList;
        this.reminderAlertType = reminderAlertType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNoOfTimesPerDay() {
        return noOfTimesPerDay;
    }

    public void setNoOfTimesPerDay(int noOfTimesPerDay) {
        this.noOfTimesPerDay = noOfTimesPerDay;
    }

    public int getNoOfDoses() {
        return noOfDoses;
    }

    public void setNoOfDoses(int noOfDoses) {
        this.noOfDoses = noOfDoses;
    }

    public String getTimingList() {
        return timingList;
    }

    public void setTimingList(String timingList) {
        this.timingList = timingList;
    }

    public String getReminderAlertType() {
        return reminderAlertType;
    }

    public void setReminderAlertType(String reminderAlertType) {
        this.reminderAlertType = reminderAlertType;
    }
}
