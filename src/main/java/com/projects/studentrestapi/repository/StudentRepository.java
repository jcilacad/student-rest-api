package com.projects.studentrestapi.repository;

import com.projects.studentrestapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.lastName = ?2")
    Student findByJPQL(String firstName, String lastName);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName")
    Student findByJPQLNamedParams(String firstName, String lastName);

    @Query(value = "SELECT * FROM students s WHERE s.first_name = ?1 AND s.last_name = ?2", nativeQuery = true)
    Student findByNativeSQL(String firstName, String lastName);

    @Query(value = "SELECT * FROM students s WHERE s.first_name = :firstName AND s.last_name = :lastName", nativeQuery = true)
    Student findByNativeSQLNamed(String firstName, String lastName);
}
