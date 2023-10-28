package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@ToString
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numCourse;
	int level;
	@Enumerated(EnumType.STRING)
	TypeCourse typeCourse;
	@Enumerated(EnumType.STRING)
	Support support;
	Float price;
	int timeSlot;

	public Course(Long numCourse, int level, TypeCourse typeCourse, Support support, Float price, int timeSlot) {
		this.numCourse = numCourse;
		this.level = level;
		this.typeCourse = typeCourse;
		this.support = support;
		this.price = price;
		this.timeSlot = timeSlot;
	}

	@JsonIgnore
	@OneToMany(mappedBy= "course")
	Set<Registration> registrations;

}