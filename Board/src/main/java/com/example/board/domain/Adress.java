package com.example.board.domain;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Adress {

	private String city;
	private String street;
	private String state;
	private String zipCode;

	public void updateAdress(String city, String street, String state, String zipCode) {
		this.city = city;
		this.street = street;
		this.state = state;
		this.zipCode = zipCode;
	}
