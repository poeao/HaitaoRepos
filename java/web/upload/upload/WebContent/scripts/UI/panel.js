Env.require("base/event.js");
Env.require("base/position.js");

function Panel(_caption,_width,_height,_confirmFunction)
{
    this.icon=new Array();	
	this.icon["minimize"]=Env.envPath+'/UI/images/minimize.gif';
	this.icon["restore"]=Env.envPath+'/UI/images/restore.gif';
	this.icon["close"]=Env.envPath+'/UI/images/close.gif';
    
    this.caption=_caption;
    this.width=_width;
    this.height=_height;
    
    this.toolDiv=null;//工具栏
    this.mainDiv=null;
    this.captionDiv=null;
    this.contentDiv=null;
    this.bottomDiv=null;
    
    this.confirmButton=null;
    this.closeButton=null;
    
    this.confirmFunction=(_confirmFunction==undefined)?{}:_confirmFunction;
    
    this.init=function()
    {
        this.mainDiv=document.createElement("div");
        this.mainDiv.style.cssText="padding:2px 2px 2px 2px;position:absolute;width:"+_width+"px;left:50%;margin-left:-"+(_width/2)+"px;top:50%;margin-top:-"+(_height/2+50)+"px;border-right:1px solid gray;border-top: 1px solid white;border-left:1px solid white;border-bottom:1px solid gray;background-color:#F2F1ED";
        document.body.appendChild(this.mainDiv);
        this.mainDiv.handler=this;
        
        this.toolDiv=document.createElement("div");
        this.toolDiv.style.cssText="background-color:#47649A;height:20px;float:right;margin-left:-3px";
        this.mainDiv.appendChild(this.toolDiv);
        var _img=document.createElement("img");
        _img.src=this.icon["minimize"];
        _img.style.cssText="margin-top:2px;cursor:pointer";
        _img.title="最小化";
        _img.handler=this;
        _img.onclick=function(event)
        {
            this.handler.minimize();
        }
        this.toolDiv.appendChild(_img);
        _img=document.createElement("img");
        _img.src=this.icon["restore"];
        _img.title="恢复";
        _img.style.cssText="margin-top:2px;cursor:pointer";
        _img.handler=this;
        _img.onclick=function(event)
        {
            this.handler.restore();
        }
        this.toolDiv.appendChild(_img);
        _img=document.createElement("img");
        _img.src=this.icon["close"];
        _img.title="关闭";
        _img.handler=this;
        _img.onclick=function(event)
        {
            this.handler.destroy();            
        }
        _img.style.cssText="margin-top:2px;cursor:pointer";
        this.toolDiv.appendChild(_img);        
        
        this.captionDiv=document.createElement("div");
        this.captionDiv.style.cssText="background-color:#47649A;color:white;height:20px;font-size:13px;line-height:20px;font-weight:bold;padding-left:10px";
        this.captionDiv.innerHTML=this.caption;
        this.mainDiv.appendChild(this.captionDiv);
        this.captionDiv.handler=this.mainDiv;
        
        Event.observe(this.captionDiv,"mousedown",captionMouseDown);
        Event.observe(this.captionDiv,"mouseup",captionMouseUp);
        
        this.contentDiv=document.createElement("div");
        this.contentDiv.style.cssText="height:"+(this.height-20-20)+"px;padding-top:3px";
        this.mainDiv.appendChild(this.contentDiv);
        
        this.bottomDiv=document.createElement("div");
        this.bottomDiv.align="center";
        this.bottomDiv.style.cssText="background-color:transparent;height:20px";
        this.mainDiv.appendChild(this.bottomDiv);
        this.confirmButton=document.createElement("input");
        this.confirmButton.style.cssText="border:none;padding-top:2px;background-color:transparent";
        this.confirmButton.type="button";
        this.confirmButton.value="确认";
        this.confirmButton.confirmFunction=this.confirmFunction;
        this.confirmButton.handler=this;
        this.confirmButton.onclick=function(event)
        {
            this.confirmFunction();            
        }
        this.bottomDiv.appendChild(this.confirmButton);
        
        this.closeButton=document.createElement("input");
        this.closeButton.style.cssText="border:none;padding-top:2px;background-color:transparent;margin-left:20px";
        this.closeButton.type="button";
        this.closeButton.value="关闭";
        this.closeButton.handler=this;
        this.closeButton.onclick=function(event)
        {
            this.handler.destroy();            
        }
        this.bottomDiv.appendChild(this.closeButton);
    }
    
    this.init();
    
    this.show=function(_show)
    {
        _show=_show || false;
        this.mainDiv.style.display=(_show)?"":"none";
    }
    
    this.destroy=function()
    {
        this.mainDiv.parentNode.removeChild(this.mainDiv);
        this.mainDiv=null;
        this.captionDiv=null;
        this.contentDiv=null;
        this.bottomDiv=null; 
        
        this.confirmButton=null;
        this.closeButton=null; 
        
        this.otherCloseAction();
    }
    
    this.otherCloseAction=function()
    {
        
    }
    
    this.minimize=function()
    {
        this.contentDiv.style.display="none";
        this.bottomDiv.style.display="none";
    }
    
    this.restore=function()
    {
        this.contentDiv.style.display="";
        this.bottomDiv.style.display="";
    }
}

var captionMouseDown=function(event)
{
    var caption=Event.element(event);
    window.dragObj=caption.handler;
    
    var p=Position.getPosition(window.dragObj);
    window.dragObj.style.marginTop="0px";
    window.dragObj.style.marginLeft="0px";
    var x=Event.pointerX(event);
    var y=Event.pointerY(event);
    window.topDistance=y-p.top;
    window.leftDistance=x-p.left;
    window.dragObj.style.top=p.top;
    window.dragObj.style.left=p.left;
}

var captionMouseUp=function(event)
{
    var caption=Event.element(event);
    window.dragObj=null; 
    window.topDistance=0;
    window.leftDistance=0;   
}

var captionMove=function(event)
{
    if (window.dragObj!=null)
    {
        var x=Event.pointerX(event);
        var y=Event.pointerY(event);       
        
        window.dragObj.style.top=y-window.topDistance;
        window.dragObj.style.left=x-window.leftDistance;
    }
}

Event.observe(document,"mousemove",captionMove);
    