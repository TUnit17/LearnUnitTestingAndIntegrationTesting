package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // autowire + spin updatabase
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    // after each test delete all entries
    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentExistsEmail() { // test to see if email gets saved to student object/database

        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);
        underTest.save(student);

        // when
        Boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentDoesNotExistsEmail() { // test to see if email gets saved to student object/database

        // given
        String email = "jamila@gmail.com";

        // when
        Boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isFalse();
    }
}