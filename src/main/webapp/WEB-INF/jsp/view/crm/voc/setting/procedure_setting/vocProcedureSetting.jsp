<%--
  Created by IntelliJ IDEA.
  User: taewoohan
  Date: 2022/10/20
  Time: 1:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>


<div class="gLeft" data-has-size="Y">
  <div class="mBox1">
    <div class="gTitle1">
      <h3 class="mTitle1">VOC 분류코드</h3>
      <button class="gRt tree_btn func_btn" data-event="manage_grp">그룹관리</button>
    </div>
    <div id="divTree"
         data-type="tree"
         data-get-url="<c:url value='${urlPrefix}/vocManagementCodeTree${urlSuffix}'/>"
         data-change-seq="Y"
    >
    </div>
  </div>
</div>