package com.kspo.voc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.CrmFileBaseDao;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.model.CrmFileVo;

import javax.xml.ws.http.HTTPException;
import java.io.File;
import java.util.List;

@Service
public class CrmFileService extends AbstractCrmService {
	@Autowired
	CrmFileBaseDao dao;

	@Value("${spring.nas.path}")
	private String nasPath;


	@Value("${spring.nas.url}")
	private String nasUrl;



	@Override
	public ICrmDao getDao() {
		return dao;
	}

	@Override
	public int delete(Object param) throws Exception {
		CrmFileVo fileInfo = get(param);
		if (fileInfo == null)
			return 0;
		String saveFileName = getSaveFileName( fileInfo.getFileSaveNm());
		Utilities.deleteFile(new File(saveFileName));
		int ret = super.delete(param);
		String fileId = fileInfo.getFileId();
		EzMap so = new EzMap();
		so.setString("fileId", fileId);
		int cnt = getListCount(so);
		if(cnt == 0) {
			String dir = Utilities.getFilePath( saveFileName);
			Utilities.deleteDirectory(dir);
		}
		return ret;
	}
	private String getSaveFileName(String saveName) {
		return nasPath + saveName;
	}
	private String getFileUrlName(String fileName) {
		return nasUrl + fileName;
	}
	private void saveUploadFile(MultipartFile uploadfile, String fileName) throws Exception {
		Utilities.createDirectory(Utilities.getFilePath(fileName));
		uploadfile.transferTo(new File(fileName));
	}

	public CrmFileVo uploadFile(MultipartFile uploadfile, CrmFileVo fileInfo) throws Exception {
		if (uploadfile == null)
			throw new Exception();

		if (Utilities.isEmpty(fileInfo.getFileNm())) {
			String rFileName = uploadfile.getOriginalFilename();
			fileInfo.setFileNm(rFileName);
			fileInfo.setFileExtNm(".txt");
			fileInfo.setFileSize(uploadfile.getSize());
			fileInfo.setMimeTypeNm(uploadfile.getContentType());
			;
		}

		String ctgryCd = fileInfo.getFileCtgryCd();
		String subUrl = fileInfo.getFileId() + "/";
		String fileName = fileInfo.getFileNm();

		// 완료후
		fileName = Utilities.getUniqID(20) + "." + Utilities.getFileExtension(fileName);

		if (Utilities.isEmpty(ctgryCd))
			ctgryCd = "attachment";
		if (ctgryCd.startsWith("/"))
			ctgryCd = ctgryCd.substring(1);
		if (ctgryCd.endsWith("/"))
			ctgryCd = ctgryCd.substring(0, ctgryCd.length() - 1);

		String fileId = fileInfo.getFileId();

		subUrl = ctgryCd + "/" + fileId + "/";
		String subFileName = subUrl + fileName;
		fileInfo.setFileUrl( getFileUrlName(subFileName));
		fileInfo.setFileSaveNm( subFileName);
		String saveFileName =getSaveFileName( subFileName) ;
		saveUploadFile(uploadfile, saveFileName);

		Integer fileSeq = fileInfo.getFileOdrg();
		if (fileSeq !=null && fileSeq > 0) {
			delete(fileInfo);
		}
		insert(fileInfo);
		return fileInfo;
	}

	public Object downloadFile(CrmFileVo param) throws Exception {
		CrmFileVo fileInfo = get(param);
		if (fileInfo == null)
			throw new HTTPException(404);
		String saveName =getSaveFileName(fileInfo.getFileSaveNm());
		String fileNm = fileInfo.getFileNm();
		Utilities.DownloadFile(saveName, fileNm);
		return true;

	}

	public void deleteFileId(String fileId) throws Exception {
		if (Utilities.isEmpty(fileId))
			return;
		EzMap fileSo = new EzMap();
		fileSo.setString("fileId", fileId);
		List<CrmFileVo> fileList = dao.selectList(fileSo);
		deleteList(fileList);

	}

	public int getMaxFileSeq(String fileId) throws Exception {
		EzMap param = new EzMap();
		param.setString("fileId", fileId);
		return dao.selectMaxFileSeq(param);
	}

}
