package com.example.workouttracker.DTO;

import java.util.List;

public class WorkoutScheduleRequestDTO {
    private Long id;
    private List<Long> workoutsIds;
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

    public List<Long> getWorkoutsIds() {
        return workoutsIds;
    }

    public void setWorkoutsIds(List<Long> workoutsIds) {
        this.workoutsIds = workoutsIds;
    }
}
