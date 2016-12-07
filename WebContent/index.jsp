

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  
<title>Registration page</title>
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript">
	function validateForm() {
		var firstname = document.forms["myForm"]["firstname"].value;
		var lastname = document.forms["myForm"]["lastname"].value;
		var email = document.forms["myForm"]["email"].value;
		var username = document.forms["myForm"]["username"].value;

		if (firstname == "") {
			alert("First Name should not be left blank");
			return false;
		}

		if (lastname == "") {
			alert("Last Name should not be left blank");
			return false;
		}

		if (username == "") {
			alert("User Name should not be left blank");
			return false;
		}
		if (email == "") {
			alert("Email should not be left blank");
			return false;
		}

	}
</script>
</head>

<body>
	<div class="container">
		<section class="register">
		<h1>CloudThat Registration</h1>
		<form method="post" action="Register"
			onsubmit=" return validateForm()" name="myForm">
			<div class="reg_section personal_info">
				<h3>Your Personal Information</h3>
				<input type="text" name="firstname" value=""
					placeholder="Your First Name"> <input type="text"
					name="lastname" value="" placeholder="Your Last Name"> <input
					type="text" name="username" value=""
					placeholder="Your Desired Username"> <input type="text"
					name="email" value="" placeholder="Your E-mail Address">
			</div>
			<p class="submit">
				<input type="submit" name="add" value="Submit"> &nbsp
			</p>
		</form>
		<br></br>


		<h2>List of users</h2>

		<table class="table">
			<thead>
				<tr>
					<th>FirstName</th>
					<th>LastName</th>
					<th>UserName</th>
					<th>Email</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach var="userModel" items="${userList}">
						<tr>
							<td><c:out value="${userModel.firstName}" /></td>
							<td><c:out value="${userModel.lastName}" /></td>
							<td><c:out value="${userModel.userName}" /></td>
							<td><c:out value="${userModel.emailId}" /></td>
					</c:forEach>
			</tbody>
		</table>
		</section>
	</div>
</body>
</html>