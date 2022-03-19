package com.example.homework;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class HomeworkApplicationTests {

	Calculator calculatorTest = new Calculator();

	@Test
	void contextLoads() {

		int one = 10;
		int two = 20;

		int result = calculatorTest.add(one,two);

		assertThat(result).isEqualTo(30);
	}


	class Calculator{
		int add(int a,int b){
			return a+b;
		}
	}
}
