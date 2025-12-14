package com.example.workouttracker.Controller;

import com.example.workouttracker.DTO.WorkoutRequestDTO;
import com.example.workouttracker.DTO.WorkoutResponseDTO;
import com.example.workouttracker.Entity.Exercise;
import com.example.workouttracker.Entity.User;
import com.example.workouttracker.Entity.Workout;
import com.example.workouttracker.Exceptions.ResourceNotFoundException;
import com.example.workouttracker.Mapper.WorkoutMapper;
import com.example.workouttracker.Repository.ExerciseRepository;
import com.example.workouttracker.Repository.UserRepository;
import com.example.workouttracker.Repository.WorkoutRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workouts")
class WorkoutController {
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;
    private final UserRepository userRepository;

    public WorkoutController(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
        this.userRepository = userRepository;
    }

    @PostFilter("filterObject.username == authentication.name")
    @GetMapping("")
    public List<WorkoutResponseDTO> getWorkouts(Authentication authentication) {
        List<Workout> workouts = this.workoutRepository.findAll();
        return this.workoutMapper.workoutsToWorkoutDTOs(workouts);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<WorkoutResponseDTO> addWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO) {
        String workoutName = workoutRequestDTO.getWorkoutName();
        if (workoutRepository.existsByWorkoutNameAndDayOfWeek(workoutName, workoutRequestDTO.getDayOfWeek())) {
            throw new ResourceNotFoundException("WORKOUT ALREADY EXISTS");
        } else if (workoutRepository.existsByWorkoutName(workoutName)) {
            throw new ResourceNotFoundException("WORKOUT ALREADY EXISTS");
        } else {
            WorkoutResponseDTO savedDTO = manageCreation(workoutRequestDTO, null);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedDTO);
        }
    }

    @PreAuthorize("@authz.canAccessWorkout(#id, authentication)")
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> modifyWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO, @PathVariable Long id) {
        Workout workoutCmp = this.workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WORKOUT NOT FOUND"));
        User userCmp = this.userRepository.findByUsername(workoutRequestDTO.getUsername());
        String workoutName = workoutRequestDTO.getWorkoutName();
        if (this.workoutRepository.existsByWorkoutNameAndUser(workoutName, userCmp) && !workoutCmp.getWorkoutName().equals(workoutName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else {
            WorkoutResponseDTO workout = manageCreation(workoutRequestDTO, id);
            return ResponseEntity.ok().body(workout);
        }
    }

    @PreAuthorize("@authz.canAccessWorkout(#id, authentication) || hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable Long id) {
        this.workoutRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("WORKOUT DELETED");
    }

    private WorkoutResponseDTO manageCreation(WorkoutRequestDTO workoutRequestDTO, @RequestParam(required = false) Long id) {
        Workout workout = workoutMapper.workoutDTOToWorkout(workoutRequestDTO);
        List<Exercise> exerciseList = this.exerciseRepository.findAllById(workoutRequestDTO.getExercisesIds());
        workout.setExercises(exerciseList);
        if (id != null) {
            workout.setId(id);
        }
        workout.setUser(this.userRepository.findByUsername(workoutRequestDTO.getUsername()));
        List<Long> dtoExercisesIds = workoutRequestDTO.getExercisesIds();
        List<Exercise> exercises = this.exerciseRepository.findAllById(dtoExercisesIds);
        workout.setExercises(exercises);
        Workout savedWorkout = workoutRepository.save(workout);
        WorkoutResponseDTO savedDTO = this.workoutMapper.workoutToWorkoutResponseDTO(savedWorkout);
        savedDTO.setUsername(workoutRequestDTO.getUsername());
        return savedDTO;
    }
}
