package com.example.workouttracker.Mapper;

import com.example.workouttracker.DTO.WorkoutRequestDTO;
import com.example.workouttracker.DTO.WorkoutResponseDTO;
import com.example.workouttracker.Entity.Exercise;
import com.example.workouttracker.Entity.Workout;
import com.example.workouttracker.Repository.ExerciseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class WorkoutMapper {
    @Autowired
    private ExerciseRepository exerciseRepository;


    @Mapping(source = "user.username", target = "username")
    public abstract WorkoutResponseDTO workoutToWorkoutResponseDTO(Workout workout);
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "exercisesIds", target = "exercises")
    public abstract Workout workoutDTOToWorkout(WorkoutRequestDTO workoutRequestDTO);
    public abstract List<WorkoutResponseDTO> workoutsToWorkoutDTOs(List<Workout> workouts);

    protected List<Exercise> mapIdsToWorkouts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return exerciseRepository.findAllById(ids);
}
}
