package com.example.workouttracker.Controller;

import com.example.workouttracker.DTO.WorkoutScheduleRequestDTO;
import com.example.workouttracker.DTO.WorkoutScheduleResponseDTO;
import com.example.workouttracker.Entity.Workout;
import com.example.workouttracker.Entity.WorkoutSchedule;
import com.example.workouttracker.Exceptions.ResourceNotFoundException;
import com.example.workouttracker.Mapper.WorkoutScheduleMapper;
import com.example.workouttracker.Repository.WorkoutScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/workout_schedule")
class WorkoutScheduleController {

    private final WorkoutScheduleRepository workoutScheduleRepository;
    private final WorkoutScheduleMapper workoutScheduleMapper;

    public WorkoutScheduleController(WorkoutScheduleRepository workoutScheduleRepository, WorkoutScheduleMapper workoutScheduleMapper) {
        this.workoutScheduleRepository = workoutScheduleRepository;
        this.workoutScheduleMapper = workoutScheduleMapper;
    }
    @PostFilter("filterObject.username == authentication.name")
    @GetMapping("")
    public List<WorkoutScheduleResponseDTO> getSchedules(Authentication authentication) {
        List<WorkoutSchedule> workoutSchedules = this.workoutScheduleRepository.findAll();
        List<Workout> workouts;
        List<Workout> sortedWorkouts;
        for (WorkoutSchedule workoutSchedule : workoutSchedules) {
            workouts = workoutSchedule.getWorkouts();

            sortedWorkouts = workouts.stream()
                    .sorted(Comparator
                            .comparing(Workout::getDayOfWeek)
                            .thenComparing(Workout::getStartTime)
                            .thenComparing(Workout::getEndTime))
                    .toList();
            workoutSchedule.setWorkouts(sortedWorkouts);
        }
        return this.workoutScheduleMapper.toResponseDTOList(workoutSchedules);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<WorkoutScheduleResponseDTO> addSchedule(@RequestBody WorkoutScheduleRequestDTO scheduleDTO) {
        WorkoutSchedule schedule = workoutScheduleMapper.toEntity(scheduleDTO);
        WorkoutScheduleResponseDTO outputSchedule = workoutScheduleMapper.toResponseDTO(
                this.workoutScheduleRepository.save(schedule)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(outputSchedule);
    }
    @PreAuthorize("authz.canAccessWorkoutSchedule(authentication, id)")
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutScheduleResponseDTO> updateSchedule(@PathVariable Long id, @RequestBody WorkoutScheduleRequestDTO scheduleDTO, Authentication authentication) {
        if (!this.workoutScheduleRepository.existsWorkoutScheduleById(id) || !Objects.equals(id, scheduleDTO.getId())) {
            throw new ResourceNotFoundException("Workout Schedule Not Found");
        }
        WorkoutSchedule scheduleToSave = workoutScheduleMapper.toEntity(scheduleDTO);
        WorkoutScheduleResponseDTO outputSchedule = this.workoutScheduleMapper.toResponseDTO(this.workoutScheduleRepository.save(scheduleToSave));
        return ResponseEntity.status(HttpStatus.OK).body(outputSchedule);
    }
    @PreAuthorize("authz.canAccessWorkoutSchedule(authentication, id) || hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id, Authentication authentication) {
        this.workoutScheduleRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout Schedule deleted successfully");
    }

}
