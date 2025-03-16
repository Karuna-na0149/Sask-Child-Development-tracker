package com.sask.tracker.model;

public class ChartData {
    private String category;
    private int completed;

    public ChartData(String category, int completed) {
        this.category = category;
        this.completed = completed;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
