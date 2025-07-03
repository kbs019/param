package com.ex.param;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private String id;
	private String password;
	private String email;
	private String gender;
	private String country;
	private List<String> hobbies;
}
