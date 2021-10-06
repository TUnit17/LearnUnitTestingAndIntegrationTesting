package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//import static org.junit.jupiter.api.Assertions.*; // going to use assertJ

@ExtendWith(MockitoExtension.class) // ?
class StudentServiceTest {

    // why Mock StudentRepo?
        // 1. since I already did a unit test on the StudentRepository interface, I know it is already working
        // 2. no need to autowire
    @Mock
    private StudentRepository mockStudentRepository;

    private StudentService underTest;



    @BeforeEach
    void setUp() {
        underTest = new StudentService(mockStudentRepository);
    }




    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();
        // then
        verify(mockStudentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);
        // when
        underTest.addStudent(student);

        // then

        // ?
        // argument captor allows us to capture an argument passed to a method in order to inspect it
        // useful when we can't access the argument outside the method we'd like to test

        // TIM COMMENTS: when I capture the arguement what is the type? Student
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        // ?
        // TIM COMMENTS: verify the mockStudentRepo was called/saved
        // then capture that value
        verify(mockStudentRepository).save(studentArgumentCaptor.capture());

        // extract/get the value
        Student capturedStudent = studentArgumentCaptor.getValue();

        // assert/compare the objects
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);

//        given(mockStudentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        given(mockStudentRepository.selectExistsEmail(anyString())).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        // verify that the following is never called
        // addStudent() => studentRepository.save(student);
        verify(mockStudentRepository, never()).save(any());
    }

    @Test
    @Disabled
    void deleteStudent() {
    }

}