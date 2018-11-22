<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reddish register</title>
    <#include "headconstants.ftl">
</head>
<body>
<#include "header.ftl">
<div class="container">
    <h2>Register new user</h2>

    <form action="register" method="post">
        <div class="form-group">
            <label for="emailinput">E-mail</label>
            <input type="text" class="form-control" id="emailinput" name="email" placeholder="email@domain.com"> <br>
        </div>
        <div class="form-group">
            <label for="usernameinput">Username</label>
            <input type="text" class="form-control" id="usernameinput" name="username" placeholder="username"> <br>
        </div>
        <div class="form-group">
            <label for="passwordinput">Password</label>
            <input type="password" class="form-control" id="passwordinput" name="password" placeholder="password"> <br>
        </div>
        <div class="form-group">
            <label for="passwordrepeatinput">Repeat password</label>
            <input type="password" class="form-control" id="passwordrepeatinput" name="passwordrepeat" placeholder="password"> <br>
        </div>
        <input type="submit" class="btn btn-primary" value="Register new user"> <br>
    </form>

    <#include "footer.ftl">
</div>
</body>
</html>