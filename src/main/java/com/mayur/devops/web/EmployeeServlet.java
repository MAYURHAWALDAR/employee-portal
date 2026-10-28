package com.mayur.devops.web;
import com.mayur.devops.service.EmployeeService; import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException;
@WebServlet("/employees") public class EmployeeServlet extends HttpServlet { private final EmployeeService service=new EmployeeService(); protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.setAttribute("employees",service.findAll());req.getRequestDispatcher("/WEB-INF/views/employees.jsp").forward(req,resp);} }
