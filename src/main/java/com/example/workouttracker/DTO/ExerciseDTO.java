package com.example.workouttracker.DTO;

import com.example.workouttracker.Enum.Category;
import com.example.workouttracker.Enum.MuscleGroup;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ExerciseDTO {
    Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Category category;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(List<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    List<MuscleGroup> muscleGroups;
}
