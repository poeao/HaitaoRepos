/**
 * @author zxub 2006-06-01
 * 状态信息显示类
 */

var Status=new function()
{
    this.statusDiv=null;    
    this.processBar=null;
    this.closeButton=null;    
    this.mainInfo=null;
    this.subInfo=null; 
    
    this.init=function()
    {
        if (this.statusDiv!=null)
	    {
	        return;
	    }
	    var _div=document.createElement("div");
		_div.id="status";
		_div.style.cssText="overflow:visible;width:300px;left:50%;margin-left:-150px;position:absolute;height:150px;top:50%;margin-top:-100px;background-color:transparent";
		_div.style.zIndex=1000;
		document.body.insertBefore(_div,document.body.firstChild);
		this.statusDiv=_div;
		var _table=document.createElement("table");
		_table.style.cssText="border-right:1px solid gray;border-top: 1px solid white;border-left:1px solid white;border-bottom:1px solid gray;width:300px;background-color:#ebeadb";
		_div.appendChild(_table);
				
		_tr=_table.insertRow(-1);
		this.processBar=_tr;		
		_cell=_tr.insertCell(-1);
		_cell.align="middle";
		_cell=_tr.insertCell(-1);
		_cell.align="middle";
		_cell.style.cssText="padding-top:8px;padding-bottom:3px;border-bottom:1px solid #898989";
		var _img=document.createElement("img");
		_img.style.cssText="border-right:1px solid white;border-top:1px solid gray;border-left:1px solid gray;border-bottom:1px solid white";
		_img.src=Env.envPath+"waiting.gif";
		_cell.appendChild(_img);
		var _input=document.createElement("input");
		_input.type="button";
		_input.style.cssText="background-color:#ebeadb;border:none;padding-top:2px;margin-left:10px;cursor:pointer";
		_input.onclick=function()
		{
		    //关闭状态栏
		    this.blur();
		    Status.setStatusShow(false);
		}
		_input.value="关闭";
		_cell.appendChild(_input);
		_cell=_tr.insertCell(-1);		
		
		this.closeButton=_input;
		
		_tr=_table.insertRow(-1);
		_cell=_tr.insertCell(-1);
		_cell.style.cssText="width:6px";
		_cell=_tr.insertCell(-1);
		_cell.style.cssText="font-size:12px;line-height:20px;width:288px;padding-top:5px";		
		_cell.align="middle";
		this.mainInfo=_cell;		
		_cell=_tr.insertCell(-1);
		_cell.style.cssText="width:6px";
				
		_tr=_table.insertRow(-1);
		_cell=_tr.insertCell(-1);		
		_cell=_tr.insertCell(-1);
		var _div2=document.createElement("div");
		_div2.id="subInfo";
		_div2.style.cssText="margin-top:3px;margin-bottom:6px;border-right:1px solid white;border-top:1px solid gray;font-size:12px;overflow:auto;border-left:1px solid gray;border-bottom:1px solid white;background-color:white;padding:5px 0px 5px 5px;line-height:18px";				
		_cell.appendChild(_div2);
		this.subInfo=_div2;
		_cell=_tr.insertCell(-1);		
		
		this.subInfo.style.display="none";		
    }
    
    this.checkInit=function()
    {
        if (this.statusDiv==null)
	    {
	        this.init();
	    }	    
    }
    
    this.setStatusShow=function(_show)
    {
        this.checkInit();         
	    this.statusDiv.style.display=(_show)?"":"none";
	    if (_show==false)
	    {
	        this.processBar.style.display="";
	        this.clearSubInfo();
	    }
    }
    
    this.setProcessShow=function(_show)
    {
        this.checkInit();
        _show=(_show==undefined)?true:_show;
	    this.processBar.style.display=(_show)?"":"none";
    }    
    
    this.setMainInfo=function(_msg,_needRefresh)
    {
        this.checkInit();
        this.mainInfo.innerHTML=_msg;
        _needRefresh=(_needRefresh==undefined)?true:false;
        this.mainInfo.innerHTML+=(_needRefresh)?"<br>若系统长时间停止响应，请<a href='javascript:document.location.reload(true);'>刷新</a>。 ":"";
        this.setStatusShow(true);
    }
    
    this.addSubInfo=function(_msg)
    {
        this.checkInit();
        this.subInfo.innerHTML+=_msg+"<br>";
        this.subInfo.style.display="";
    }
    
    this.clearSubInfo=function()
    {
        this.checkInit();
        this.subInfo.innerHTML="";
        this.subInfo.style.display="none";        
    }
    
    this.showErrInfo=function(_URI,_line,_message)
    {
        this.setProcessShow(false);
        this.setMainInfo("由于发生错误，程序无法继续：",false);
        this.addSubInfo(_URI+"<br>"+_line+"<br><font color=red>"+_message+"</fomt>");
    }
}

/**
 * @author zxub
 * 用于存放通道名称及通信对象的类，这样可以通过不同通道名称来区分不同的通信对象
 */
function HttpRequestObject()
{
    this.chunnel=null;
    this.instance=null;
}

/**
 * @author zxub
 * 通信处理类，可以静态引用其中的方法
 */
var Request=new function()
{
    this.showStatus=true;
    
    //通信类的缓存
    this.httpRequestCache=new Array();
    
    /**
     * 创建新的通信对象
     * @return 一个新的通信对象
     */
    this.createInstance=function()
    {
        var instance=null;
        if (window.XMLHttpRequest)
        {
            //mozilla
            instance=new XMLHttpRequest();
            //有些版本的Mozilla浏览器处理服务器返回的未包含XML mime-type头部信息的内容时会出错。因此，要确保返回的内容包含text/xml信息
            if (instance.overrideMimeType)
            {
                instance.overrideMimeType="text/xml";
            }
        }
        else if (window.ActiveXObject)
        {
            //IE
            var MSXML = ['MSXML2.XMLHTTP.5.0', 'Microsoft.XMLHTTP', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP'];
            for(var i = 0; i < MSXML.length; i++)
            {
                try
                {
                    instance = new ActiveXObject(MSXML[i]);
                    break;
                }
                catch(e)
                {
                }
            }
        }
        return instance;
    }
    
    /**
     * 获取一个通信对象
     * 若没指定通道名称，则默认通道名为"default"
     * 若缓存中不存在需要的通信类，则创建一个，同时放入通信类缓存中
     * @param _chunnel：通道名称，若不存在此参数，则默认为"default"
     * @return 一个通信对象，其存放于通信类缓存中
     */
    this.getInstance=function(_chunnel)
    {
        var instance=null;
        var object=null;
        if (_chunnel==undefined)//没指定通道名称
        {
            _chunnel="default";
        }
        var getOne=false;
        for(var i=0; i<this.httpRequestCache; i++)
        {
            object=HttpRequestObject(this.httpRequestCache[i]);
            if (object.chunnel==_chunnel)
            {
                if (object.instance.readyState==0 || object.instance.readyState==4)
                {
                    instance=object.instance;
                }
                getOne=true;
                break;                    
            }
        }
        if (!getOne) //对象不在缓存中，则创建
        {
            object=new HttpRequestObject();
            object.chunnel=_chunnel;
            object.instance=this.createInstance();
            this.httpRequestCache.push(object);
            instance=object.instance;
        }         
        return instance;
    }
    
    /**
     * 客户端向服务端发送请求
     * @param _url:请求目的
     * @param _data:要发送的数据
     * @param _processRequest:用于处理返回结果的函数，其定义可以在别的地方，需要有一个参数，即要处理的通信对象
     * @param _chunnel:通道名称，默认为"default"
     * @param _asynchronous:是否异步处理，默认为true,即异步处理
     */
    this.send=function(_url,_data,_processRequest,_chunnel,_asynchronous)
    {
        if (_url.length==0 || _url.indexOf("?")==0)
        {
         
            window.setTimeout("Status.setStatusShow(false)",5000);
            return;
        }
        if (this.showStatus)
        {
            Status.setMainInfo("正在与服务器交换数据，请稍候...");  
        }
        if (_chunnel==undefined || _chunnel=="")
        {
            _chunnel="default";
        }
        if (_asynchronous==undefined)
        {
            _asynchronous=true;
        }
        var instance=this.getInstance(_chunnel);
        if (instance==null)
        {
          
            window.setTimeout("Status.setStatusShow(false)",5000);
            return;
        }  
        if (_asynchronous==true && typeof(_processRequest)=="function")
        {
            instance.onreadystatechange=function()
            {
                if (instance.readyState == 4) // 判断对象状态
                {
                    if (instance.status == 200) // 信息已经成功返回，开始处理信息
                    {
                        try
                    	{
                    	    _processRequest(instance);
                    	    Status.setStatusShow(false);
                    	    Request.showStatus=true;
                    	}
                        catch(e)
                        {
                           
                            window.setTimeout("Status.setStatusShow(false)",5000);
                        }
                    }
                    else
                    {
                      
                        window.setTimeout("Status.setStatusShow(false)",5000);
                    }
                }
            }
        }
        //_url加一个时刻改变的参数，防止由于被浏览器缓存后同样的请求不向服务器发送请求
        if (_url.indexOf("?")!=-1)
        {
            _url+="&requestTime="+(new Date()).getTime();
        }
        else
        {
            _url+="?requestTime="+(new Date()).getTime();
        }
        if (_data.length==0)
        {
            instance.open("GET",_url,_asynchronous);          
            instance.send(null);            
        }
        else
        {
            instance.open("POST",_url,_asynchronous);
            instance.setRequestHeader("Content-Length",_data.length);
            instance.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            instance.send(_data);
        }
        if (_asynchronous==false && typeof(_processRequest)=="function")
        {
            _processRequest(instance);
            if (Request.showStatus)
            {
                Status.setStatusShow(false);
            }
            else
            {
                Request.showStatus=true;
            }
        }
    }
    
    /**
     * 间隔一段时间持续发送请求，只用于异步处理，只用于GET方式
     * @param _interval:请求间隔，以毫秒计
     * @param _url:请求地址
     * @param _processRequest:用于处理返回结果的函数，其定义可以在别的地方，需要有一个参数，即要处理的通信对象
     * @param _chunnel:通道名称，默认为"defaultInterval"，非必添
     */
    this.intervalSend=function(_interval,_url,_processRequest,_chunnel)
    {
        var action=function()
        {
            if (_chunnel==undefined)
            {
                _chunnel="defaultInterval";
            }
            var instance=Request.getInstance(_chunnel);
            if (instance==null)
            {
               
                window.setTimeout("Status.setStatusShow(false)",5000);
                return;
            }
            if (typeof(_processRequest)=="function")
            {
                instance.onreadystatechange=function()
                {
                    if (instance.readyState == 4) // 判断对象状态
                    {
                        if (instance.status == 200) // 信息已经成功返回，开始处理信息
                        {
                            _processRequest(instance);
                        }
                        else
                        {
                         
                            window.setTimeout("Status.setStatusShow(false)",5000);
                        }
                    }
                }
            }
            //_url加一个时刻改变的参数，防止由于被浏览器缓存后同样的请求不向服务器发送请求
            if (_url.indexOf("?")!=-1)
            {
                _url+="&requestTime="+(new Date()).getTime();
            }
            else
            {
                _url+="?requestTime="+(new Date()).getTime();
            }
            instance.open("GET",_url,true);
            instance.send(null);
        }
        window.setInterval(action,_interval);        
    }
}

var Env=new function()
{   
    this.funcList=new Array();
        
    this.envPath=null;
    
    this.selfName="env.js";
    
    this.getPath=function()
    {
        this.envPath=document.location.pathname;
        this.envPath=this.envPath.substring(0,this.envPath.lastIndexOf("/")+1);        
        var _scripts=document.getElementsByTagName("script");
        var _envPath=null;
        var _scriptSrc=null;
        for (var i=0; i<_scripts.length; i++)
        {
            _scriptSrc=_scripts[i].getAttribute("src");
        	if (_scriptSrc && _scriptSrc.indexOf(this.selfName)!=-1)
        	{
        	    break;
        	}
        }
        if (_scriptSrc!=null)
        {
            if (_scriptSrc.charAt(0)=='/')
            {
                this.envPath=_scriptSrc.substr(0,_scriptSrc.length-this.selfName.length);
            }
            else
            {
                this.envPath=this.envPath+_scriptSrc.substr(0,_scriptSrc.length-this.selfName.length);
            }
        }        
    }
    this.getPath();    
    
    /**
     * 按需获取需要的js文件
     * @param _jsName：js文件路径，若为相对路径，则是对应当前js(env.js)的相对路径，也可以用绝对路径
     * @param _language:对返回函数进行处理的语言，默认为JScript，可不填
     */
    this.require=function(_jsName,_language)
    {
        var _absJsName=null;
        if (_jsName.charAt(0)=='/')
        {
            _absJsName=_jsName;
        }
        else
        {
            _absJsName=this.envPath+_jsName;
        }        
        if (!Env.funcList[_absJsName])
        {
            Env.funcList[_absJsName]="finished";
            var processJs=function(_instance)
            {
                //为兼容firefox做判断
                if (_language!=undefined)
                {
                    if (window.execScript)
                    {
                        window.execScript(_instance.responseText,_language);
                    }
                    else
                    {
                        window.eval(_instance.responseText,_language);
                    }                                       
                }
                else
                {
                    if (window.execScript)
                    {
                        window.execScript(_instance.responseText);
                    }
                    else
                    {
                        window.eval(_instance.responseText);
                    }                    
                }               
            }
            Request.showStatus=false;
            Request.send(_absJsName,"",processJs,"",false);
        }
    }
    
    /**
     * 该函数的效果是在应用它的script块后加一个script块
     * 是由document.write在script块中的执行顺序决定的
     */
    this.getJs=function(_jsName)
    {
        if (!Env.funcList[_jsName])
        {
            Env.funcList[_jsName]="finished";
            document.write('<scr'+'ipt type="text/javascript" src="'+_jsName+'"></'+'scr'+'ipt>');
        }
    }
}

/**
 * ajax调用远程页面后，远程页面中script块未执行的处理
 */
function reloadJs(_language)
{
    var _c=document.getElementsByTagName("SCRIPT");
    for (var i=0;i<_c.length;i++)
    {
        if (_c[i].src)
        {
            var _s=document.createElement("script");
            _s.type="text/javascript";
            _s.src=_c[i].src;
            //为兼容firefox不用_c[0].insertAdjacentElement("beforeBegin",_s)
            _c[0].parentNode.insertBefore(_s,_c[0]);            
            _c[i].parentNode.removeChild(_c[i]);
        }
        else if (_c[i].text)
        {
            if (_language!=undefined)
            {
                if (window.execScript)
                {
                    window.execScript(_c[i].text,_language);
                }
                else
                {
                    window.eval(_c[i].text,_language);
                }
            }
            else
            {
                if (window.execScript)
                {
                    window.execScript(_c[i].text);
                }
                else
                {
                    window.eval(_c[i].text);
                }
            }
        }
    }
}

window.onerror=handleError;

function handleError(_message, _URI, _line)
{
	Status.showErrInfo(_URI,_line,_message); // 提示用户这个页面可能无法正常响应
    return true; // 停止默认的消息
}