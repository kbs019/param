package com.ex.param.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ex.param.Member;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/param/*")
@Log4j2		// 출력문으로 사용을 했을 때는 에러인지 아닌지 모른다.	=> print 대신 사용하여 기록을 남긴다.
public class ParamController {
	@GetMapping("form")
	public String form() {
		
		log.trace("[trace] url-/param/form");	// 보는 사람이 알아볼 수 있게끔 설정
		log.debug("[debug] url-/param/form");
		log.info("[info] url-/param/form");
		log.warn("[warn] url-/param/form");
		log.error("[error] url-/param/form");
		log.fatal("[fatal] url-/param/form");
		// properties 파일에서 info 로 설정해두었기 때문에 info 이하의 레벨인 trace 와 debug 는 출력되지 않는다.
		
		return "form";
	}
	
	@PostMapping("/ex01")	// /form 에서 넘어온 파라미터의 값들이 
	public String ex01( Member member ) {	// 이 부분에서 자동으로 주입된다.
		// DTO(Data Transfer Object) 선언하면 파라미터를 자동으로 주입해준다.
		// DTO는 view 까지 전달된다.
		// view 에서 사용할 때는 ${member.id} 형태로 사용을 한다.
		
		log.info("ex01 -- parameter id : "+ member.getId());
		log.info("ex01 -- parameter password : "+ member.getPassword());
		
		return "ex01";
	}
	
	@PostMapping("/ex02")
	public String ex02( @RequestParam("id") String id, @RequestParam("password") String pw, Model model ) {	// "id" 는 input 태그의 name 속성 값
		// @RequestParam : request.getParameter() 이것과 같은 기능을 한다. - 
		
		log.info("ex02 -- parameter id : "+ id);
		log.info("ex02 -- parameter password : "+ pw);
		
		// @RequestParam 은 view 까지 전달 불가
		model.addAttribute("id", id);		// @RequestParam() 으로 꺼낸 값을 담은 String 타입의 id 를 뷰 페이지로 전송하려면, Model 에 넣어주어야 한다.
		model.addAttribute("password", pw);	// Model 은 컨트롤러에서 뷰까지 데이터를 전송하는 역할
		
		return "ex02";	// 도착 view 페이지
	}
	
	@PostMapping("/ex03")	// 요청 uri
	public String ex03(@RequestParam("hobbies") List<String> hobbies, @RequestParam("hobbies") String[] arr, Model model) {
		// 체크박스 같은 파라미터의 이름이 같은 경우 : List or 배열로 받을 수 있다.
		
		model.addAttribute("hobbies", hobbies);
		model.addAttribute("arr", arr);		// 사실 이름 상관없음..;;
		
		log.info("ex03 -- parameter ListHobbies : "+ hobbies);
		log.info("ex03 -- parameter arrHobbies : "+ Arrays.asList(arr));
//		log.info("ex03 -- parameter arrHobbies : "+ arr);		<-- ex03 -- parameter arrHobbies : [Ljava.lang.String;@35786185 가 출력된다.  :  배열객체는 주소값을 가진다.
		
		return "ex03";	// view 페이지
	}
	
	@PostMapping("ex04")
	public String ex04( Member member, @ModelAttribute("id") String id ) {
		// 바인딩? Member member 필요?
		// @ModelAttribute() : Model 과 RequestParam 을 합친 것과 같다. --  view 까지 데이터를 전달할 수 있다.
		
		log.info("ex04 -- parameter id : "+ id);
		
		return "ex04";
	}
	
	@PostMapping("ex05")
	public String ex05( @RequestParam("id") String id, RedirectAttributes rttr ) {
		// redirect - 강제 페이지 이동 (get 방식 => 파라미터 전달 못함)
		// 일회성 데이터 전달 : 화면에 한번만 사용됨. -> 새로고침을 하면 사라진다. (값이 출력되지 않는다.)
		log.info("ex05 -- parameter id : "+ id);

		rttr.addFlashAttribute("id", id);
		rttr.addFlashAttribute("password", 1234);
		
		return "redirect:ex05";	// redirect 는 get 방식이기 때문에 @GetMapping("ex05") 인 메서드가 필요하다.
	}
	
	@GetMapping("ex05")
	public String ex05() {	// 오버로딩이므로 오류가 생기지 않는다. (선언부가 같음)
		return "ex05";
	}
	
	@GetMapping("ex06")		// 실행 주소 : http://localhost:8080/param/ex06
//	이 위치에 와도 됨!!
	public @ResponseBody String ex06() {	// @ResponseBody 를 쓸때는 뷰페이지 필요 없음
		// @ResponseBody : 뷰로 이동하지 않고, return 값을 화면에 그대로 출력시킨다.
		return "안녕하세요!";	
	}
	
	@GetMapping("ex07")
	public @ResponseBody Member ex07() {	// 응답을 뷰 페이지로 전달하지 않고 바로 브라우저에 출력시킨다.
		Member dto = new Member();
		dto.setId("boot");
		dto.setPassword("1234");
		
		return dto;
	}
	// {"id":"boot","password":"1234","email":null,"gender":null,"country":null,"hobbies":null} : Member 객체가 Json 방식으로 변환되어 브라우저에 출력됨
	
	@GetMapping("ex08")
	public ResponseEntity<String> ex08(){
		// ResponseEntity 클래스 : Http 응답 클래스이다.
		// 응답의 데이터, 헤더의 정보, HTTP 상태 코드(404, 500, ..) 등을 포함하는 객체
		
		// 이스케이프 문자의 시작을 알릴 때 \ 사용
		String msg = "{\"name\":\"춘식이\"}";		// "{"name":"춘식이"}" 의 형태로 출력되도록 한것이다.
		
		// 응답 헤더 설정
		HttpHeaders header = new HttpHeaders();
		// 브라우저에게 Content-Type 이 json 타입이고, 인코딩은 UTF-8 이라고 얘기하는 것
		header.add("Content-Type", "application/json;charset=UTF-8");	// Content-Type 을 "" 로 해라
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	// {"name":"춘식이"} 가 출력됨
	
	@PostMapping("ex09")
	public String ex09(@RequestParam("save") MultipartFile mf, @ModelAttribute("id") String id, Model model) {	// spring boot 에서는 MultipartFile 을 사용하며, 이는 내장되어 있으므로 그냥 사용하면 된다.
		// 선택한 파일의 정보를 꺼내어 각 
		model.addAttribute("contentType", mf.getContentType());
		model.addAttribute("orgName", mf.getOriginalFilename());
		model.addAttribute("fileSize", mf.getSize());
		
		File copy = new File("D:/kbs/upload/"+mf.getOriginalFilename());	// 여기가 고장나면 
		
		try {
			mf.transferTo(copy);	// 이 부분에서 실질적으로 파일이 업로드됨
			// 이 부분이 잘못되어 업로드가 되지 않음 (152 행과 연계)
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "ex09";
	}
}
/*
	스프링 부트에서 일반적으로 사용되는 로그의 레벨
		TRACE	: 가장 상세한 로그 레벨이다.
				-- 애플리케이션의 동작을 상세히 기록한다.
				-- 내부 정보 추적할 때, 사용이 된다.
				-- 로그의 양이 많아질 수 있기 때문에 일반적인 운영환경에서 사용하지 않는다.
				
		DEBUG	: 디버깅 목적으로 상세한 정보를 출력
		 		-- 개발 중에 애플리케이션의 내부 상태를 파악하거나 문제를 진단할 때 사용한다.
		 		
		INFO	: 일반적인 정보를 기록하는 로그
				-- 애플리케이션의 정상 작동을 확인할 수 있는 정보들을 제공한다.
				
		WARN	: 경고 수준의 정보를 기록한다.
				-- 애플리케이션의 동작에는 문제가 없으나 잠재적인 문제나 예상치 못한 상황이 발생했을 때 사용한다.
				
		ERROR	: 오류가 발생했을 때 기록한다.
				-- 예외 상황이나 중대한 문제가 발생하여 애플리케이션이 제대로 작동하지 않을 때 사용한다.
				
		FATAL	: 치명적인 오류가 발생했을 때 기록
		
		아래로 내려올수록 레벨의 크기가 크다.
		TRACE < < < < FATAL
*/