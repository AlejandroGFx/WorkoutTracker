package com.example.workouttracker.Entity;


import com.example.workouttracker.Enum.Category;
import com.example.workouttracker.Enum.MuscleGroup;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.List;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = MuscleGroup.class)
    @CollectionTable(
            name = "excercise_muscle_groups", joinColumns = @JoinColumn(name = "exercise_id")
    )
    @Enumerated(EnumType.STRING)
    @Column
    private List<MuscleGroup> muscleGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(List<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
