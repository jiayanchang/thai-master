package com.magic.thai;

import java.util.Random;

import org.junit.Test;

public class TestTemp {

	@Test
	public void test() {
		for (int i = 0; i < 100; i++) {
			System.out.println(new Random().nextInt(100));
		}
	}

}