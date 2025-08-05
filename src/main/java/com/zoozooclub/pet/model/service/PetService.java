
package com.zoozooclub.pet.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zoozooclub.member.model.mapper.MemberMapper;
import com.zoozooclub.pet.model.mapper.PetMapper;
import com.zoozooclub.pet.model.vo.Pet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetService {
		
	private final PetMapper mapper;

	public ArrayList<Pet> petList(String memberNo) {
		return mapper.petList(memberNo);
	}

	public int savePet(Pet pet) {
		// TODO Auto-generated method stub
		return mapper.savePet(pet);
	}
}
