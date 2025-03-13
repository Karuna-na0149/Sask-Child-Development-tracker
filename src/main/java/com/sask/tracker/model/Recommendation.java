package com.sask.tracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendation", schema = "sask")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String category;

    @Column(length = 2000)
    private String recommendation;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}
