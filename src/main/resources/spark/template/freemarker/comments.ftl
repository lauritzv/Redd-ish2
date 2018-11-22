<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reddish<#if subredditname??> | ${subredditname}</#if></title>
    <#include "headconstants.ftl">
</head>
<body>
<#include "header.ftl">
<div class="container">
    <h2>
    <#if subreddit?? && subreddit != "">
        Subreddish: ${subreddit}
    <#else>
    The main page
    </#if>
    </h2>

    <div class="container">
        <table class="post">
            <tr>
                <td rowspan="3" class="upvotes">${post.votes}</td>
                <td class="post-title">
                    <#if post.linkPost>
                        <a href="${post.link}" target="_blank">${post.title}</a>
                    <#else>
                        ${post.title}
                    </#if>
                </td>
            </tr>
                <#if post.content?? && post.content != "">
                <tr>
                    <td class="post-content">${post.content}</td>
                </tr>
                </#if>
            <tr>
                <td class="post-info">posted by <a href="/user/${post.poster.username}">${post.poster.username}</a>
                    on ${post.date?number_to_datetime?string("YYYY-MM-dd HH:mm:ss")}</td>
            </tr>
        </table>
    </div>

    <div>
        <h3>Comments</h3>
        <#list post.comments as comment>
            <table class="post">
                <tr>
                    <td rowspan="2" class="upvotes">${comment.votes}</td>
                    <td><a href="/user/${comment.commenter.username}">${comment.commenter.username}</a>
                        on ${comment.date?number_to_datetime?string("YYYY-MM-dd HH:mm:ss")}</td>
                </tr>
                <tr>
                    <td>${comment.content}</td>
                </tr>
            </table>
        </#list>
    </div>
    <#if user??>
    <div class="container">
        <form action="comments" method="post">
            <div class="form-group" id="contentinputwrapper">
                <label for="contentinput"><b>Add a comment</b></label>
                <textarea class="form-control" id="contentinput" name="commentcontent"
                          placeholder="Don't be all that rude"></textarea>
            </div>
            <input type="submit" class="btn btn-primary" value="Submit"> <br>
        </form>
    </div>
    </#if>



    <#include "footer.ftl">
</div>
</body>
</html>