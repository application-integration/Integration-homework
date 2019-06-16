package com.nju.edu.filmintegration.model;

public class FilmPredict {
	private String filmName;
	private double predict;
	
	public FilmPredict(String fileName, double predict) {
		this.filmName = fileName;
		this.predict = predict;
	}
	
	public String getFilmName() {
		return filmName;
	}
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	public double getPredict() {
		return predict;
	}
	public void setPredict(double predict) {
		this.predict = predict;
	}
	
	
}
