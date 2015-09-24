/**
 * 线程管理类
 * @author zxub 2006-06-12
 */
function Thread(_task,_delay,_times)
{
    this.runFlag=false;
    this.busyFlag=false;
    this.taskArgs=Array.prototype.slice.call(arguments,3);
    
    if (_times!=undefined)
    {
        this.times=_times;
    }
    else
    {
        this.times=1;
    }
    
    var _point=this;
    
    this.timerID=-1;
    
    this.start=function()
    {
        if (this.runFlag==false)
        {
            this.timerID=window.setInterval(_point.run,_delay);            
            this.runFlag=true;
        }
    }
    
    this.run=function()
    {
        if (_point.busyFlag) return;
        if (_point.times==-1)//无限循环
        {
            _task(_point.taskArgs);
        }
        else if (_point.times>0)
        {
            _task(_point.taskArgs);
            _point.times-=1;
            if (_point.times==0)
            {
                window.clearInterval(this.timerID);
            }                                  
        }        
    }
    
    this.sleep=function()
    {
        this.busyFlag=true;
    }
    
    this.resume=function()
    {
        this.busyFlag=false;
    }
    
    this.abort=function()
    {        
        window.clearInterval(this.timerID);        
    }
}