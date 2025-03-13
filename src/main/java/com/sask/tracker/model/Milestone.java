package com.sask.tracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "milestone", schema = "sask")
public class Milestone {

    @Id
    private Long id;

    private int age;

    @Column(length = 2000)
    private String developmentMilestone;

    private String category;

    // Constructors
    public Milestone() {}

    public Milestone(int age, String developmentMilestone, String category) {
        this.age = age;
        this.developmentMilestone = developmentMilestone;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }


	public String getDevelopmentMilestone() {
		return developmentMilestone;
	}

	public void setDevelopmentMilestone(String developmentMilestone) {
		this.developmentMilestone = developmentMilestone;
	}

	public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
