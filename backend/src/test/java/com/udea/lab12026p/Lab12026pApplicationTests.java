package com.udea.lab12026p;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class Lab12026pApplicationTests {

	@Test
	void applicationClassShouldBeAnnotatedAsSpringBootApplication() {
		assertTrue(Lab12026pApplication.class.isAnnotationPresent(SpringBootApplication.class));
	}

}
