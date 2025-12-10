package com.example.workouttracker.Controller;

import com.example.workouttracker.DTO.WorkoutDTO;
import com.example.workouttracker.Entity.Exercise;
import com.example.workouttracker.Entity.Workout;
import com.example.workouttracker.Exceptions.ResourceNotFoundException;
import com.example.workouttracker.Mapper.WorkoutMapper;
import com.example.workouttracker.Repository.ExerciseRepository;
import com.example.workouttracker.Repository.UserRepository;
import com.example.workouttracker.Repository.WorkoutRepository;
import jakarta.annotation.Nullable;
import org.springframework.data.repository.query.Param;
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
    public List<WorkoutDTO> getWorkouts(Authentication authentication) {
        List<Workout> workouts = this.workoutRepository.findAll();
        return this.workoutMapper.workoutsToWorkoutDTOs(workouts);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<WorkoutDTO> addWorkout(@RequestBody WorkoutDTO workoutDTO) {
        if (workoutRepository.existsByWorkoutNameAndDayOfWeek(workoutDTO.getWorkoutName(), workoutDTO.getDayOfWeek())) {
            throw new ResourceNotFoundException("WORKOUT NOT FOUND");
        } else {
            WorkoutDTO savedDTO = manageCreation(workoutDTO,null);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedDTO);
        }
    }
    @PreAuthorize("authz.canAccessWorkout(authentication, id)")
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> modifyWorkout(@RequestBody WorkoutDTO workoutDTO, @PathVariable Long id) {
        if (!workoutRepository.existsByIdAndWorkoutNameAndDayOfWeek(id, workoutDTO.getWorkoutName(), workoutDTO.getDayOfWeek())) {
            throw new ResourceNotFoundException("WORKOUT NOT FOUND");
        } else {
            WorkoutDTO workout =  manageCreation(workoutDTO, id);
            return ResponseEntity.ok().body(workout);
        }
    }
    @PreAuthorize("authz.canAccessWorkout(authentication, id) || hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable Long id) {
        this.workoutRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("WORKOUT DELETED");
    }

    private WorkoutDTO manageCreation(WorkoutDTO workoutDTO, @RequestParam(required = false) Long id) {
        Workout workout = workoutMapper.workoutDTOToWorkout(workoutDTO);
        List<Exercise> exerciseList = this.exerciseRepository.findAllById(workoutDTO.getExercisesIds());
        workout.setExercises(exerciseList);
        if (id != null) {
            workout.setId(id);
        }
        workout.setUser(this.userRepository.findByUsername(workoutDTO.getUsername()));
        List<Long> dtoExercisesIds = workoutDTO.getExercisesIds();
        List<Exercise> exercises = new ArrayList<>();
        dtoExercisesIds.forEach(dtoExercisesId -> {
            exercises.add(this.exerciseRepository.findById(dtoExercisesId)
            .orElseThrow(() -> new ResourceNotFoundException("Exercise NOT FOUND")));
        });
        workout.setExercises(exercises);
        Workout savedWorkout = workoutRepository.save(workout);
        WorkoutDTO savedDTO = this.workoutMapper.workoutToWorkoutDTO(savedWorkout);
        savedDTO.setExercisesIds(workoutDTO.getExercisesIds());
        savedDTO.setUsername(workoutDTO.getUsername());
        return savedDTO;
    }
}
