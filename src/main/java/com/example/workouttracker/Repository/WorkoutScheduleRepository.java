package com.example.workouttracker.Repository;

import com.example.workouttracker.Entity.WorkoutSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Long> {
    boolean existsWorkoutScheduleById(Long id);
}
