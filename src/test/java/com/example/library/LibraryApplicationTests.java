package com.example.library;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Requires MySQL database")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LibraryApplicationTests {

	@Test
	void contextLoads() {
	}

}