mapbuilder.loadScript(baseDir+"/widget/ButtonBase.js");
function ZoomOut(widgetNode,model){
ButtonBase.apply(this,new Array(widgetNode,model));
this.zoomBy=4;
this.doAction=function(objRef,targetNode){
if(!objRef.enabled)return;
var bbox=objRef.targetModel.getParam("aoi");
var extent=objRef.targetModel.extent;
var newRes=extent.res[0]*objRef.zoomBy;
extent.centerAt(bbox[0],newRes);
}
this.setMouseListener=function(objRef){
if(objRef.mouseHandler){
objRef.mouseHandler.model.addListener('mouseup',objRef.doAction,objRef);
}
}
this.model.addListener("loadModel",this.setMouseListener,this);
}
