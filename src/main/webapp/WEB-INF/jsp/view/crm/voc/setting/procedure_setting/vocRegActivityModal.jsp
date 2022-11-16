<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022-11-16
  Time: PM 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
  .content_wrapper {
    padding: 10px;
  }

  #regBtn {
    width: 100%;
    height: 35px;
    font-size: 13px;
    font-weight: 500;
    border: 1px solid gray;
  }
</style>

<div class="v_modal_header">
    <h3>ACTIVITY 등록</h3>
    <button id="close_btn">X</button>
</div>


<script>
    $("#close_btn").on('click', function () {
        Utilities.closeModal();
    });
</script>