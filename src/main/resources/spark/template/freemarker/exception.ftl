<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reddish error</title>
</head>
<body>
<#if exception??>${exception.message} ${exception.status} <br/></#if>
</body>
</html>