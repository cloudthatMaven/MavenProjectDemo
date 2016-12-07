package com.cloudthat.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudthat.controllers.UserDynamodbController;

@SuppressWarnings("serial")
public class UserOperations extends HttpServlet {

	private UserDynamodbController dbController = null;
	private String INDEX_PAGE = "/index.jsp";
	private UserDynamodbController userController;

	private String result = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		String actions = req.getParameter("action");
		String username = req.getParameter("username");
		String datetime = req.getParameter("datetime");
		System.out.println(actions + "" + username + "" + datetime);

		dbController = new UserDynamodbController();
		// create the object of UserDbConroller
		userController = new UserDynamodbController();

		result = dbController.deleteData(username, datetime);

		if (result.equalsIgnoreCase("success")) {
			System.out.println("Record Deleted successfully");
		} else {
			System.out.println("Error!!!");
		}

		/*
		 * // redirect to index page RequestDispatcher dis =
		 * getServletContext().getRequestDispatcher(INDEX_PAGE);
		 * req.setAttribute("userList", userController.getUserData());
		 * dis.forward(req, resp);
		 */

	}
}
