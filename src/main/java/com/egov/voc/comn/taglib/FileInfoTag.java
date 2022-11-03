package com.egov.voc.comn.taglib;



import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmFileBaseVo;
import com.egov.voc.sys.service.CrmFileService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * 
 * <pre>
 * com.wigo.crm.common.taglib - FileInfoTag.java
 * </pre>
 *
 * @ClassName : FileInfoTag
 * @Description : file Tag
 * @author : MKH
 * @date : 2021. 1. 18.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class FileInfoTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810949506019349503L;

	private String name;
	private String fileCd;
	private int fileSeq = 0;
	private String acceptFileTypes="";
	private String acceptExts="";
	private boolean viewMode = false;
	private String fileCategory = "attachment";
	CrmFileService service = Utilities.getBean(CrmFileService.class);

	@Override
	public int doStartTag() throws JspException {
		StringBuffer html = new StringBuffer();
		String idNm = "";
		if(Utilities.isNotEmpty(id))
			idNm += " id='"+id+"'";
		if(Utilities.isNotEmpty(name))
			idNm += " name='"+name+"'";
		String dataInfo = "data-file-cd='"+fileCd+"' data-file-seq='"+fileSeq+"' data-file-category='"+fileCategory+"' data-accept-ext='"+acceptExts+"' data-accept-types='"+acceptFileTypes+"' "+idNm;
		String info = "<span "+dataInfo+" data-file-info='info' >";
		String button = "&nbsp;<button "+dataInfo+" class=\"btnInner gray btnSubmit\" type=\"button\" data-click=\"onFileInfo\" data-file-info='button'>첨부</button>";
		String cancel = "&nbsp;<button "+dataInfo+" class=\"btnInner gray btnSubmit\" style='display:none'  type=\"button\" data-click=\"onCancelFileInfo\"  data-file-info='cancel'>취소</button>";
		String remove = "&nbsp;<button "+dataInfo+" class=\"btnInner gray btnSubmit\" style='display:none'  type=\"button\" data-click=\"onRemoveFileInfo\"  data-file-info='remove'>삭제</button>";
		String download = "&nbsp;<button "+dataInfo+" style='display:none' class=\"btnInner gray btnSubmit\" data-file-url='' type=\"button\" data-click=\"onDownloadFileInfo\"  data-file-info='download'>받기</button>";

		EzMap param = new EzMap();
		param.setString("fileCd", fileCd);
		if (fileSeq > 0) {
			param.setInt("fileSeq", fileSeq);
		}
		List<CrmFileBaseVo> fileList = null;
		try {
			fileList = service.getList(param);
			if (Utilities.isEmpty(fileList))
				info += "첨부없음";
			else {
				remove = "&nbsp;<button "+dataInfo+" class=\"btnInner gray btnSubmit\" type=\"button\" data-click=\"onRemoveFileInfo\"  data-file-info='remove'>삭제</button>";
				download = "&nbsp;<button "+dataInfo+" class=\"btnInner gray btnSubmit\" data-file-url='"+fileList.get(0).getFileUrl()+"' data-file-nm='"+fileList.get(0).getFileNm()+"' type=\"button\" data-click=\"onDownloadFileInfo\"  data-file-info='download'>받기</button>";
				info += fileList.get(0).getFileNm();
				
				if (fileList.size() > 1)
					info += "(총 " + fileList.size() + " 파일)";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return SKIP_BODY;
		}
		info = info + "</span>";
		html.append(info);
		if (!viewMode)
		{
			html.append(button);
			html.append(cancel);
			html.append(download);
			html.append(remove);
			
			
		}
		else {
			if (Utilities.isNotEmpty(fileList))
				html.append(download);
		}
		
		try {
			pageContext.getOut().print(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileCd() {
		return fileCd;
	}

	public void setFileCd(String fileCd) {
		this.fileCd = fileCd;
	}

	public int getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getAcceptFileTypes() {
		return acceptFileTypes;
	}

	public void setAcceptFileTypes(String acceptFileTypes) {
		this.acceptFileTypes = acceptFileTypes;
	}

	public String getAcceptExts() {
		return acceptExts;
	}

	public void setAcceptExts(String acceptExts) {
		this.acceptExts = acceptExts;
	}

	public boolean isViewMode() {
		return viewMode;
	}

	public void setViewMode(boolean viewMode) {
		this.viewMode = viewMode;
	}

	public String getFileCategory() {
		return fileCategory;
	}

	public void setFileCategory(String fileCategory) {
		this.fileCategory = fileCategory;
	}

}
