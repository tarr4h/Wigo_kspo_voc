<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<tiles:insertTemplate template="header.jsp" />
</head>

<body>
	<div id="wrapIframe">
			<div class="mGrid1">
				<tiles:insertAttribute name="content" />
			</div>
	</div>
<script>
if(parent != window && parent["mainIndex"] && parent["resizeTabWnd"]){
	const $body = $(document);
	
	var smartResize = {
			width : -1,
			height :-1,
			init : function(){
				var self = this;
				if(self.chkTimeout)
					clearTimeout(self.chkTimeout);
				var wd = $body.outerWidth();
				var ht = $body.outerHeight();
				if(this.width != wd || this.height != ht){
					this.handler();
				}
				this.width = wd;
				this.height = ht;
				self.chkTimeout = setTimeout(function(){
					self.init();
				}, 100);
			},
			timeOut : '',
			chkTimeout : '',
			handler : function(){
				var self = this;
				if(self.timeOut) clearTimeout(self.timeOut) 
					//setTimeOut 이 걸려있다면 클리어
				self.timeOut = setTimeout(self.action, 150); 
					// 150ms 동안 이벤트가 반복 실행 되지 않으면 action() 함수를 실행
			},
			action : function(){
				try{
					
					parent["resizeTabWnd"](smartResize.width,smartResize.height);
				
				}catch(e){
					console.error(e);
				}
			}
		};
	smartResize.init();
}

</script>
</body>
</html>