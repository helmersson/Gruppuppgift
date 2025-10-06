package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.deca.InvalidResultException;

public class Hep200M {

	private int score;
	private double A = 4.99087;
	private double B = 42.5;
	private double C = 1.81;
	boolean active = true;
	CalcTrackAndField calc = new CalcTrackAndField();
	InputResult inputResult = new InputResult();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) throws InvalidResultException {

		if (runningTime < 14) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (runningTime > 42.08) {
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);

		System.out.println("The result is " + score);

		return score;
	}

}
