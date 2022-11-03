$(document).ready(function() {
    var treeList = $("[data-type=attachFiles]");
    for (var i = 0; i < treeList.length; i++) {
        var el = treeList[i];
        initEzAttachfile(el);
    }
});
function initEzAttachfile(el){
	const $el = $(el);
	let $data = $el.data();
	let id = $el.attr("id");
	if(!id)
	{
		id = Utilities.uuid();
		 $el.attr("id",id);
	}
	
	const fileCd = $data.fileCd;
	const fileOdrg = $data.fileOdrg;
	if(!fileCd){
		return createEzFileUploader(el);
	}
	let param = {
		fileCd : fileCd,
		recordCountPerPage : 100000
	}
	if(fileOdrg)
		param.fileOdrg = fileOdrg;
	var url = "/util/file/getList";
	Utilities.getAjax(url,param,true,function(data,jqXHR){
        if(Utilities.processResult(data,jqXHR,"첨부파일 설정에 실패했습니다."))
        {
        	createEzFileUploader(el,data.data.contents);
        }
    });
	
}

function createEzFileUploader(el,fileList){
	const urlPrefix = "/util/file/";
	const $el = $(el);
	let $data = $el.data();
	const multiple =  !$data.fileOdrg;
	const editable = $data.mode == "edit";
	let ezUploader = {
		el : el,
		fileCd : $data.fileCd,
		fileOdrg : $data.fileOdrg ? $data.fileOdrg : "",
		multiple : multiple,
		fileList : fileList,
		editable : editable,
		getMaxfileOdrg : function(){
			let fileOdrg = 0;
			$(el).find('[data-file-type=file][data-file-odrg]').each(function(){
				const fSeq = parseInt($(this).attr("data-file-odrg"));
				if(fSeq && fSeq > fileOdrg)
					flieSeq = fSeq; 
			});
			return fileOdrg + 1;
		},
//		readonly : !editable,
		onAddedFile : function(e,data,uploader){
			$this = el.ezFileUploader;
			let fileInfo = {
				fileCd  : $this.fileCd,
				fileUrl : "",
				fileExtNm : Utilities.getFileExt(data.file.name),
				mimeTypeNm : data.file.type,
				fileOdrg : $this.fileOdrg ? $this.fileOdrg : "",
//				fileOdrg : $this.getMaxfileOdrg(),
				fileSize : data.file.size,
				fileNm : data.file.name,
				id : data.id
			};
			$this.addFile(fileInfo,uploader,data);
		},
		onProgressFile : function(id, loaded, total, percentComplete){
			$this = el.ezFileUploader;
			//data-file-upload-id="'+data.id+'" data-file-type="uploadInfo"
			let $info = $(el).find("[data-file-type=uploadInfo][data-file-upload-id="+id+"]");
//			let html = "("+ Utilities.numberWithCommas(loaded) + " / " + Utilities.numberWithCommas(total) + ")";
			let html = "("+ Utilities.getReadableFileSizeString(loaded) + " / " + Utilities.getReadableFileSizeString(total) + ")";
			if(loaded == total){
				html = "(파일처리중)";
			}
			$info.html(html);
		},
		onCompleteFile : function(id, resultData, result){
			$this = el.ezFileUploader;
			const $info = $(el).find("[data-file-type=uploadInfo][data-file-upload-id="+id+"]");
			if(!resultData || !result){
				let html = "(전송실패)";
				$info.html(html);
				return;
			}else {
				$this.drawFileInfo($info,resultData);
			}
		},
		fileUploader : null,
		draw : function(){
			$this = this;
			$div = $(this.el);
			$div.html("");
			if(!this.fileCd)
				return;
			let dataStr = 'data-file-cd="'+this.fileCd+'" data-file-odrg="'+this.fileOdrg+'" ';
			if(editable)
			{
				
				let $btn = $('<a href="#;" data-file-type="addBtn" '+dataStr+' class="mBtn1 m lWhite">파일추가</a>') ;
				const $this = this;
				$btn.click(function(){
					if($this.fileOdrg &&  $($this.el).find('[data-file-type=file][data-file-cd='+$this.fileCd+'][data-file-odrg='+$this.fileOdrg+']').length > 0 ){
						if(!confirm("기존파일을 삭제하고 재업로드 하시겠습니까?"))
							return;
					}
					$this.fileUploader.addFile();
				});	
				$div.append($btn);
			}
			for(let i=0;this.fileList != null && i<this.fileList.length;i++){
				const fileInfo = this.fileList[i];
				this.addFile(fileInfo);
				
			}
			
		},
		drawFileInfo: function(fDiv,fileInfo){
			$this = this;
			fDiv.html("");
			let dataStr = 'data-file-cd="'+fileInfo.fileCd+'" data-file-odrg="'+fileInfo.fileOdrg+'" data-fileSize="'+fileInfo.fileSize+'" data-file-save-nm="'+fileInfo.fileSaveNm+'"';
			let fA = $('<a href="#;" style="text-decoration:underline" '+dataStr+'>'+ fileInfo.fileNm +'('+Utilities.getReadableFileSizeString(fileInfo.fileSize)+')</a>');
			fA.click(function(){
				$fileInfo = $(this).data();
				var url = urlPrefix + 'download?fileCd='+$fileInfo.fileCd+'&fileOdrg=' + $fileInfo.fileOdrg;

				Utilities.downloadFileUrl(url);
			});
			fDiv.append(fA);
			if(this.editable){
//				fDel = $('<a href="#;" '+dataStr+'><i class="fas fa-trash-alt"></i></a>');
//				fDel = $('<button class="delete" '+dataStr+'><img src=""삭제</button>');
				fDel = $('<a href="#;" '+dataStr+'><img src="/static/crm/images/ico_delete2.png	" /></a>');
			
				fDel.click(function(){
					
					$this.removeFile($(this).data());
				});
				fDiv.append(fDel);
				
			}
		},
		addFile : function(fileInfo,uploader,data){
			$div = $(this.el);
			let dataStr = 'data-file-cd="'+fileInfo.fileCd+'" data-file-odrg="'+fileInfo.fileOdrg+'" ';
			let fDiv = $div.find('[data-file-type="file"][data-file-cd='+fileInfo.fileCd+'][data-file-odrg='+(fileInfo.fileOdrg ?fileInfo.fileOdrg : 0 )+']');
			
			if(fDiv.length==0){
				if(data){
					fDiv = $('<div '+dataStr+' data-file-type="file" data-file-upload-id="'+data.id+'">'+fileInfo.fileNm+'<span data-file-upload-id="'+data.id+'" data-file-type="uploadInfo"> 0 /'+data.file.size +'</span></div>');
				}
				else {
					fDiv = $('<div '+dataStr+' data-file-type="file"></div>');
				}
				$div.append(fDiv);
			}
			if(data){
					fDiv.attr("data-file-upload-id",data.id);
					fDiv.data("data-file-upload-id",data.id);
				}
			if(uploader && data){
				var url = urlPrefix + "uploadFile";
				uploader.upload(data.id, url, fileInfo, this.onCompleteFile, this.onProgressFile)
			} else {
				this.drawFileInfo(fDiv, fileInfo);	
			}
			
		},
		removeFile : function(fileInfo){
			if(!confirm("파일을 삭제 하시겠습니까?"))
				return;
			let url = urlPrefix + "removeFileInfo";
			$this = this;
			Utilities.getAjax(url,fileInfo,true,function(data,jqXHR){
		        if(Utilities.processResult(data,jqXHR,"첨부파일 삭제에 실패했습니다."))
		        {
					alert("첨부파일 삭제에 성공했습니다.");
					$($this.el).find('[data-file-type=file][data-file-cd='+fileInfo.fileCd+'][data-file-odrg='+fileInfo.fileOdrg+']').remove();
					
		        }
		    });
		},
		reset : function(fileCd,fileOdrg){
			$this = this;
			this.fileCd = fileCd;
			this.fileOdrg = fileOdrg ? fileOdrg : "";
			const param = {
				fileCd : this.fileCd,
				fileOdrg : this.fileOdrg
			};
			if(!this.fileCd)
				return draw();
			var url = urlPrefix +"getList";
			Utilities.getAjax(url,param,true,function(data,jqXHR){
		        if(Utilities.processResult(data,jqXHR,"첨부파일 설정에 실패했습니다."))
		        {
					$this.fileList = data.data.contents;
					$this.draw();
//		        	createEzFileUploader(el,data.data.contents);
		        }
		    });
			
		},
		refresh : function(){
			draw();
		}
		
	};
	const uploadOption = {
		addCallback : ezUploader.onAddedFile,
		singleFile : !multiple ,
		acceptTypes : $data.acceptTypes ,
		acceptExt : $data.acceptExt
	};
	ezUploader.fileUploader = Utilities.getFileUploader(uploadOption);
	el.ezFileUploader = ezUploader;
	
	ezUploader.draw();
	return ezUploader;
}

