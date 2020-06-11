<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet' href='resources/css/style.css' type='text/css'>
    <title>Book Catalog</title>
</head>
<body>
<br>
<br>
<h1 align='center' class='title'>Book Catalog</h1>
<p align='center' style="font-weight: normal">click book title to show / hide book details</p>
<br>
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
                <td><button type='reset' class='addCancel'>Cancel</button></td>
                <td><input type='submit' value='Add this book to database'/></td>
            </tr>
        </table>
    </form>
</div>
<div>
    <input type="radio" id="memory-database" name="database" value="memory">
    <label for="memory-database">memory</label>
    <input type="radio" id="mysql-database" name="database" value="mysql">
    <label for="mysql-database">mySQL</label>
</div>
<script src='resources/js/jquery-3.3.1.min.js'></script>
<script src='resources/js/app.js'></script>
</body>
</html>