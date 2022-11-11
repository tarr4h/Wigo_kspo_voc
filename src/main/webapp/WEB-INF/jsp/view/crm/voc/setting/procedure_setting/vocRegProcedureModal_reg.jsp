<%--
  Created by IntelliJ IDEA.
  User: taewoohan
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
    height: 200px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    align-content: center;
    justify-content: space-around;
}
.procedure_box{
    border: 1px solid gray;
    height: 100px;
    width: 80px;
    text-align: center;
}
.box_title{
    height: 50%;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-content: center;
    justify-content: center;
}
.compulsory_info{
    height: 20%;
    text-align: center;
}
.procedure_box input[type="checkbox"]{
    height: 30%;
}

.org_mapping{
    height: 50px;
    display: flex;
    align-content: center;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
}
.org_mapping input{
    height: 30px;
    width: 200px;
    border: 1px solid gray;
    margin-right: 10px;
}
.org_mapping button{
    border: 1px solid gray;
    width: 50px;
    font-size: 12px;
}

.reg_btn{
    width: 100%;
    height: 53px;
    border: 1px solid gray;
}

</style>

<div class="v_modal_header">
    <h3>분류코드 등록모달</h3>
    <button id="close_btn">X</button>
</div>

<div class="content_wrapper">
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">절차 선택</h3>
        </div>
        <div class="procedure_list"></div>
    </div>
    <div class="mBox1">
        <div class="gTitle1">
            <h3 class="mTitle1">부서 지정</h3>
        </div>
        <div class="org_mapping">
            <input type="text" id="orgNm" data-org-id="">
            <button id="orgSearch">검색</button>
        </div>
    </div>
</div>

<button class="reg_btn" onclick="regPrcd();">등록</button>

<script>
  $(() => {
    setPrcdBasList();
  });

  $('#close_btn').on('click', function(){
      Utilities.closeModal();
  });

  $('#orgSearch').on('click', function(){
     openComnModal('vocOrgSearchModal', 950, 650);
  });

  function regPrcd(){
      let deptId = $('#orgNm').data('org-id');
      if(deptId == null || deptId === ''){
          alert('부서 지정은 필수입니다.');
          $('#deptNm').focus();
          return false;
      };

      let managementCd = '${param.managementCd}';
      let selectedPrcd = $('input[type="checkbox"]:checked');

      let arr = [];
      $.each(selectedPrcd, (i, e) => {
         arr.push($(e).val());
      });

      $.ajax({
          url: '<c:url value="${urlPrefix}/insertProcedure${urlSuffix}"/>',
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify({
              arr,
              managementCd,
              deptId
          }),
          success(res){
              console.log(res);
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
   * @returns {Promise<void>}
   */
  async function setPrcdBasList(){
      let list = await selectPrcdBasList();

      $.each(list, (i, e) => {
          let convertCompulsoryYn = e.regCompulsoryYn === 'Y' ? '필수입니다.' : '선택입니다.';
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
              success(res) {
                  resolve(res);
              },
              error: console.log
          })
      })
  }

  /**
   * 부서 검색 callback
   * @param data
   */
  function orgSearchCallback(data){
      console.log(data);
      let orgId = data.orgId;
      let orgNm = data.orgNm;

      $('#orgNm').val(orgNm).data('org-id', orgId);
  }

  /**
   * 공통 모달 open
   * @param pageNm
   * @param width
   * @param height
   */
  function openComnModal(pageNm, width, height){
      let url = `<c:url value='${urlPrefix}/openComnModal${urlSuffix}'/>/\${pageNm}`;
      Utilities.openModal(url, width, height);
  }

</script>