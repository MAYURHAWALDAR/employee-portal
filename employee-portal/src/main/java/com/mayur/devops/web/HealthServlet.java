package com.mayur.devops.web;
import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.IOException;
@WebServlet("/health") public class HealthServlet extends HttpServlet { protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{resp.setContentType("application/json");resp.getWriter().write("{\"status\":\"UP\"}");} }
