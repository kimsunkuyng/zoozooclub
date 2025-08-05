package com.zoozooclub.pet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zoozooclub.member.model.vo.Member;
import com.zoozooclub.pet.model.service.PetService;
import com.zoozooclub.pet.model.vo.Pet;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PetController {
	
	private final PetService pService;
	
	@GetMapping("pet/setting")
    public String setting(Model model) {
		model.addAttribute(model);
		System.out.println(model);
        return "views/member/petInfoAdd";
    }
	
	
	@PostMapping("/pet/addPet")
	public String addPet(
	    @ModelAttribute("pet") Pet pet,
	    HttpSession session
	) {
	    // Get memberNo from session
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    pet.setMemberNo(loginUser.getMemberNo());

	    // Upload file processing (e.g., save to disk, DB path setting)
	    //String savedFileName = fileService.save(uploadFile);
	    //pet.setPetImage(savedFileName);

	    int result = pService.savePet(pet);
	    return "views/member/mypageTab";
	}
}