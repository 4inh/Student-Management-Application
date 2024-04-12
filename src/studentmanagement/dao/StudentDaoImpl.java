package studentmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import studentmanagement.model.Student;
import studentmanagement.utils.JDBCUtils;

public class StudentDaoImpl implements StudentDao {

	private static final String INSERT_STUDENTS_SQL = "INSERT INTO students"
			+ "  (username, faculty, dob,  gender) VALUES " + " (?, ?, ?, ?);";

	private static final String SELECT_STUDENT_BY_ID = "select id,username,faculty,dob,gender from students where id =?";
	private static final String SELECT_ALL_STUDENTS = "select * from students";
	private static final String DELETE_STUDENT_BY_ID = "delete from students where id = ?;";
	private static final String UPDATE_STUDENT = "update students set username= ?, faculty =?, dob =?, gender = ? where id = ?;";

	public StudentDaoImpl() {
	}

	@Override
	public void insertStudent(Student student) throws SQLException {
		System.out.println(INSERT_STUDENTS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENTS_SQL)) {
			preparedStatement.setString(1, student.getUsername());
			preparedStatement.setString(2, student.getFaculty());
			preparedStatement.setDate(3, JDBCUtils.getSQLDate(student.getDob()));
			preparedStatement.setString(4, student.getGender());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
	}

	@Override
	public Student selectStudent(long studentId) {
		Student student = null;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);) {
			preparedStatement.setLong(1, studentId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String username = rs.getString("username");
				String faculty = rs.getString("faculty");
				LocalDate dob = rs.getDate("dob").toLocalDate();
				String gender = rs.getString("gender");
				student = new Student(id, username, faculty, dob, gender);
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return student;
	}

	@Override
	public List<Student> selectAllStudents() {
		List<Student> students = new ArrayList<>();
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String username = rs.getString("username");
				String faculty = rs.getString("faculty");
				LocalDate dob = rs.getDate("dob").toLocalDate();
				String gender = rs.getString("gender");
				students.add(new Student(id, username, faculty, dob, gender));
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return students;
	}

	@Override
	public boolean deleteStudent(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	@Override
	public boolean updateStudent(Student student) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT);) {
			statement.setString(1, student.getUsername());
			statement.setString(2, student.getFaculty());
			statement.setDate(3, JDBCUtils.getSQLDate(student.getDob()));
			statement.setString(4, student.getGender());
			statement.setLong(5, student.getId());
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
}
