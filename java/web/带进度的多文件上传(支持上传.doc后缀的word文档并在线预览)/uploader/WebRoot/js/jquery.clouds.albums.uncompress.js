﻿/**
 * jQuery 浮云插件
 * jQuery Clouds Albums Plus v1.0
 *
 * .albums{ float : cloud ;}
 *
 * 网址 : http://julying.com/lab/albums
 * 类型： 原创脚本
 * 邮箱 : i@julying.com
 * QQ群 ： 11500749 (jQuery 自习室)
 * 发布 : 2010-09-10 13:55:29 
 * 更新 : 2011-03-16 12:30:00 
 * 地点 : 中国 北京
 *
 * 版权所有 2010 | julying.com
 * 此插件遵循 MIT、GPL2、GNU 许可.
 * http://julying.com/code/license
 *
 ***************************
 * 注意事项 ：
		1、此插件为开源插件
		2、最终版权、解释权归 julying.com 所有有
 */

;;(function($){
    $.fn.synchroMove = function(options){
        var opts = $.extend({}, $.fn.synchroMove.defaults,options);
        var speed = ['slow','normal','fast'];
        var speedTimes = [25,15,5];
        var indexof = $.inArray(opts.speed,speed);
        indexof = indexof == -1 ? 1: indexof;
        speedTime = speedTimes[indexof];
        this.each(function(i){
			synchroMoveDo(this,i)
        });
        setTimeout(opts.complete,10);
        function synchroMoveDo(obj,i){
            var $moveShow = $(obj);
            var html = $moveShow.addClass('moveShow').html();
            var $moveShowBox = $moveShow.html('<div class="moveShowBox">' + html + '</div>').find('div.moveShowBox:first');
            var $img = $moveShow.find('img:first');
            var showW = parseInt($moveShow.css('width'));
            var showH = parseInt($moveShow.css('height'));
            var imgW = parseInt($img.css('width') == 'auto' ? $img.width() : $img.css('width'));
            var imgH = parseInt($img.css('height') == 'auto' ? $img.height() : $img.css('height'));
            $moveShowBox.css({
                'width': imgW,
                'height': imgH
            });
            var showOffSetLeft = $moveShow.get(0).offsetLeft;
            var showOffSetTop = $moveShow.get(0).offsetTop;
            var xMouse = 0,
            	yMouse = 0;
            var xMove = 0,
            	yMove = 0;
            var timeout,
				timeoutAgain;
            $moveShow.mousemove(function(e){
                xMouse = e.pageX;
                yMouse = e.pageY
            }).mouseover(function(e){
                if (timeoutAgain) clearTimeout(timeoutAgain);
                if (timeout) clearInterval(timeout);
                timeout = setInterval(function(){
                    run();
                },speedTime);
            }).mouseout(function(e){
                timeoutAgain = setTimeout(function(){
                    if (timeout) clearInterval(timeout)
                },3000);
            });
            function run(){
                xMove += (((Math.max( - showW,Math.min(0,(showW * .5 - (xMouse - showOffSetLeft) * 2))) * (imgW - showW)) / showW) - xMove) * .1;
                yMove += (((Math.max( - showH,Math.min(0,(showH * .5 - (yMouse - showOffSetTop) * 2))) * (imgH - showH)) / showH) - yMove) * .1;
				xMove = parseInt(xMove);
				yMove = parseInt(yMove);
                $moveShowBox.css({
                    'left': xMove+ 'px',
                    'top': yMove+ 'px'
                });
            };
        };
    };
    $.fn.synchroMove.defaults = {
        speed: 'normal',
        complete: function(fn){}
    };
})(jQuery);