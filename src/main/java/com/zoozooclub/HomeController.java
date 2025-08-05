package com.zoozooclub;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zoozooclub.board.model.service.BoardService;
import com.zoozooclub.board.model.vo.Board;
import com.zoozooclub.member.model.service.MemberService;
import com.zoozooclub.shopping.model.service.ShoppingService;
import com.zoozooclub.shopping.model.vo.Product;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final ShoppingService sService;
	private final BoardService bService;
	
	@GetMapping({"/", "/index"})
    public String index(Model model) {
		
		ArrayList<Product> pList = sService.selectProductList();
		ArrayList<Board> bList  =bService.selectBoardListIndex();
		
		model.addAttribute("pList", pList);
		model.addAttribute("bList", bList);
		
        return "index";
    }
	
}
