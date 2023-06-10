package com.w.saffron.schdule;

/**
 * @author w
 * @since 2023/4/27
 */

public class ScheduleBuilder {

    private String second = "0";
    private String minute = "0";
    private String hour = "0";
    private String day = "*";
    private String month = "*";
    private String week = "?";
    private String year = "";

    private ScheduleBuilder() {
    }

    public static ScheduleBuilder getInstance() {
        return new ScheduleBuilder();
    }

    public static ScheduleBuilder getEveryHourSchedule(String h) {
        ScheduleBuilder scheduleBuilder = getInstance();
        scheduleBuilder.setHour("*/"+ h );
        return scheduleBuilder;
    }

    public static  ScheduleBuilder getEveryMinuteSchedule(int m) {
        ScheduleBuilder scheduleBuilder = getInstance();
        scheduleBuilder.setHour("*/"+m);
        scheduleBuilder.setHour("*");
        return scheduleBuilder;
    }

    public static  ScheduleBuilder getEverySecondSchedule(int s) {
        ScheduleBuilder scheduleBuilder = getInstance();
        scheduleBuilder.setSecond("*/"+s);
        scheduleBuilder.setMinute("*");
        scheduleBuilder.setHour("*");
        return scheduleBuilder;
    }


    public void setSecond(String s) {
        this.second = s;
    }

    public void setMinute(String m) {
        this.minute = m;
    }

    public void setHour(String h) {
        this.hour = h;
    }

    public ScheduleBuilder setDayOfMonth(String d) {
        this.day = d;
        this.month = "?";
        return this;
    }

    public ScheduleBuilder setMonth(String m) {
        this.month = m;
        return this;
    }

    public ScheduleBuilder setDayOfWeek(String w) {
        this.week = w;
        this.day = "?";
        return this;
    }

    public ScheduleBuilder setYear(String y) {
        this.year = y;
        return this;
    }

    public String getSchedule() {
        return (this.second + " " + this.minute + " " + this.hour + " " + this.day + " " + this.month + " " + this.week + " " + this.year).trim();
    }
}
