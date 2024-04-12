package studentmanagement.model;

import java.time.LocalDate;

public class Student {

	private Long id;
	private String username;
	private String faculty;
	private LocalDate dob;
	private String gender;
	
	protected Student() {
		
	}
	
	public Student(long id, String username, String faculty, LocalDate dob, String gender) {
		super();
		this.id = id;
		this.username = username;
		this.faculty = faculty;
		this.dob = dob;
		this.gender = gender;
	}

	public Student(String username, String faculty, LocalDate dob, String gender) {
		super();
		this.username = username;
		this.faculty = faculty;
		this.dob = dob;
		this.gender = gender;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
