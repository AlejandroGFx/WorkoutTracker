package com.example.workouttracker.DTO;

import java.util.List;

public class WorkoutScheduleResponseDTO {
    private Long id;
    private List<WorkoutResponseDTO> workouts;
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

    public List<WorkoutResponseDTO> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<WorkoutResponseDTO> workouts) {
        this.workouts = workouts;
    }
}
