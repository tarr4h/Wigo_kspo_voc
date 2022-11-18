<%--
  Created by IntelliJ IDEA.
  User: tarr4h
  Date: 2022/11/10
  Time: 4:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<style>
.mBox1{
    border-bottom: none;
}
.procedure_list{
    width: 100%;
    height: 300px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    align-content: center;
    justify-content: space-around;
}
.procedure_box{
    border: 1px solid gray;
    height: 130px;
    width: 100px;
    text-align: center;
}
.box_title{
    min-height: 40%;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-content: center;
    justify-content: center;
}
.compulsory_info{
    min-height: 30%;
    text-align: center;
}
.procedure_box input[type="checkbox"]{
    min-height: 40%;
}

.reg_btn{
    width: 100%;
    height: 53px;
    border: 1px solid gray;
}

</style>

<div class="v_modal_header">
    <h3>절차 등록</h3>
    <button id="close_btn">X</button>
</div>

<div class="content_wrapper">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">절차 선택</h3>
        </div>
        <div class="procedure_list"></div>
    </div>
</div>

<button class="reg_btn" onclick="regPrcd();">등록</button>

<script>
  let orgTarget = null;
  const primaryOrg = 'primaryOrg';
  const subOrg = 'subOrg';
  const dirCd = '${param.dirCd}';

  $(() => {
    setPrcdBasList();
  });

  $('#close_btn').on('click', function(){
      Utilities.closeModal();
  });

  /**
   * 저장
   * @returns {boolean}
   */
  function regPrcd(){
      let selectedPrcd = $('input[type="checkbox"]:checked');

      let prcdArr = [];
      $.each(selectedPrcd, (i, e) => {
         prcdArr.push($(e).val());
      });

      $.ajax({
          url: '<c:url value="${urlPrefix}/insertProcedure${urlSuffix}"/>',
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify({
              prcdArr,
              dirCd
          }),
          success(res, status, jqXHR){
              if(jqXHR.status === 200){
                  alert(`해당 코드에 \${res}건의 절차가 등록되었습니다.`);

                  let opnr = Utilities.getOpener();
                  opnr.location.reload();
                  Utilities.closeModal();
              }
          },
          error: console.log
      });
  }

  /**
   * 절차 box 클릭 시 checkbox check evt
   */
  $('.procedure_list').on('click', '.procedure_box', function(){
      let chkBox = $(this).find('input[type="checkbox"]');
      let disabled = chkBox.prop('disabled');
      if(disabled){
          alert('필수 절차는 변경하실 수 없습니다.');
          return false;
      }

      if(chkBox.prop('checked')){
          chkBox.prop('checked', false);
      } else {
          chkBox.prop('checked', true);
      }
  });

  /**
   * prcd 목록 코드를 화면에 append
   *  - 필수여부값에 따라서 checkbox 선택제어, 상태명 표기
   * @returns {Promise<void>}
   */
  async function setPrcdBasList(){
      let list = await selectPrcdBasList();

      $.each(list, (i, e) => {
          let convertCompulsoryYn = e.regCompulsoryYn === 'Y' ? '필수' : '선택';
          let compulsoryOpt = e.regCompulsoryYn === 'Y' ? 'checked disabled' : '';

          let box = `
                <div class="procedure_box">
                    <div class="box_title">
                        <h3>\${e.prcdNm}</h3>
                    </div>
                    <div class="compulsory_info">
                        <span>\${convertCompulsoryYn}</span>
                    </div>
                    <input type="checkbox" name="reg_prcd" value="\${e.prcdSeq}" \${compulsoryOpt}>
                </div>
          `;
          $(".procedure_list").append(box);
      });
  }

  /**
   * procedure_bas 테이블에서 등록절차에 적용 가능한 절차 조회
   * @returns {Promise<unknown>}
   */
  function selectPrcdBasList() {
      return new Promise(function (resolve) {
          $.ajax({
              url: '<c:url value="${urlPrefix}/selectPrcdBasList${urlSuffix}"/>',
              data : {
                  dirCd
              },
              success(res) {
                  resolve(res);
              },
              error: console.log
          })
      })
  }
</script>