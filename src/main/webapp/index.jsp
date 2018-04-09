<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel='stylesheet' href='resources/css/style.css' type='text/css'>
	<title>Book catalog</title>
</head>
<body>
	<br>
	<br>
	<br>
	<h1 align='center' class='title'>Book catalog</h1>
	<div class='listContainer'>
		<ul class='bookList'></ul>
	</div>
	<div align='center'>
		<button class='add'>Add new book</button>
	</div>
	<div class='addBook'>
		<form action='books/add' method='post'>
			<table>
				<tr>
					<td><label for='isbn'>isbn</label></td>
					<td><input id='isbn' type='text' name='isbn'/></td>
				</tr>
				<tr>
					<td><label for='title'>title</label></td>
					<td><input id='title' type='text' name='title'/></td>
				</tr>
				<tr>
					<td><label for='author'>author</label></td>
					<td><input id='author' type='text' name='author'/></td>
				</tr>
				<tr>
					<td><label for='publisher'>publisher</label></td>
					<td><input id='publisher' type='text' name='publisher'/></td>
				</tr>
				<tr>
					<td><label for='type'>type</label></td>
					<td><input id='type' type='text' name='type'/></td>
				</tr>
				<tr>
					<td colspan=2><input type='submit' value='Add this book to database'/></td>
			</table>
		</form>
	</div>
	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>
	<script src='resources/js/app.js'></script>
</body>
</html>