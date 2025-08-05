package com.zoozooclub.pet.model.mapper;



import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.zoozooclub.pet.model.vo.Pet;

@Mapper
public interface PetMapper {

	ArrayList<Pet> petList(String memberNo);

	int savePet(Pet pet);
	
	
}
