<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022/10/19
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
    .header{
        height: 15px;
        padding: 13px;
        background-color: #676767;
    }
    .header h3{
        display: inline;
        font-weight: 500;
        color: white;
    }
    .header button{
        float: right;
        border: none;
        background-color: inherit;
        color: white;
    }

    .reg_table{
        width: 100%;
        border-collapse: collapse;
    }
    .reg_table th{
        border: 1px solid gray;
        background-color: #e8eeef;
    }
    .reg_table td{
        border: 1px solid gray;
        height: 50px;
        text-align: center;
    }
    .register_input {
        width: 210px;
        height: 26px;
        text-align: center;
    }

    .reg_btn{
        width: 100%;
        height: 36px;
        border: 1px solid gray;
        border-top: none;
    }
</style>

<div class="header">
    <h3>분류코드 등록모달</h3>
    <button id="close_btn">X</button>
</div>

<section>
    <table class="reg_table">
        <tbody>
            <tr>
                <th>상위코드</th>
                <td>
                    <span id="prntsMgmtCdNm"></span>
                </td>
            </tr>
            <tr>
                <th>등록 코드명</th>
                <td>
                    <input type="text" class="register_input" id="mgmtCdNm">
                </td>
            </tr>
        </tbody>
    </table>
    <button id="register_btn" class="reg_btn">등록</button>
</section>


<script>
    let prntsMgmtCd = '${param.prntsMgmtCd}';
    let topComnCd;
    let comnCd;

    $(() => {
        getPrntsInfo();
    });

    // eventListener
    $('#register_btn').on('click', function(e){
        regCode();
    });

    $('#close_btn').on('click', function(e){
        Utilities.closeModal();
    })

    function regCode(){
        let mgmtCdNm = $("#mgmtCdNm").val();

        $.ajax({
            url : '<c:url value="${urlPrefix}/insert${urlSuffix}"/>',
            method : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({
                prntsMgmtCd,
                topComnCd,
                comnCd,
                mgmtCdNm
            }),
            success(res){
                alert(res.msg);
                if(res.result){
                    let opnr = Utilities.getOpener();
                    opnr.location.reload();

                    Utilities.closeModal();
                }
            },
            error: console.log,
            complete: function(){

            }
        });
    }

    function getPrntsInfo(){
        $.ajax({
            url : '<c:url value="${urlPrefix}/vocMgmtCd${urlSuffix}"/>',
            data : {
                mgmtCd : prntsMgmtCd
            },
            success(res){
                $("#prntsMgmtCdNm").text(res.mgmtCdNm);
                topComnCd = res.topComnCd;
                comnCd = res.comnCd;
            },
            error: console.log
        });
    }
</script>


