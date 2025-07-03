package com.ex.param.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.param.Lombok;

@Controller		// 이것을 적어주어야만 컨트롤러로 인식을 할 수 있다.
@RequestMapping("/lombok")		// http://ip:post 의 뒤에 오는 루트주소 설정	// 루트 주소(공통 주소)로 사용할 수 있도록한다.
public class LombokController {
	
	@GetMapping("/lom01")
	public String lom01( Model model ) {	// controller -> view 로 데이터를 전달하는 객체
		Lombok lom = new Lombok();
		lom.setName("춘식");		// @Setter 로 인해 직접 set() 를 생성하지 않아도 사용할 수 있는 것
		lom.setAge(1);
		lom.setMemo("hahaha");
		
		model.addAttribute("lom", lom);
		
		return "lom01";		// /templates/lom01.html 로 알아서 찾아감
	}
	
	// @GetMapping("/lom02")
	// public String lom02(Model model) {
	// 	Lombok lom = new Lombok();
	// 	lom.setName("춘식2");
	// 	lom.setAge(2);
		
	// 	// lom.setMemo(null);	@NotNull 설정에 의해 null 값이 들어갔을 때, 실행 예외가 발생한다.
	// 	lom.setMemo("lombok");	// 수정해주어야함.	해당 setMemo() 가 쓰이지 않아도 안됨!!
	// 	model.addAttribute("lom2", lom);	
		
	// 	return "lom02";
	// }
	
	// @GetMapping("/lom03")
	// public String lom03(Model model) {
	// 	// @AllArgsConstructor 로 인해 모든 변수를 매개변수로 받는 생성자를 사용할 수 있다.
	// 	Lombok lom = new Lombok("춘식3", 3, "hello");
		
	// 	model.addAttribute("lom3", lom);
	// 	return "lom03";
	// }
	
	// @GetMapping("/lom04")
	// public String lom04(Model model) {
	// 	// .builder() 로 시작하여 끝날 때는 .build() 로 끝낸다.
	// 	Lombok lom = Lombok.builder().name("춘식4").age(4).memo("hi").build();	// @builder 때문에 사용할 수 있는 것이다.
		
	// 	model.addAttribute("lom4", lom);
		
	// 	return "lom04";
	// }
}
