<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Reddish</title>
    <#include "headconstants.ftl">

</head>
<body>
<#include "header.ftl">
<div class="container">



    <h2>Subreddish ${subreddit} does not exist!</h2>

    <p>You can <a href="/registersubreddit">register subreddishes here</a></p>

    <#if similarnamedsubreddishes??>
        <p id="did_you_mean">Did you mean:</p>
            <ul>
        <#list similarnamedsubreddishes as sub>
            <li><a href="/r/${sub.name}">r/${sub.name}</a></li>

            <#else>
            <script>document.getElementById("did_you_mean").style.visibility = "hidden";</script>
        </#list>
            </ul>
    </#if>

    <#include "footer.ftl">
</div>
</body>
</html>