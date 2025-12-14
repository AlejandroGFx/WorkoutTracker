package com.example.workouttracker.Controller;

import com.example.workouttracker.DTO.ExerciseDTO;
import com.example.workouttracker.Entity.Exercise;
import com.example.workouttracker.Mapper.ExerciseMapper;
import com.example.workouttracker.Repository.ExerciseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/exercises")
class ExerciseController {
    private final ExerciseMapper exerciseMapper;
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseMapper exerciseMapper, ExerciseRepository exerciseRepository) {
        this.exerciseMapper = exerciseMapper;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping()
    public ResponseEntity<Set<ExerciseDTO>> getAllExercises() {

        Set<Exercise> exercises = exerciseRepository.findAllBy();
        Set<ExerciseDTO> exerciseDTOs = new HashSet<>();
        for (Exercise exercise : exercises) {
            exerciseDTOs.add(exerciseMapper.exerciseToExerciseDTO(exercise));
        }
        return ResponseEntity.status(HttpStatus.OK).body(exerciseDTOs);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<ExerciseDTO> postExercise(@RequestBody ExerciseDTO exerciseDTO) {
         if (this.exerciseRepository.existsByName(exerciseDTO.getName())) {
             System.out.println("Exercise already exists");
             return ResponseEntity.badRequest().build();
         }else {
           Exercise exerciseToSave = exerciseMapper.exerciseDTOToExercise(exerciseDTO);
           Exercise saved = exerciseRepository.save(exerciseToSave);
           return  ResponseEntity.status(HttpStatus.CREATED).body(exerciseMapper.exerciseToExerciseDTO(saved));
         }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> put(@PathVariable Long id, @RequestBody ExerciseDTO exerciseDTO) {
        if (!this.exerciseRepository.existsByNameAndId(exerciseDTO.getName(), id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found");
        }
        Exercise exerciseToModify = this.exerciseMapper.exerciseDTOToExercise(exerciseDTO);
        exerciseRepository.save(exerciseToModify);
        return ResponseEntity.status(HttpStatus.OK).body(exerciseDTO);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable Long id) {
        this.exerciseRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Exercise deleted");
    }
}
