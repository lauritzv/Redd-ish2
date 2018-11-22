<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reddish post</title>
    <#include "headconstants.ftl">
    <script src="/js/post_creation.js"></script>
</head>
<body>
<#include "header.ftl">
<div class="container">
    <h2>Make a new Post</h2>

    <form action="post" method="post">

        <div class="form-group">
            <label for="selfradio">Selfpost</label>
            <input type="radio" class="form-control" id="selfradio" name="type" placeholder="some subreddit" value="self" checked="true" onclick="self_post_mode()">
            <label for="linkradio">linkpost</label>
            <input type="radio" class="form-control" id="linkradio" name="type" placeholder="some subreddit" value="link" onclick="link_post_mode()"> <br>
        </div>

        <div class="form-group">
            <label for="titleinput">Title</label>
            <input type="text" class="form-control" id="titleinput" name="title" placeholder="some title"> <br>
        </div>
        <div class="form-group" id="contentinputwrapper">
            <label for="contentinput">Content</label>
            <textarea class="form-control" id="contentinput" name="content" placeholder="some content..."></textarea>
            <#--<input type="text" class="form-control" id="contentinput" name="content" placeholder="some content"> <br>-->
        </div>
        <div class="hidden" id="linkinputwrapper">
            <label for="linkinput">Link</label>
            <input type="text" class="form-control" id="linkinput" name="link" placeholder="some link"> <br>
        </div>
        <div class="form-group">
            <label for="subredditinput">Subreddit</label>
            <input type="text" class="form-control" id="subredditinput" name="subreddit" placeholder="some subreddit"> <br>
        </div>
        <input type="submit" class="btn btn-primary" value="submit"> <br>
    </form>

    <#include "footer.ftl">
</div>
</body>
</html>