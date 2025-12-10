package com.example.workouttracker.Repository;

import com.example.workouttracker.Entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    public Set<Exercise> findAllBy();
    public boolean existsByName(@Param("name") String name);
    public boolean existsByNameAndId(@Param("name") String name, @Param("id") Long id);
}
