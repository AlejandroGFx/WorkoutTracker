package com.example.workouttracker.Entity;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String workoutName;

    @Column
    @ManyToMany
    @JoinTable(
            name = "workout_exercises", // Nombre de la tabla puente

            // 1. joinColumns = "MI LADO" (Donde estoy escribiendo el código)
            // Conecta con la entidad dueña (Workout)
            joinColumns = @JoinColumn(name = "workout_id"),

            // 2. inverseJoinColumns = "EL LADO INVERSO" (El otro lado)
            // Conecta con la entidad de la lista (Exercise)
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises;

    @Column
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "user_id"))
    private User user;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    @PreUpdate
    private void validarIntervalo() {
        if (startTime != null && endTime != null) {
            if (!endTime.isAfter(startTime)) {
                throw new IllegalArgumentException("EndTime must be greater than StartTime");
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
