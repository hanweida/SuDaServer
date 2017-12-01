
function player(player, vid) {
    switch (player) {
        case 'qie':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/qie.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'zhangyu':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/zhangyu.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'tv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/tv.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'pptv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/pptv.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'ppvip':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/ppvip.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'letv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/letv.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'levip':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/levip.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'ss':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/ss.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'zhibotv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/zhibotv.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'longzhu':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/longzhu.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'qq':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/qq.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'bestv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/bestv.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'line':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/live/line" + vid + ".php' allowfullscreen></iframe>";
            break
        case 'huajiao':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/huajiao.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'url':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/url.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'els':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/els.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'sina':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/sina.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'player':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/player.html?str=" + vid + "' allowfullscreen></iframe>";
            break
        case 'm3u8':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/m3u8.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'cntv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/cntv.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'room':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/live/room.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'jrs':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/jrs.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'ballbar':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='https://www.ballbar.cc/live/" + vid + "' allowfullscreen></iframe>";
            break
        case 'no':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/no.php' allowfullscreen></iframe>";
            break
        case 'rus':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://russian15487545158795348735674799536595131454464587523554.com/live/" + vid + ".php' allowfullscreen></iframe>";
            break
        case 'wx':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.kanbisai.tv/zhibo/wx.php' allowfullscreen></iframe>";
            break
    }

    $(function(){
        var cotrs = $(".signal p");
        cotrs.click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
    });

}

