var ua = navigator.userAgent,
    iframe_height;
var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
    isMobile = isIphone || isAndroid;
if (isMobile) {
    iframe_height = "200px"
} else {
    iframe_height = "450px"
}
function player(player, vid) {
    if (nostart == true) {
        var ifrmae_url = $("#share_link").val();
        document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='" + ifrmae_url + "' allowfullscreen></iframe>";
        return false;
    }
    switch (player) {
        case 'qie':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='https://1.kuwantiyu.com?s=qie&id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'zhangyu':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=zhangyu&id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'tv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=tv&id=" + vid + "' allowfullscreen></iframe>";
            break
        case "le":
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=le&id=" + vid + "' allowfullscreen></iframe>";
            break;
        case 'pptv':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=pptv&id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'cctv5':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=cctv5&id=" + vid + "' allowfullscreen></iframe>";
            break;
        case 'longzhu':
            var l_url = isMobile ? "https://1.kuwantiyu.com": 'http://api.kuwanzhibo.com';
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='" + l_url + "?s=longzhu&id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'url':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' scrolling='no' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=url&id=" + vid + "' allowfullscreen></iframe>";
            break
        case 'https':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' scrolling='no' height='" + iframe_height + "' frameborder='0' src='https://1.kuwantiyu.com?s=url&id=" + vid + "' allowfullscreen></iframe>";
            break;
        case 'no':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=no&id=" + vid + "' allowfullscreen></iframe>";
            break;
        case 'm3u8':
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.fultz.cn:88/zhibo/m3u8.php?id=" + vid + "' allowfullscreen></iframe>";
            break
        default:
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' scrolling='no' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=" + player + "&id=" + vid + "' allowfullscreen></iframe>";
            break;
    }
    $(function() {
        var cotrs = $(".signal p");
        cotrs.click(function() {
            $(this).addClass("active").siblings().removeClass("active");
        });
    });
}
function init_copy(guid, copy_id) {
    if (guid == 'undefind') {
        return false;
    }
    var clip = null;
    clip = new ZeroClipboard.Client();
    clip.setHandCursor(true);
    clip.addEventListener('mouseOver',
        function(client) {
            clip.setText($('#' + guid + '').val());
        });
    clip.addEventListener('complete',
        function(client, text) {
            alert("复制成功，您可以使用该接口地址了^_^");
        });
    clip.glue(copy_id);
}
