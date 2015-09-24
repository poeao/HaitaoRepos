/**
 * 事件处理类
 * @author zxub 2006-06-21
 */
var Event=new function()
{
    this.element=function(_event)
    {
        return _event.target || _event.srcElement;        
    }
    
    this.pointerX=function(_event)
    {
        return _event.pageX || (_event.clientX+(document.documentElement.scrollLeft || document.body.scrollLeft));
    }
    
    this.pointerY=function(_event)
    {
        return _event.pageY || (_event.clientY+(document.documentElement.scrollTop || document.body.scrollTop));
    }
    
    this.isLeftClick=function(_event)
    {
        return (((_event.which) && (_event.which==1)) || ((_event.button) && (_event.button==1)));
    }
    
    this.isRightClick=function(_event)
    {
        return (((_event.which) && (_event.which==2)) || ((_event.button) && (_event.button==2)));
    }
    
    this.observers=null;
    
    this.alreadyObserve=function(element,name,observer,useCapture)
    {
        for (var i=0; i<Event.observers.length; i++)
        {
            if (Event.observers[i][0]==element && Event.observers[i][1]==name && Event.observers[i][2]==observer)
        	{
        	    return true;        	    
        	}
        }
        return false;
    }
    
    this.unReg=function(element,name,observer,useCapture)
    {
        for (var i=0; i<Event.observers.length; i++)
        {
        	if (Event.observers[i][0]==element && Event.observers[i][1]==name && Event.observers[i][2]==observer)
        	{
        	    Event.observers[i][0]=null;
        	    Event.observers.splice(i,1);
        	    break;
        	}
        }
    }
    
    /**
     * 防止事件传播，进行中止
     */
    this.stopEvent=function(_event)
    {
        if (_event.preventDefault)
        {
            _event.preventDefault();
            _event.stopPropagation();
        }
        else
        {
            _event.returnValue = false;
            _event.cancelBubble = true;
        }
    }
    
    
    /**
     * 增加事件处理
     * 注意，对于同一个Element,IE和Firefox的处理顺序是相反的，IE用栈的形式，后注册的先执行，而Firefox用队列的形式，先注册先执行
     * 至于useCapture，是当element存在包含关系，事件的处理顺序
     * 处理顺序有2种，capturing和bubbling
     * capturing是从外向内，而bubbling是从内向外
     * IE只支持bubbling，所有useCapture是没有意义的
     * Firefox中，2种都支持，useCapture为false的时候为bubbling，true为capturing，推荐用bubbling
     * 默认useCapture为false
     */
    this.observe=function(element, name, observer, useCapture)
    {
        if (typeof(element)=="string")
        {
            element=document.getElementById(element);
        }
        useCapture = useCapture || false;
        if (this.observers==null)
        {
            this.observers=new Array();
        }        
        //检查是否注册了同样的事件处理
        if (Event.alreadyObserve(element, name, observer, useCapture)) return;        
        if (element.addEventListener) //firefox
        {          
            element.addEventListener(name, observer, useCapture);
        }
        else if (element.attachEvent) //IE
        {
            element.attachEvent("on"+name, observer);
        }
        this.observers.push([element, name, observer, useCapture]);        
    }
    
    this.stopObserving=function(element, name, observer, useCapture)
    {
        if (typeof(element)=="string")
        {
            element=document.getElementById(element);
        }
        useCapture = useCapture || false;
        
        if (element.removeEventListener)
        {
            element.removeEventListener(name, observer, useCapture);
        }
        else if (element.detachEvent)
        {
            element.detachEvent("on"+name, observer);
        }
        Event.unReg(element, name, observer, useCapture);
    }
    
    this.clearObservers=function()
    {
        if (Event.observers==null) return;
        for (var i=0;i<Event.observers.length; i++)
        {
            Event.stopObserving.apply(this, Event.observers[i]);
            Event.observers[i][0] = null;//释放对element的引用
        }
        Event.observers = null;
    }
}

if (window.navigator.userAgent.indexOf("MSIE")>=1)
{
    Event.observe(window,'onunload',Event.clearObservers,false);
}