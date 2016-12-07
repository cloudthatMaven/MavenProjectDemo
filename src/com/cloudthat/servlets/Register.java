package com.cloudthat.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudthat.controllers.UserDynamodbController;
import com.cloudthat.models.UserModel;

public class Register extends HttpServlet {

	private static final long serialVersionUID = 102831973239L;
	private String result = "success";
	private UserModel userObject;
	private UserDynamodbController userController;
	private String INDEX_PAGE = "/index.jsp";
	private String action = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			// create the object of UserModel
			userObject = new UserModel();
			// create the object of UserDbConroller
			userController = new UserDynamodbController();
			// set the properties
			userObject.setFirstName(req.getParameter("firstname"));
			userObject.setLastName(req.getParameter("lastname"));
			userObject.setUserName(req.getParameter("username"));
			userObject.setEmailId(req.getParameter("email"));

			result = userController.insertData(userObject);

			if (result.equalsIgnoreCase("success")) {
				System.out.println("Record inserted successfully");
			} else {
				System.out.println("Error!!!");
			}

			// redirect to index page
			RequestDispatcher dis = getServletContext().getRequestDispatcher(INDEX_PAGE);
			req.setAttribute("userList", userController.getUserData());
			dis.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
