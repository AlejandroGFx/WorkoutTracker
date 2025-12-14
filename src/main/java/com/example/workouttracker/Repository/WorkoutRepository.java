package com.example.workouttracker.Repository;

import com.example.workouttracker.Entity.User;
import com.example.workouttracker.Entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllById(Long id);
    public Boolean existsByWorkoutNameAndDayOfWeek(String workoutName, DayOfWeek dayOfWeek);
    public Boolean existsByIdAndWorkoutNameAndDayOfWeek(Long id, String workoutName, DayOfWeek dayOfWeek);

    boolean existsByWorkoutName(String workoutName);

    boolean existsByWorkoutNameAndUser(String workoutName, User user);
}
