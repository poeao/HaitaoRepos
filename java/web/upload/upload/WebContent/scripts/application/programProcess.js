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
		    updateDetail(paras[1],paras[2],paras[3]);
	    }
	}
}

function getDetail()
{
    Request.showStatus=false;
   
    Request.send(detailUrl,"",callback);
}

var t1=new Thread(getDetail,10,-1);



var processBarName=null;
var currentProNum=null;
var currentDetail=null;
var processMaxNum=null;



function updateDetail(processBarName,currentDetail,processMaxNum)
{
    if (window.progressPanel!=null && window.progressPanel.mainDiv!=null)
    {        
    	processBarName.nodeValue=processBarName;
    	currentDetail.nodeValue=currentDetail;
    	processMaxNum.nodeValue=processMaxNum;
    }
    else
    {
        t1.sleep();
    }
}

function showProgress(ajaxUrl)
{
	detailUrl=ajaxUrl;
	if (window.progressPanel==null || window.progressPanel.mainDiv==null)
	{
	    window.progressPanel=new Panel(processBarName,214,160);
	    window.progressPanel.mainDiv.removeChild(window.progressPanel.bottomDiv);
    	var div=UI.$Div("left");
    	div.style.height=18;
    	window.progressPanel.contentDiv.appendChild(div);   
    	div=UI.$Div("left");	
    	ProgressBar.init(processMaxNum);    	
    	div.style.height=18;
    	div.appendChild(ProgressBar.container);
    	window.progressPanel.contentDiv.appendChild(div); 
    	div=UI.$Div("left");
    	div.style.height=18;
    	div.innerHTML=currentDetail;
    	//remainTime=UI.$Text("未知");
    	//div.appendChild(remainTime);
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