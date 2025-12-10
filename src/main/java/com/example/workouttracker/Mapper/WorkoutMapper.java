package com.example.workouttracker.Mapper;

import com.example.workouttracker.DTO.WorkoutDTO;
import com.example.workouttracker.Entity.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {
    @Mapping(source = "user.username", target = "username")
    public WorkoutDTO workoutToWorkoutDTO(Workout workout);
    @Mapping(target = "user", ignore = true)
    public Workout workoutDTOToWorkout(WorkoutDTO workoutDTO);
    public List<WorkoutDTO> workoutsToWorkoutDTOs(List<Workout> workouts);
}
