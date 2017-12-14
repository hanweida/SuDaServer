<!-- NBA 直播页面-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>速达直播</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="description" content="酷玩直播免费观看足球、NBA、中超、英超、西甲、意甲、德甲、法甲和世界杯直播!">
    <meta name="keywords" content="酷玩直播">

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta name="applicable-device" content="mobile" />
    <meta charset="utf-8" />
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <meta HTTP-EQUIV="Expires" CONTENT="0">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript">document.write("<s" + "cript type='text/javascript' src='http://m.kanbisai.tv/js/play.js?" + Math.random() + "'></s" + "cript>");</script>
    <script src="https://cdn.bootcss.com/jquery/1.8.3/jquery.min.js" charset="utf-8"></script>
    <!--<script src="/static/pc/js/fbi.js" charset="utf-8"></script>-->
    <script type="text/javascript">
        document.write("<s" + "cript type='text/javascript' src='http://m.leqiuba.cc/js/play.js?" + Math.random() + "'></s" + "cript>");
    </script>
    <style type="text/css">
        .container .main .wrap .title{
            height: 90px;
            background-color: #00ADF9;
            text-align: center;
            color: #E9D7C5;
            font-family: 微软雅黑;
            font-size: 30px;
            font-weight: bolder;
        }
        .container .main .wrap .icon{
            height: 40px;
            width: 40px;
            margin-top: 5px;
        }
        .container .main .signal{
            margin-left: 10px;
        }
    </style>
</head>
<body style="margin: 0;padding: 0;border: 0">
<div class="container">
    <div class="main">
        <div class="wrap">
            <div class="title">
                <img class="icon" src="/static/img/icon.png">
                <div><font>速达体育</font></div>
            </div>
            <div id="showplayer">
                <c:forEach items="${list}" var="item" varStatus="stat">
                    <c:if test="${item.active}">
                        <script type="text/javascript">player('${item.player}','${item.vid}');</script>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="signal">
            <c:forEach items="${list}" var="item" varStatus="stat">
                <c:if test="${item.active}">
                    <p class="active" style="background-color: brown" onClick="player('${item.player}','${item.vid}')">${item.name}</p>
                </c:if>
                <c:if test="${!item.active}">
                    <p style="background-color:darkgray" onClick="player('${item.player}','${item.vid}')">${item.name}</p>
                </c:if>
            </c:forEach>
        </div>
        <div class="clear"></div>
    </div>

</div>
</div>
</div>

</body>
<script type="text/javascript">
    $(function () {
        player('${matchUrl.player}','${matchUrl.vid}');
    });
</script>


</html>
