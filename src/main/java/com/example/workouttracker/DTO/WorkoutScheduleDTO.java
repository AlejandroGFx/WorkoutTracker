package com.example.workouttracker.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class WorkoutScheduleDTO {
    private Long id;
    private List<WorkoutDTO> workouts;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WorkoutDTO> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<WorkoutDTO> workouts) {
        this.workouts = workouts;
    }
}
