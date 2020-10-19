<%@ page contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <link rel='stylesheet' crossorigin='anonymous'
          href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'
          integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T'>
    <link rel='stylesheet' href='resources/css/style.css' type='text/css'>
    <title>Book Catalog</title>
</head>
<body>
<div class='main-style title'>
    <h1>Book Catalog</h1>
</div>
<div class='list-container'>
    <ul class='book-list'></ul>
</div>
<div class='add-book-button'>
    <button class='btn btn-primary' id='add-button'>Add new book</button>
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
                    <button type='reset' id='add-cancel' class='btn btn-primary'>Cancel</button>
                </td>
                <td><input type='submit' class='btn btn-primary' value='Add this book to database'/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class='main-style database-selection'>
    <p>Select database type:</p>
    <input type='radio' id='memory-database' name='database' value='memory' checked>
    <label for='memory-database'>Memory</label>
    <input type='radio' id='mysql-database' name='database' value='mysql'>
    <label for='mysql-database'>MySQL</label>
    <label for='reset-database'></label>
    <button id='reset-database' class='btn btn-primary'>reset database</button>
</div>
<script src='https://code.jquery.com/jquery-3.5.1.min.js' crossorigin='anonymous'
        integrity='sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0='></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js'
        integrity='sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1'
        crossorigin='anonymous'></script>
<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js'
        integrity='sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM'
        crossorigin='anonymous'></script>
<script src='resources/js/app.js'></script>
</body>
</html>