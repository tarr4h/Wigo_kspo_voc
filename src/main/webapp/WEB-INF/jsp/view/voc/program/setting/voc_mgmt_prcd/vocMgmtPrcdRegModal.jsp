<%--
  @author: tarr4h
  @date: 2023-01-06
  @time: AM 9:37
  @description
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld" %>


<div class="grid_wrapper">
    <div id="divGrid1"
         data-get-url="<c:url value='${urlPrefix}/selectDirOrgGrid${urlSuffix}'/>"
         data-grid-id="dirOrgGrid"
         data-type="grid"
         data-tpl-url="<c:url value='/static/gridTemplate/voc/vocDirOrg.xml${urlSuffix}'/>"
         style="width:100%;height:200px;"
    >
    </div>
</div>