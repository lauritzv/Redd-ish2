<header>
    <nav role="navigation" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="row">
                <div class="navbar-header">
                    <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">REDD-ISH</a>
                </div>
                <div id="navbarCollapse" class="navbar-collapse collapse" style="height: 1px;">
                    <ul class="nav navbar-nav">
                        <li><a href="/r/cats">r/cats</a></li>
                        <li><a href="/r/Dank">r/Dank</a></li>
                        <li><a href="/r/Wavy">r/Wavy</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><hr></li>
                        <#if user??>
                            <li><a href="/post">Create Post</a></li>
                            <li><a href="/user/${user.username}">${user.username}</a></li>
                            <li><a href="/logout">Logout</a></li>
                        <#else>
                            <li><a href="/login">Login</a></li>
                            <li><a href="/register">Register</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
    <br><br><br>
</header>

<#--<#if Session??>session exists</#if><br />-->
<#--<#if Session?? && Session.getAttribute("user")??>user exists in session</#if><br />-->
<#--<#if Session?? && Session.user??>user exists in session <br /></#if>-->
<#--<#if user??>user exists <br /></#if>-->
<#--<#if subreddit??>subreddit exists <br/></#if>-->
<#--<#if Request?? && Request.subreddit>request&</#if>-->


<#--
bootstrap navbar tutorial:

https://getbootstrap.com/docs/4.0/components/navbar/
-->