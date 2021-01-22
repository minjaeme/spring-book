package me.minjae.learningtest.junit;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;


import org.junit.Test;

// 2-24 JUnit Test
public class JUnitTest {
	static JUnitTest testObject;
	
	@Test
	public void Test1() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	
	@Test
	public void Test2() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
	
	@Test
	public void Test3() {
		assertThat(this, is(not(sameInstance(testObject))));
		testObject = this;
	}
}
