var ProgressBar=new function()
{
    this.min=0;
    this.max=100;
    
    this.width=200;
    this.height=18;
    this.currPos=0;
    
    this.container=null;
    this.backgroundDiv=null;
    this.progressDiv=null;
    this.textDiv=null;
    
    this.init=function(_width)
    {
        if (_width!=undefined)
        {
            this.width=_width;
        }        
        if (this.container!=null) return;
        this.container=document.createElement("div");
        this.container.style.cssText="width:"+(this.width+2)+"px;height:1px";
        
        this.backgroundDiv=document.createElement("div");
        this.backgroundDiv.style.cssText="position:absolute;border:1px solid black;width:"+this.width+"px;height:16px;background-color:#47649A";
        this.container.appendChild(this.backgroundDiv);
        
        this.progressDiv=document.createElement("div");
        this.progressDiv.style.cssText="position:absolute;border:1px solid black;width:0px;height:16px;background-color:red";
        this.container.appendChild(this.progressDiv);
        
        this.textDiv=document.createElement("div");
        this.textDiv.style.cssText="position:absolute;border:1px solid black;width:"+this.width+"px;height:16px;font-weight:bold;color:white;font-size:12px";
        this.textDiv.innerHTML="0%";
        this.textDiv.align="center";
        this.container.appendChild(this.textDiv);
        
        document.body.appendChild(this.container);
    }
    
    this.setPosition=function(_position)
    {
        this.progressDiv.style.width=parseInt(this.width*_position/100);
        this.textDiv.innerHTML=_position+"%";
    }
}