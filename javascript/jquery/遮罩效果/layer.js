/**************************
*Desc:�ύ����ʱ����
*Argument:type=0 ȫ���� 1�ֲ���
* 
**************************/
; (function ($) { 
    $.fn.jqLoading =function(option) {
        var defaultVal = {
            backgroudColor: "#ECECEC",//����ɫ
            backgroundImage: "loading.gif",//����ͼƬ
            text: "����������....",//���� 
            width: 150,//���
            height: 60,//�߶�
            type:0 //0ȫ���ڣ�1 �ֲ���
            
        };
        var opt = $.extend({}, defaultVal, option);

        if (opt.type == 0) {
            //ȫ����
            openLayer();
        } else {
            //�ֲ���(��ǰ����ӦΪ��Ҫ���ڵ��Ķ���)
            openPartialLayer(this);
        }
        
        //���ٶ���
        if (option === "destroy") {
            closeLayer();
        }
        
        //���ñ������
        function height () {
            var scrollHeight, offsetHeight;
            // handle IE 6
            if ($.browser.msie && $.browser.version < 7) {
                scrollHeight = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight);
                offsetHeight = Math.max(document.documentElement.offsetHeight, document.body.offsetHeight);
                if (scrollHeight < offsetHeight) {
                    return $(window).height();
                } else {
                    return scrollHeight;
                }
                // handle "good" browsers
            }
            else if ($.browser.msie && $.browser.version == 8) {
                return $(document).height() - 4;
            }
            else {
                return $(document).height();
            }
        };
        
        //���ñ������
        function width() {
            var scrollWidth, offsetWidth;
            // handle IE
            if ($.browser.msie) {
                scrollWidth = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth);
                offsetWidth = Math.max(document.documentElement.offsetWidth, document.body.offsetWidth);
                if (scrollWidth < offsetWidth) {
                    return $(window).width();
                } else {
                    return scrollWidth;
                }
                // handle "good" browsers
            }
            else {
                return $(document).width();
            }
        };
        
        /*==========ȫ������=========*/
        function openLayer() {
            //�������ֲ�
            var layer = $("<div id='layer'></div>");
            layer.css({
                zIndex:9998,
                position: "absolute",
                height: height() + "px",
                width: width() + "px",
                background: "black",
                top: 0,
                left: 0,
                filter: "alpha(opacity=30)",
                opacity: 0.3
              
            });
           
           //ͼƬ�����ֲ�
            var content = $("<div id='content'></div>");
            content.css({
                textAlign: "left",
                position:"absolute",
                zIndex: 9999,
                height: opt.height + "px",
                width: opt.width + "px",
                top: "50%",
                left: "50%",
                verticalAlign: "middle",
                background: opt.backgroudColor,
                borderRadius:"8px",
                fontSize:"13px"
            });
            
            content.append("<img style='vertical-align:middle;margin:"+(opt.height/4)+"px; 0 0 5px;margin-right:5px;' src='" + opt.backgroundImage + "' /><span style='text-align:center; vertical-align:middle;'>" + opt.text + "</span>");
            $("body").append(layer).append(content);
            var top = content.css("top").replace('px','');
            var left = content.css("left").replace('px','');
            content.css("top",(parseFloat(top)-opt.height/2)).css("left",(parseFloat(left)-opt.width/2));
            
           return this;
        }

        //���ٶ���
        function closeLayer() {
            $("#layer,#content,#partialLayer").remove();
            return this;
        }
        
        /*==========�ֲ�����=========*/
        function openPartialLayer(obj) {
         
            var eheight = $(obj).css("height");//Ԫ�ش�px�ĸ߿��
            var ewidth = $(obj).css("width");
            var top = $(obj).offset().top; // Ԫ�����ĵ���λ�� ��������Ӱ��
            var left = $(obj).offset().left;

            var layer = $("<div id='partialLayer'></div>");
            layer.css({
                style: 'z-index:9998',
                position: "absolute",
                height: eheight,
                width: ewidth,
                background: "black",
                top: top,
                left: left,
                filter: "alpha(opacity=60)",
                opacity: 0.6,
                borderRadius:"3px",
                dispaly: "block"
            });
            $("body").append(layer);

            return this;
        }
    };
    
})(jQuery)