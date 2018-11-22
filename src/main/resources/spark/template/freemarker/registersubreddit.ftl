<!DOCTYPE html>
<html lang="en">
<head>
    <title>Subreddit creation</title>
    <#include "headconstants.ftl">
</head>
<body>
<#include "header.ftl">
<div class="container">
    <h2>Create a subreddit</h2>

    <form action="registersubreddit" method="post">
        <div class="form-group">
            <label for="nameinput">Subreddit name</label>
            <input type="text" class="form-control" id="nameinput" name="name" placeholder="dank"> <br>
        </div>

        <input type="submit" class="btn btn-primary" value="Create a subreddit"> <br>
    </form>

    <#include "footer.ftl">
</div>
</body>
</html>