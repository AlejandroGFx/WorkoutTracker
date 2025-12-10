package com.example.workouttracker.Mapper;

import com.example.workouttracker.DTO.ExerciseDTO;
import com.example.workouttracker.Entity.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    public ExerciseDTO exerciseToExerciseDTO(Exercise exercise);
    public Exercise exerciseDTOToExercise(ExerciseDTO exerciseDTO);
}
