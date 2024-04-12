package studentmanagement.dao;

import java.sql.SQLException;
import java.util.List;

import studentmanagement.model.Student;

public interface StudentDao {

	void insertStudent(Student student) throws SQLException;

	Student selectStudent(long studentId);

	List<Student> selectAllStudents();

	boolean deleteStudent(int id) throws SQLException;

	boolean updateStudent(Student student) throws SQLException;

}