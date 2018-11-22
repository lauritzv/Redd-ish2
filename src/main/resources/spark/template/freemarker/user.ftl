<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reddish</title>
    <#include "headconstants.ftl">
    <script src="/js/votes.js"></script>

    <script>
        <#if user??>
        let loginUsername = "${user.username}";
        let userPassword = "${user.password}";
        <#else>
        let loginUsername = "not_logged_in";
        let userPassword = "not_logged_in";
        </#if>
    </script>
</head>
<body>
<#include "header.ftl">
<div class="container">
    <h1>
        ${pageuser.username}
    </h1>


    <p>
        Karma: ${pageuser.karma}
    </p>

    <h3>
        Posts by this user
    </h3>

    <div id="posts">
        <#list pageuser.posts as post>
            <hr>
            <table class="post">
                <tr>
                    <td>
                        <button class="upvotebutton" onclick="upvote(${post.id});">↑</button>
                    </td>
                    <td rowspan="2" class="post-title">
                        <a href="${post.link}">${post.title}</a>
                    </td>
                </tr>
                <tr>
                    <td id="votes_for_post_${post.id}" class="upvotes">${post.votes}</td>
                </tr>
                <tr>
                    <td rowspan="2">
                        <button class="downvotebutton" onclick="downvote(${post.id});">↓</button>
                    </td>
                    <td class="post-info">posted
                        <#if post.subreddit?? && post.subreddit.name??>in <a
                                href="/r/${post.subreddit.name}">${post.subreddit.name}</a> </#if>
                        by ${post.poster.username}
                        on ${post.date?number_to_datetime?string("YYYY-MM-dd HH:mm:ss")}</td>
                </tr>
                <tr>
                <td><#if post.subreddit?? && post.subreddit.name??><a
                        href="/r/${post.subreddit.name}/${post.id}/comments"> ${post.commentcount()}
                    <#if post.commentcount() == 1>
                         Comment
                    <#else>
                        Comments
                    </#if></a></td></#if>
                </tr>
            </table>
        </#list>
    </div>

    <#include "footer.ftl">
</div>
</body>
</html>
