package com.example.workouttracker.Mapper;

import com.example.workouttracker.DTO.WorkoutScheduleDTO;
import com.example.workouttracker.Entity.WorkoutSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutScheduleMapper {
    @Mapping(source = "user.username", target = "username")
    public WorkoutScheduleDTO workoutScheduleToWorkoutScheduleDTO(WorkoutSchedule workoutSchedule);
    @Mapping(target = "user", ignore = true)
    public WorkoutSchedule workoutScheduleDTOToWorkoutSchedule(WorkoutScheduleDTO workoutScheduleDTO);
public List<WorkoutScheduleDTO> workoutScheduleListToWorkoutScheduleDTOList(List<WorkoutSchedule> workoutSchedules);
}
