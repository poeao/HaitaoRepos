'comment run some cmd without window 

Set ws = CreateObject("Wscript.Shell") 
ws.run "cmd /c zh_staticIP.cmd",vbhide 