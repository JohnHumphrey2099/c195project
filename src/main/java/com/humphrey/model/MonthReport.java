package com.humphrey.model;

public class MonthReport {
    String month;
    int count;
    String type;

    public MonthReport(String month, String type, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public String getType(){return type;}

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
