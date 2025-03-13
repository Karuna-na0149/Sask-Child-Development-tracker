package com.sask.tracker.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "child_profile", schema = "sask")
public class ChildProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int age;

    @Column(nullable = false)
    private Long parentId;

    @Column(nullable = false)
    private String parentEmail;

    @ManyToMany
    @JoinTable(
            name = "child_profile_milestone",
            joinColumns = @JoinColumn(name = "child_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "milestone_id")
    )
    private Set<Milestone> milestones = new HashSet<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public Set<Milestone> getMilestones() { return milestones; }
    public void setMilestones(Set<Milestone> milestones) { this.milestones = milestones; }
}
