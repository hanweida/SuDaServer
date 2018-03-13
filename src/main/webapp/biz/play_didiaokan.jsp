<!doctype html>
<html><head>
    <meta charset="utf8">
    <title>LIVE</title>
    <link rel="shortcut icon" href="http://www.kuwantiyu.com/favicon.ico">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="description" content="">
    <meta name="keywords" content="直播">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta name="applicable-device" content="mobile">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.8.3/jquery.min.js" charset="utf-8"></script>
    <link href="http://www.kuwantiyu.com/static/mobile/style/style.css" rel="stylesheet">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">



</head>
<body>
<div class="container">
    <div class="wrap">

        <div id="showplayer"><iframe width="100%" height="180px" src="${matchUrl}"
                 frameborder="0" marginwidth="0" marginheight="0" scrolling="no" >
            <video
                   controls="controls" autoplay="autoplay" width="100%" height="100%"></video>
        </iframe></div>
        <div class="signal">
        </div>
    </div>
</div>
</body>

<%--<SCRIPT LANGUAGE="JavaScript">--%>
    <%--function dw(str){document.write(str);}--%>
    <%--function J_get(name,url) {--%>
        <%--url=url?url:self.window.document.location.href;var start=url.indexOf(name+'=');if(start==-1)return'';var len=start+ name.length+ 1;var end=url.indexOf('&',len);if(end==-1)end=url.length;return unescape(url.substring(len,end));--%>
    <%--}--%>
    <%--function obj(id){return document.getElementById(id);}--%>
    <%--function changBg(obj,id){if(id==1){obj.style.background='#F5F5F5';}--%>
    <%--else{obj.style.background='#FFFFFF';}}--%>
<%--</script>--%>

<%--<script type="text/javascript">--%>
    <%--var id = J_get('id');--%>
    <%--dw('<video src="'+id+'" controls="controls" autoplay="autoplay" width="100%" height="100%"></video>');--%>
<%--</script>--%>
</html>