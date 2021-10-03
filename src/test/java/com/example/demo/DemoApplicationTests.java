package com.example.demo;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
class DemoApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		// given
		int numberOne = 1;
		int numberTwo = 2;

		// when
		int actual= underTest.add(numberOne,numberTwo);

		// then
		int expected = 3;
		assertEquals(expected,actual); // jUnit
		assertThat(actual).isEqualTo(expected); //assertJ
	}

	static class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}

}
