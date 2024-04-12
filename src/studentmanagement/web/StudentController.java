package studentmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studentmanagement.dao.StudentDao;
import studentmanagement.dao.StudentDaoImpl;
import studentmanagement.model.Student;

@WebServlet("/")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDao studentDAO;

	public void init() {
		studentDAO = new StudentDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertStudent(request, response);
				break;
			case "/delete":
				deleteStudent(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateStudent(request, response);
				break;
			case "/list":
				listStudent(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Student> listStudent = studentDAO.selectAllStudents();
		request.setAttribute("listStudent", listStudent);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student/student-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("student/student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Student existingStudent = studentDAO.selectStudent(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student/student-form.jsp");
		request.setAttribute("student", existingStudent);
		dispatcher.forward(request, response);

	}

	private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String username = request.getParameter("username");
		String faculty = request.getParameter("faculty");
		String gender = request.getParameter("gender");
		Student newStudent = new Student(username, faculty, LocalDate.now(), gender);
		studentDAO.insertStudent(newStudent);
		response.sendRedirect("list");
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	    int id = Integer.parseInt(request.getParameter("id"));
	    String username = request.getParameter("username");
	    String faculty = request.getParameter("faculty");
	    LocalDate dob = LocalDate.parse(request.getParameter("dob"));
	    String gender = request.getParameter("gender");
	    Student updateStudent = new Student(id, username, faculty, dob, gender);
	    
	    studentDAO.updateStudent(updateStudent);
	    
	    response.sendRedirect("list");
	}


	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		studentDAO.deleteStudent(id);
		response.sendRedirect("list");
	}
}
