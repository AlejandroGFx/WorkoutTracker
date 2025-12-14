package com.example.workouttracker.Mapper;

import com.example.workouttracker.DTO.WorkoutScheduleRequestDTO;
import com.example.workouttracker.DTO.WorkoutScheduleResponseDTO;
import com.example.workouttracker.Entity.User;
import com.example.workouttracker.Entity.Workout;
import com.example.workouttracker.Entity.WorkoutSchedule;
import com.example.workouttracker.Repository.UserRepository;
import com.example.workouttracker.Repository.WorkoutRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = WorkoutMapper.class)
public abstract class WorkoutScheduleMapper {
    @Autowired
    protected WorkoutRepository workoutRepository;
    @Autowired
    protected UserRepository userRepository;

    protected List<Workout> mapIdsToWorkouts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return workoutRepository.findAllById(ids);
    }
    protected User mapUsernameToUser(String username) {
        if (username == null) {
            return null;
        }
        return userRepository.findByUsername(username);
    }

    @Mapping(target = "workouts", source = "workoutsIds")
    @Mapping(target = "user", source = "username")
    public abstract WorkoutSchedule toEntity(WorkoutScheduleRequestDTO workoutScheduleDTO);
    @Mapping(target = "username", source = "user.username")
    public abstract WorkoutScheduleResponseDTO toResponseDTO(WorkoutSchedule workoutSchedule);

    public abstract List<WorkoutScheduleResponseDTO> toResponseDTOList(List<WorkoutSchedule> workoutSchedules);
}
