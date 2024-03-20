<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
<b>普通文本 String 展示：</b><br><br>
<#if username??>
    Hello  ${name}<br>
</#if>
Hello  ${username!'香港仔'}
<br>

账单日期:${today?string("1996年8月24日")}

账单号:${No?c};
<hr>
<b>对象Student中的数据展示：</b>
<br/>姓名：${stu.name}<br/>
年龄：${stu.age}
<hr>
</body>
</html>