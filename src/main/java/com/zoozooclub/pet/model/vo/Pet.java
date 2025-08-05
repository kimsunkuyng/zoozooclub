package com.zoozooclub.pet.model.vo;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Pet {
	private int petNo;
	private String petName;
	private String petType;
	private String petCategory;
	private Date petBirthDay;
	private String gender;
	private String petNote;
	private String memberNo;
	private String petImage;
	private String neutered;
	private String petWeight;

}