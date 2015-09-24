var Position=new function()
{
    this.getPosition=function(element)
    {
        if (element==undefined || element==null || element=="") return new Position.instance(0,0,0,0);
        var offsetTop=element.offsetTop;
        var offsetLeft=element.offsetLeft;
        var offsetWidth=element.offsetWidth;
        var offsetHeight=element.offsetHeight;
        while (element==element.offsetParent)
        {
            if (element.style.position=='absolute' || element.style.position=='relative' || (element.style.overflow!='visible' && element.style.overflow!=''))
            {
                break;
            }
            offsetTop+=element.offsetTop;
            offsetLeft+=element.offsetLeft;
        }
        return new Position.instance(offsetTop,offsetLeft,offsetWidth,offsetHeight);
    }
    
    this.inside=function(_source,_target)
    {
        var _tPosition=Position.getPosition(_target);
        var _sPosition=Position.getPosition(_source);
        return (_sPosition.minX>=_tPosition.minX && _sPosition.maxX<=_tPosition.maxX && _sPosition.minY>=_tPosition.minY && _sPosition.maxY<=_tPosition.maxY);
    }
    
    this.outside=function(_source,_target)
    {
        var _tPosition=Position.getPosition(_target);
        var _sPosition=Position.getPosition(_source);
        return (_sPosition.maxX<_tPosition.minX || _sPosition.minX>_tPosition.maxX || _sPosition.minY>_tPosition.maxY || _sPosition.maxY<_tPosition.minY);
    }
}

Position.instance=function(_top,_left,_width,_height)
{
    this.top=_top;
    this.left=_left;
    this.width=_width;
    this.height=_height;
    this.minX=this.left;
    this.maxX=this.left+this.width;
    this.minY=this.top;
    this.maxY=this.top+this.height;
    this.toString=function()
    {
        return "top:"+this.top+";left:"+this.left+";width:"+this.width+";height:"+this.height+";xRange:"+this.minX+"-"+this.maxX+";yRange:"+this.minY+"-"+this.maxY;
    }
}

Position.direction=new function()
{
    this.up=false;
    this.down=false;
    this.left=false;
    this.rgiht=false;
    this.lastX=0;
    this.lastY=0;
    this.setInitValue=function(_x,_y)
    {
        this.lastX=_x;
        this.lastY=_y;
    }
    
    this.setDirection=function(_cX,_cY)
    {
        if (_cX==undefined) _cX=Event.pointerX(event);
        if (_cY==undefined) _cY=Event.pointerY(event);
        this.up=(_cY<this.lastY);
        this.down=!this.up;
        this.left=(_cX<this.lastX);
        this.right=!this.left;
        this.setInitValue(_cX,_cY);
    }
    
    this.toString=function()
    {
        return "up:"+this.up+";down:"+this.down+";left:"+this.left+";right:"+this.right;
    }
}