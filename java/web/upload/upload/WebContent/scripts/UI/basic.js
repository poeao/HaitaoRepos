var UI=new function()
{
    this.$Input=function(_id,_width,_type)
    {
        var _input=document.createElement("input");
        _input.id=_id;
        _input.setAttribute("name",_id);
        _input.style.cssText="font-size:12px;width:"+_width+"px;height:16px;border-left:1px solid gray;border-bottom: 1px solid white;border-right:1px solid white;border-top:1px solid gray";
        _type=_type || "text";
        _input.type=_type;
        return _input;
    }
    
    this.$Div=function(_align)
    {
        _align=_align || "left";
        var _div=document.createElement("div");
        _div.align=_align;
        _div.style.cssText="padding:3px 3px 3px 3px";
        return _div;    
    }
    
    this.$Text=function(_value)
    {
        return document.createTextNode(_value);
    }
    
    this.$button=function(_id,_value)
    {
        var _button=document.createElement("input");
        _button.type="button";
        _button.value=_value;
        _button.style.cssText="height:16px;font-size:12px;border:1px solid black;background-color:transparent"
        return _button;
    }
}



