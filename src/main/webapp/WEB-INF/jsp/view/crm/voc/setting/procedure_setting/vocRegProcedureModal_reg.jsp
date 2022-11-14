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

.org_mapping{
    height: 50px;
    display: flex;
    align-content: center;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
}
.org_mapping span{
    width: 88px;
    height: 32px;
    font-size: 14px;
    padding-top: 5px;
    font-weight: 600;
}
.org_mapping input{
    height: 32px;
    width: 200px;
    border: 1px solid gray;
    margin-right: 10px;
}
.org_mapping button{
    border: 1px solid gray;
    width: 50px;
    height: 32px;
    font-size: 12px;
}
.org_wrapper{
    height: 130px;
    overflow: auto;
    margin-top: 20px;
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
            <div class="gRt">
                <a class="mBtn1" id="addSubOrgBtn">부처리부서 추가</a>
            </div>
        </div>
        <div class="org_wrapper">
            <div class="org_mapping">
                <span class="org_mapping_title">주처리부서</span>
                <input type="text" id="orgNm" data-org-id="" data-primary-org="Y" disabled>
                <button id="orgSearch">검색</button>
            </div>
            <div class="sub_org_list">
            </div>
        </div>
    </div>
</div>

<button class="reg_btn" onclick="regPrcd();">등록</button>

<script>
  let orgTarget = null;
  const primaryOrg = 'primaryOrg';
  const subOrg = 'subOrg';

  $(() => {
    setPrcdBasList();
  });

  $('#close_btn').on('click', function(){
      Utilities.closeModal();
  });

  $('#orgSearch').on('click', function(){
      orgTarget = primaryOrg;
     openComnModal('vocOrgSearchModal', 950, 650);
  });

  $('#addSubOrgBtn').on('click', function (){
      orgTarget = subOrg;
      openComnModal('vocOrgSearchModal', 950, 650);
  });

  $('.org_wrapper').on('click', '.deleteOrgMapping', function(){
      if(confirm('제거하시겠습니까?')){
        $(this).parent().remove();
      }
  });

  /**
   * 저장
   * @returns {boolean}
   */
  function regPrcd(){
      let orgList = [];

      let primaryOrg = $('#orgNm');
      let primaryId = primaryOrg.data('org-id');
      if(primaryId == null || primaryId === ''){
          alert('주처리 부서 지정은 필수입니다.');
          $('#orgId').focus();
          return false;
      }

      let primaryOrgPr = {
          orgId : primaryId,
          primaryOrgYn : primaryOrg.data('primary-org')
      }
      orgList.push(primaryOrgPr);

      let subOrgArr = $('.subOrgNm');
      $.each(subOrgArr, (i, e) => {
          let subOrgPr = {
              orgId : $(e).data('org-id'),
              primaryOrgYn : $(e).data('primary-org')
          };
          orgList.push(subOrgPr);
      });

      let managementCd = '${param.managementCd}';
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
              managementCd,
              orgList
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
              success(res) {
                  resolve(res);
              },
              error: console.log
          })
      })
  }

  /**
   * 부처리부서 영역 추가
   */
  function addSubOrg(orgNm, orgId){
      let component = `
            <div class="org_mapping">
                <span>부처리부서</span>
                <input type="text" class="subOrgNm" data-org-id="\${orgId}" data-primary-org="N" value="\${orgNm}" disabled>
                <button class="deleteOrgMapping">삭제</button>
            </div>
      `;
      $('.sub_org_list').append(component);
  }

  /**
   * 부서 검색 callback
   * @param data
   */
  function orgSearchCallback(data){
      let primaryOrgTarget = $('#orgNm');
      let subOrgTarget = $('.subOrgNm');

      let orgId = data.orgId;
      let orgNm = data.orgNm;

      if(orgId === primaryOrgTarget.data('org-id')){
          alert('주처리 부서로 등록된 부서입니다.');
          openComnModal('vocOrgSearchModal', 950, 650);
          return false;
      }
      if(subOrgTarget.length !== 0){
          let chk = 0;
          $.each(subOrgTarget, (i, e) => {
             if($(e).data('org-id') === orgId){
                 alert('부처리 부서로 등록된 부서입니다.');
                 chk++;
             }
          });
          if(chk > 0) {
              openComnModal('vocOrgSearchModal', 950, 650);
              return false;
          }
      }

      if(orgTarget === primaryOrg){
          primaryOrgTarget.val(orgNm).data('org-id', orgId);
      } else if(orgTarget === subOrg){
          addSubOrg(orgNm, orgId);
      }
      orgTarget = null;
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