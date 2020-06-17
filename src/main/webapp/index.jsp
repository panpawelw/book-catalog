<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
<h1 class='title'>Book Catalog</h1>
<p style="font-weight: normal">click book title to show / hide book details</p>
<br>
<div class='list-container'>
    <ul class='book-list'></ul>
</div>
<div class="add-book-button">
    <button class='add'>Add new book</button>
</div>
<div class='add-book'>
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
                <td>
                    <button type='reset' class='add-cancel'>Cancel</button>
                </td>
                <td><input type='submit' value='Add this book to database'/></td>
            </tr>
        </table>
    </form>
</div>
<div class="database-selection">
    <p>Select database type:</p>
    <input type="radio" id="memory-database" name="database" value="memory" checked>
    <label for="memory-database">memory</label>
    <input type="radio" id="mysql-database" name="database" value="mysql">
    <label for="mysql-database">mySQL</label>
</div>
<span id="myvar">${error}</span>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="></script>
<script src='resources/js/app.js'></script>
</body>
</html>