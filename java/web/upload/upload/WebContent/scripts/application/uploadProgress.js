Env.require("UI/panel.js");
Env.require("UI/basic.js");
Env.require("base/thread.js");
Env.require("application/progressBar.js");

window.progressPanel=null;

var detailUrl="";

var callback=function(instance)
{	
	var response=instance.responseText;
	if (response.length!=0)
	{
	    var paras=response.split("||");
	    if(paras != null ){
		    ProgressBar.setPosition(paras[0]);
		    updateDetail(paras[1],paras[2],paras[3],paras[4],paras[5],paras[6]);
	    }
	}
}

function getDetail()
{
    Request.showStatus=false;
    Request.send(detailUrl,"",callback);
}

var t1=new Thread(getDetail,10,-1);

var fileName=null;
var speed=null;
var readTotalSize=null;
var totalSize=null;
var remainTime=null;
var totalTime=null;

function updateDetail(_fileName,_speed,_readTotalSize,_totalSize,_remainTime,_totalTime)
{
    if (window.progressPanel!=null && window.progressPanel.mainDiv!=null)
    {        
        fileName.title=_fileName;
        if (_fileName.length>18) _fileName="..."+_fileName.slice(_fileName.length-15);
        fileName.innerHTML=_fileName;
        speed.nodeValue=_speed;
        readTotalSize.nodeValue=_readTotalSize;
        totalSize.nodeValue=_totalSize;
        remainTime.nodeValue=_remainTime;
        totalTime.nodeValue=_totalTime;
    }
    else
    {
        t1.sleep();
    }
}

//验证上传的文件是不是可上传文件类型
function checkUploadFileType(fileTypes,uploadFile){
	var uploadFileType=uploadFile.substring(uploadFile.lastIndexOf("."));
	fileTypes=fileTypes.split("|");
	for(var i=0;i<fileTypes.length;i++){
		if(fileTypes[i]==uploadFileType){
			return true;
		}
	}
	return false;
}

//路径，可上传文件类型，上传文件
function showProgress(ajaxUrl,fileTypes,uploadFile)
{
	if(!checkUploadFileType(fileTypes,uploadFile)){
		alert("只能上传 "+fileTypes+" 文件！");
		return false;
	}
	detailUrl=ajaxUrl;
	if (window.progressPanel==null || window.progressPanel.mainDiv==null)
	{
	    window.progressPanel=new Panel("数据传输进度",214,160);
	    window.progressPanel.mainDiv.removeChild(window.progressPanel.bottomDiv);
    	var div=UI.$Div("left");
    	div.style.height=18;
    	div.innerHTML="信息：文件上传|";
    	fileName=document.createElement("span");
    	fileName.style.cssText="cursor:pointer";
    	fileName.innerHTML="未知文件";
    	div.appendChild(fileName);    	
    	window.progressPanel.contentDiv.appendChild(div);   
    	div=UI.$Div("left");	
    	ProgressBar.init(200);    	
    	div.style.height=18;
    	div.appendChild(ProgressBar.container);
    	window.progressPanel.contentDiv.appendChild(div); 
    	div=UI.$Div("left");
    	div.style.height=18;
    	div.innerHTML="上传速度：|";
    	speed=UI.$Text("未知");
    	div.appendChild(speed);
    	div.appendChild(UI.$Text(" K/S"));    	
    	window.progressPanel.contentDiv.appendChild(div);
    	div=UI.$Div("left");
    	div.style.height=18;
    	div.innerHTML="已上传：";
    	readTotalSize=UI.$Text("未知");
    	div.appendChild(readTotalSize);
    	div.appendChild(UI.$Text("M 共："));
    	totalSize=UI.$Text("未知");
    	div.appendChild(totalSize);
    	div.appendChild(UI.$Text("M")); 
    	window.progressPanel.contentDiv.appendChild(div);
    	div=UI.$Div("left");
    	div.style.height=18;
    	div.innerHTML="估计剩余时间：";
    	remainTime=UI.$Text("未知");
    	div.appendChild(remainTime);
    	window.progressPanel.contentDiv.appendChild(div);
    	div=UI.$Div("left");
    	div.style.height=18;
    	div.innerHTML="估计总时间";
    	totalTime=UI.$Text("未知");
    	div.appendChild(totalTime);
    	window.progressPanel.contentDiv.appendChild(div);
    	
    	window.progressPanel.otherCloseAction=function()
    	{
    	    t1.sleep();
    	}
    		
    	if (t1.runFlag)
    	{
    	    t1.resume();
    	}
    	else
    	{
    	    t1.start();
    	}
	}
}