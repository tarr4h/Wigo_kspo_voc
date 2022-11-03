<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>





<div class="title">
        <h1>첨부파일</h1>
        <a href="#" data-btn-type="closeModal" class="close">팝업 닫기</a>
    </div>
<div class="cont">
    <!-- //top -->
    <div class="mBox1">
    	<div class="gTitle1">
            <h3 class="mTitle1">첨부파일</h3>
            <div class="gRt">
             
		    <a type="button" data-grid-id="grdList" data-click="downloadFile" id="btnDownload" class="mBtn1 m lWhite">
		         다운로드
		    </a>
		    </div>
        </div>
        
        <div id="divGrid"  style="height: 395px" data-get-url="<c:url value='${urlPrefix}/file/getList${urlSuffix}'/>" 
                data-grid-id="grdList" data-pagenation="N" data-type="grid" data-tpl-url="<c:url value='/static/gridTemplate/file/filePopupView.xml'/>">
    	</div>
    	
    </div>
</div>


<script>
    var fileUploader = null;
    var currentFileInfo = null;
    function grdList_btnChange_buttonClicked(gridView, row, col,json){
        changeFile(json);
    }
    function changeFile(data) {
        currentFileInfo = data;
        fileUploader.addFile();
    }
    function addFile() {
        currentFileInfo = null;
        fileUploader.addFile();
    }
    function uploadFile() {

        var url = "<c:url value='${urlPrefix}/file/uploadFile'/>${urlSuffix}?fileCtgryCd=${fileCtgryCd}";
        var saveJson = grdList.getCheckedJson();
        if (saveJson.length == 0) {
            alert("체크된 데이터가 존재하지 않습니다.");
            return;
        }
        var cList = [];
        for (var i = 0; i < saveJson.length; i++) {
            if (saveJson[i].stat == "c") {
                cList.push(saveJson[i]);
                fileUploader.upload(saveJson[i].id, url, saveJson[i], onUploadComplete, onUploadProgress);
                continue;
            }

        }
        if (cList.length) {
        } else {
            alert("추가된 파일이 없습니다.");
        }
    }

    function downloadFile() {
        var saveJson = grdList.getCheckedJson();
        if (saveJson.length == 0) {
            alert("체크된 데이터가 존재하지 않습니다.");
            return;
        }
        var list = [];
        var cList = [];
        for (var i = 0; i < saveJson.length; i++) {
            if (saveJson[i].stat == "c") {
                cList.push(saveJson[i]);
                continue;
            }
            list.push(saveJson[i]);
        }
        if (list.length) {
            for(var i=0;i<list.length;i++){
                var file = list[i];
                onDownloadfile(file.fileCd,file.fileOdrg,file.fileSize);
            }
        } else {
            alert("다운로드할 파일이 존재하지 않습니다.");  
        }
        
    }
    function onDownloadfile(fileCd, fileOdrg, fileSize) {
        var url = "<c:url value='${urlPrefix}/file/downloadFile'/>${urlSuffix}";
        var fnDownProg = function(loaded, total, percentComplete) {
            if (loaded && !total)
                total = fileSize;
            if (total && !percentComplete)
                percentComplete = loaded / total;
            onDownloadProgress(fileCd, fileOdrg, loaded, total, percentComplete);
        };
        var fnDownComp = function(returnValue, jqXHR) {
            onDownloadComplete(fileCd, fileOdrg, fileSize, returnValue, jqXHR);
        };
        var list = grdList.getJsonRows();
        for (var i = 0; i < list.length; i++) {
            if (fileCd == list[i].fileCd && fileOdrg == list[i].fileOdrg) {
                var json = grdList.getJsonRow(i);
                Utilities.ajaxDownload(url, json, true, fnDownProg, fnDownComp);

                break;
            }
        }
    }
    function removeFile() {
        var saveJson = grdList.getCheckedJson();
        if (saveJson.length == 0) {
            alert("체크된 데이터가 존재하지 않습니다.");
            return;
        }
        if (saveJson.length) {
            if (!confirm("데이터를 삭제하면 복구할  수 없습니다. 계속하시겠습니까?"))
                return;
            var list = [];
            var cList = [];
            for (var i = 0; i < saveJson.length; i++) {
                if (saveJson[i].stat == "c") {
                    cList.push(saveJson[i]);
                    continue;
                }
                list.push(saveJson[i]);
            }
            if (list.length) {
                var url = "<c:url value='${urlPrefix}/file/removeFileList${urlSuffix}'/>";
                Utilities.blockUI();
                Utilities.getAjax(url, saveJson, true, function(data, jqXHR) {
                    if (Utilities.processResult(data, jqXHR, "파일 삭제에 실패했습니다.")) {
                        Utilities.unblockUI();
                        alert("파일 삭제에  성공했습니다.");
                        grdList.removeCheckRow();
                        removeFileInfo(cList);
                    }
                });
            } else {
                grdList.removeCheckRow();
                removeFileInfo(cList);
            }

        } else {
            alert("추가된 파일이 없습니다.");
        }
    }
    function onUploadProgress(id, loaded, total, percent) {
        if(percent<1)
            $("#uploadInfo_" + id).html( /*loaded +"/"+ total +*/parseInt(percent * 100, 10) + " %");
        else
            $("#uploadInfo_" + id).html( "파일 처리중 ");
    }
    function onUploadComplete(id, resultData, result) {
        if (result) {
            $("#uploadInfo_" + id).html("전송완료");
            var list = grdList.getJsonRows();
            for (var i = 0; i < list.length; i++) {
                var no = list[i].id;
                if (id == no) {
                    grdList.resetRowState(list[i].itemIndex);
                    break;
                }
            }
        } else
            $("#uploadInfo_" + id).html("전송실패");
    }
    function onDownloadProgress(fileCd, fileOdrg, loaded, total, percentComplete) {
        var hDiv = $("div[data-type=downloadInfo][data-file-cd=" + fileCd + "][data-file-seq=" + fileOdrg + "]");
        var html = "전송준비중";
        if (loaded)
            html = parseInt(percentComplete * 100, 10) + " %";
        hDiv.html(html);
    }
    function onDownloadComplete(fileCd, fileOdrg, fileSize, returnValue, jqXHR) {
        var hDiv = $("div[data-type=downloadInfo][data-file-cd=" + fileCd + "][data-file-seq=" + fileOdrg + "]");
        var html = '<a href="#;" onclick="onDownloadfile(\'' + fileCd + '\',\'' + fileOdrg + '\',' + fileSize
        + ')" id="btnDownload" class="mBtn1 m lWhite"><i class="fas fa-download"></i> 다운로드</a>';
        hDiv.html(html);
    }

    function search() {
        var url = "<c:url value='${urlPrefix}/file/getList${urlSuffix}'/>";
        var param = {
            recordCountPerPage : 1000000,
            fileCd : "<c:out value='${fileInfo.fileCd}'/>"
        };
        if (!param.fileCd) {
            alert("파일정보가 존재하지 않습니다.");
            return false;
        }
        grdList.loadUrl(url, param);
    }
    function removeFileInfo(list) {
        for (var i = 0; i < list.length; i++) {
            fileUploader.removeFile(list[i].id);
        }
    }

    function grdList_load() {
        search();
    }
    var _fileOdrg = 0;
    function addFormFile(e, data) {
        
        
        var list = grdList.getJsonRows();
        var fileOdrg = 0;
        if(currentFileInfo){
            fileOdrg = currentFileInfo.fileOdrg;
        }
        if(_fileOdrg==0){
            for (var i = 0; i < list.length; i++) {
                var no = list[i].fileOdrg;
                if (no >= fileOdrg)
                    _fileOdrg = no;
            }   
        }
        
        _fileOdrg++;
        var file = data.file;
        var fileExt = Utilities.getFileExt(file.name);
        var fileInfo = {
            fileCd : "<c:out value='${fileInfo.fileCd}'/>",
            fileOdrg : _fileOdrg,
            fileNm : file.name,
            fileSize : file.size,
            mimeTypeNm : file.type,
            fileSaveNm : null,
            id : data.id,
            fileExtNm : fileExt,
            stat : 'c'
        };
        if(!currentFileInfo){
            var rowIdx = grdList.addRow(fileInfo);
            grdList.checkItem(rowIdx,true); 
        }
        else {
            
            var rowKey = currentFileInfo.rowKey;
            
            grdList.setRow(rowKey, fileInfo);
            grdList.checkItem(rowKey,true);
        }
        
    }
    function onGridHtmlRender(gridView, row, col, data,colValue) {
        if (data.stat != 'c') {
            if (data.id) {
                return "전송완료";
            }
            
            
            //          var html = "download";
            var html = '<div data-file-cd="'+data.fileCd+'" data-file-seq="'+data.fileOdrg+'" data-type="downloadInfo"><a href="#;" onclick="onDownloadfile(\'' + data.fileCd + '\',\''
                    + data.fileOdrg + '\',' + data.fileSize + ')" id="btnDownload" class="mBtn1 m lWhite"><i class="fas fa-download"></i> 다운로드</a></div>';
            return html;
        } else {
            return '<div id="uploadInfo_'+ data.id +'">업로드 대기중</div>';
        }

    }
    function grdList_load(gridView,gridId){
         search();
    }
    
    function onClose(){
        var opn = Utilities.getOpener();
        if(opn && opn.onFileClose){
            try{
                var fileInfo = getFileInfo();
                opn.onFileClose( "<c:out value='${fileInfo.fileCd}'/>",grdList.getJsonRows(),fileInfo);
            }catch(e){
                console.error(e);
            }
        }
        Utilities.closeModal();
    }
    function getFileInfo(){
        var fileInfo = "첨부 없음";
        var list = grdList.getJsonRows();
        if(list && list.length){
            fileInfo = list[0].fileNm ;
            if(list.length>1)
                fileInfo += "(총 " + list.length +"건)";
        }
        return fileInfo;
    }
    $(document).ready(function() {
        fileUploader = Utilities.getFileUploader({
            addCallback : addFormFile
        });
        //maxFileCount, maxFileSize, maxTotalSize, multifiles,acceptFiles,url,addCallback
    });
</script>
