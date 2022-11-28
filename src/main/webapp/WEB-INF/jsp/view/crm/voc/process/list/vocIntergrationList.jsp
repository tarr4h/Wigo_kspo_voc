<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-23
  Time: AM 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>

</style>



<div class="v_header">
    <div class="v_header_title">
        <h3>VOC 통합목록</h3>
    </div>
</div>

<div class="content_wrapper">


    <div class="divWrapper">
        <div id="divGrid1"
             data-get-url="<c:url value='${urlPrefix}/selectList${urlSuffix}'/>"
             data-grid-id="listGrid"
             data-type="grid"
             data-tpl-url="<c:url value='/static/gridTemplate/voc/vocIntergrationList.xml${urlSuffix}'/>"
             style="width:100%;height:500px;"
        >
        </div>
    </div>
</div>

<script>
    function onGridLoad(){
        let param = {};
        window['listGrid'].loadUrl('', param);
    }
</script>