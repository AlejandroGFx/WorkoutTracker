package com.example.workouttracker.Security;

import com.example.workouttracker.Entity.User;
import com.example.workouttracker.Entity.Workout;
import com.example.workouttracker.Exceptions.ResourceNotFoundException;
import com.example.workouttracker.Repository.UserRepository;
import com.example.workouttracker.Repository.WorkoutRepository;
import com.example.workouttracker.Repository.WorkoutScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authz")
@Transactional
class AuthorizationService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutScheduleRepository workoutScheduleRepository;

    public AuthorizationService(UserRepository userRepository, WorkoutRepository workoutRepository, WorkoutScheduleRepository workoutScheduleRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.workoutScheduleRepository = workoutScheduleRepository;
    }

    public Boolean canAccessWorkout(Long workoutId, Authentication authentication) {
        return workoutRepository.findById(workoutId)
                .map(workout -> workout.getUser().getUsername().equals(authentication.getName()))
                .orElse(false);
    }
    public Boolean canAccessWorkoutSchedule(Long workoutScheduleId, Authentication authentication) {
        return workoutScheduleRepository.findById(workoutScheduleId)
                .map(workout -> workout.getUser().getUsername().equals(authentication.getName()))
                .orElse(false);
    }
}
