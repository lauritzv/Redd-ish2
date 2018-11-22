<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reddish login</title>
    <#include "headconstants.ftl">
</head>
<body>
<#include "header.ftl">
<div class="container">
    <h2>Login</h2>

    <form action="login" method="post">
        <div class="form-group">
            <label for="usernameinput">Username</label>
            <input type="text" class="form-control" id="usernameinput" name="username" placeholder="username"> <br>
        </div>
        <div class="form-group">
            <label for="passwordinput">Password</label>
            <input type="password" class="form-control" id="passwordinput" name="password" placeholder="password"> <br>
        </div>
        <input type="submit" class="btn btn-primary" value="Log in"> <br>
    </form>

    <#include "footer.ftl">
</div>
</body>
</html>