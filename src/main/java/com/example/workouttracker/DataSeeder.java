package com.example.workouttracker;

import com.example.workouttracker.Entity.Exercise;
import com.example.workouttracker.Entity.User;
import com.example.workouttracker.Enum.Category;
import com.example.workouttracker.Enum.MuscleGroup;
import com.example.workouttracker.Enum.UserRole;
import com.example.workouttracker.Repository.ExerciseRepository;
import com.example.workouttracker.Repository.UserRepository;
import com.example.workouttracker.Repository.WorkoutScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public DataSeeder(ExerciseRepository exerciseRepository, UserRepository userRepository, WorkoutScheduleRepository workoutScheduleRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        chargeExercises();
    }

    private void chargeExercises(){
        if (this.exerciseRepository.count() == 0) {
            // --- CHEST ---
            createExercise("Push Up", "Classic bodyweight push-ups.", Category.STRENGTH,
                    List.of(MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDER));

            createExercise("Bench Press", "Barbell bench press.", Category.STRENGTH,
                    List.of(MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDER));

            createExercise("Dumbbell Fly", "Dumbbell chest flys.", Category.STRENGTH,
                    List.of(MuscleGroup.CHEST));

            // --- BACK ---
            createExercise("Pull Up", "Pull-ups on the bar.", Category.STRENGTH,
                    List.of(MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.FOREARM));

            createExercise("Deadlift", "Conventional deadlift.", Category.STRENGTH,
                    List.of(MuscleGroup.BACK, MuscleGroup.HAMSTRINGS, MuscleGroup.LOWERBACK, MuscleGroup.TRAP));

            createExercise("Bent Over Row", "Bent-over barbell row.", Category.STRENGTH,
                    List.of(MuscleGroup.BACK, MuscleGroup.BICEPS));

            // --- LEGS ---
            createExercise("Squat", "Barbell back squat.", Category.STRENGTH,
                    List.of(MuscleGroup.QUADRICEPS, MuscleGroup.HAMSTRINGS, MuscleGroup.LOWERBACK));

            createExercise("Lunge", "Walking lunges.", Category.STRENGTH,
                    List.of(MuscleGroup.QUADRICEPS, MuscleGroup.HAMSTRINGS));

            createExercise("Calf Raise", "Standing calf raises.", Category.STRENGTH,
                    List.of(MuscleGroup.HAMSTRINGS)); // Using Hamstrings as closest posterior leg group in Enum

            // --- ARMS & SHOULDERS ---
            createExercise("Overhead Press", "Standing military press.", Category.STRENGTH,
                    List.of(MuscleGroup.SHOULDER, MuscleGroup.TRICEPS));

            createExercise("Bicep Curl", "Barbell bicep curl.", Category.STRENGTH,
                    List.of(MuscleGroup.BICEPS, MuscleGroup.FOREARM));

            createExercise("Tricep Dip", "Tricep dips on parallel bars.", Category.STRENGTH,
                    List.of(MuscleGroup.TRICEPS, MuscleGroup.CHEST));

            // --- CARDIO ---
            createExercise("Running", "Moderate pace running.", Category.CARDIO,
                    List.of(MuscleGroup.QUADRICEPS, MuscleGroup.HAMSTRINGS));

            createExercise("Jump Rope", "Jumping rope.", Category.CARDIO,
                    List.of(MuscleGroup.HAMSTRINGS, MuscleGroup.SHOULDER));

            // --- FLEXIBILITY ---
            createExercise("Yoga Flow", "Vinyasa yoga session.", Category.FLEXIBILITY,
                    List.of(MuscleGroup.BACK, MuscleGroup.HAMSTRINGS, MuscleGroup.SHOULDER));

            System.out.println(">>> DATA SEEDER: Exercises loaded successfully.");
            // User
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setFirstName("adminFirst");
            admin.setLastName("adminLast");
            admin.setRole(UserRole.ADMIN);
            admin.setEmail("admin@gmail.com");
            this.userRepository.save(admin);
            System.out.println(">>> DATA SEEDER: Admin loaded successfully.");

        }
    }
    private void createExercise(String name, String description, Category category, List<MuscleGroup> muscleGroups) {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setCategory(category);
        exercise.setMuscleGroups(muscleGroups);

        this.exerciseRepository.save(exercise);
    }
}

