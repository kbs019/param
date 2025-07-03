package com.ex.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter		// 모든 변수의 get() 자동 생성
@Setter		// 모든 변수의 set() 자동 생성		// 적용되었는지 확인하는 것은 해당 클래스를 펼쳐보면 된다.
@NoArgsConstructor		// 매개변수가 없는 생성자(기본 생성자)를 자동으로 생성
@AllArgsConstructor		// 모든 변수를 매개변수로 받는 생성자를 자동으로 생성
@RequiredArgsConstructor		// final 이 붙어있거나 @NonNull 이 붙은 변수만 매개변수로 받는 생성자
@Builder		// Builder 패턴 메서드를 자동으로 생성해주는 annotation(객체 생성 시 변수값을 유연하게 설정을 가능하게 해준다.)
@ToString		// toString() 를 자동으로 생성
@EqualsAndHashCode		// equals() 와 hashCode() 를 자동으로 생성
@Data			// @Setter, @Getter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 를 한번에 생성해주는 annotation
public class Lombok {	// DTO 가 되는 클래스 이므로 setter / getter 필요
	private String name;
	private int age;
	
	@NonNull		// lombok 에 들어있는 NonNull 임포트 (객체를 생성할 때, null 값을 허용하지 않는다는 annotation)
	private String memo;
}
