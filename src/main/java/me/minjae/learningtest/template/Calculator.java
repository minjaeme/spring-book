package me.minjae.learningtest.template;

import java.io.BufferedReader;
import java.io.IOException;

public class Calculator {
	public Integer calcSum (String filepath) throws IOException {
		BufferedReader br = new BufferedReader(getClass().getResource(filepath));
	}
}
