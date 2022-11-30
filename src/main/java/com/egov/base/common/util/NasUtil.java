package com.egov.base.common.util;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.wigo.crm.common.util - AwsS3Util.java
 * </pre>
 *
 * @ClassName : AwsS3Util
 * @Description : TODO
 * @author : 김성태
 * @date : 2021. 3. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Component
public class NasUtil {
//    private final Logger logger = LoggerFactory.getLogger( this.getClass());
//
//	@Value("${cloud.aws.bucket-name}")
//	private String bucketName;
//	
//	@Value("${cloud.aws.mc-bucket-name}")
//	private String mcBucketName;
//	
//
//	@Value("${cloud.aws.region.static}")
//	private String region;
//
//	@Autowired
//	private AmazonS3Client amazonS3Client;
//
//	public String getHttpUrl(String fileName) {
//		return "https://" + getBuketName(fileName) + ".s3." + region + ".amazonaws.com/" + fileName;
////		return amazonS3Client.getUrl(bucketName, fileName).toString();
//	}
//
//	private String getBuketName(String fileName) {
//		if(fileName.toUpperCase().endsWith(".MP4"))
//			return mcBucketName;
//		return bucketName;
//	}
//
//	public String getPreSignedUrl(String fileName) {
//		Date expiration = new Date();
//		long expTimeMillis = expiration.getTime();
//        expTimeMillis += 1000 * 60 * 60 * 24;
//        expiration.setTime(expTimeMillis);
//        GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                 new GeneratePresignedUrlRequest(getBuketName(fileName), fileName)
//                         .withMethod(HttpMethod.GET)
//                         .withExpiration(expiration);
//        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
//        return url.toString();
//	}
//	public PutObjectResult upload(MultipartFile file,String saveFileName, String fileName) throws Exception  {
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					ObjectMetadata omd = new ObjectMetadata();
//					omd.setContentType("application/octet-stream");
//					omd.setContentLength(file.getSize());
//					omd.setHeader("Content-Disposition", 	"attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
//					
////					PutObjectResult res = 
//							amazonS3Client
//							.putObject(new PutObjectRequest(getBuketName(saveFileName), saveFileName, file.getInputStream(), omd)
////							.withCannedAcl(CannedAccessControlList.PublicRead)		
//									);
////					return res;
//				} catch (AmazonServiceException e) {
//					logger.error(e.getErrorMessage(), e);
////					throw e;
//				} catch (SdkClientException e) {
//					logger.error(e.getMessage(), e);
////					throw e;
//				} catch(Exception e) {
//					logger.error(e.getMessage(), e);
////					throw e;
//				}
//			}
//		}).run();
//		return null;
//	}
//
//	public void delete(String fileName) throws Exception {
//		try {
//			logger.info("파일삭제 ["+fileName+"]");
//			DeleteObjectRequest delReq = new DeleteObjectRequest(getBuketName(fileName), fileName);
//			amazonS3Client.deleteObject(delReq);
//		} catch (AmazonServiceException e) {
//			logger.error(e.getErrorMessage(), e);
//			throw e;
//		} catch (SdkClientException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//	}
//
//	public void copy(String orgPath, String copyPath) throws Exception {
//		try {
//			logger.info("파일복사 ["+orgPath+"] ===> ["+copyPath+"]");
//			// Copy 객체 생성
//			CopyObjectRequest copyObjRequest = new CopyObjectRequest(getBuketName(orgPath), orgPath, getBuketName(copyPath), copyPath);
//			// Copy
//			this.amazonS3Client.copyObject(copyObjRequest);
//
//		} catch (AmazonServiceException e) {
//			logger.error(e.getErrorMessage(), e);
//			throw e;
//		} catch (SdkClientException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//	}
//
//	public void rename(String orgPath, String copyPath) throws Exception {
//		try {
//			logger.info("파일변경 ["+orgPath+"] ===> ["+copyPath+"]");
//			copy(orgPath, copyPath);
//			delete(orgPath);
//		} catch (AmazonServiceException e) {
//			logger.error(e.getErrorMessage(), e);
//			throw e;
//		} catch (SdkClientException e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		} catch(Exception e) {
//			logger.error(e.getMessage(), e);
//			throw e;
//		}
//	}
//	public long getFileSize(String saveName) {
//		S3Object obj =  null;
//		try {
//			obj = amazonS3Client.getObject(getBuketName(saveName), saveName);
//			if(obj == null)
//				return 0;
//			long nLength = (long) obj.getObjectMetadata().getContentLength();
//			return nLength;
//		} catch (Exception ex) {
//			return 0L;
//		}finally {
//			if(obj!=null) {
//				try {
//					obj.close();
//				} catch (Exception e) {
//
//				}
//			}	
//		}
//	}
//	public void download(String fileName,String strDisplayName,HttpServletResponse response) throws Exception {
//		
//		S3ObjectInputStream s3is = null;
//		S3Object obj = amazonS3Client.getObject(getBuketName(fileName), fileName);
//		String contentsType = obj.getObjectMetadata().getContentType();
//		int nLength = (int) obj.getObjectMetadata().getContentLength();
//		if (BaseUtilities.isEmpty(contentsType))
//			contentsType = "application/octet-stream";
//		try {
//			response.setContentType(contentsType);
//			response.setHeader("Content-Disposition",
//					"attachment;filename=\"" + URLEncoder.encode(strDisplayName, "UTF-8") + "\";");
//			response.setContentLength(nLength);
//		    s3is = obj.getObjectContent();
//			BaseUtilities.DownloadStream(response.getOutputStream(),s3is);
//			
//		} catch (Exception ex) {
//			try {
//				response.sendError(404);
//			} catch (Exception e) {
//
//			}
//			throw ex;
//		}finally {
//			if(s3is!=null) {
//				try {
//					s3is.close();
//				} catch (Exception e) {
//
//				}
//			}	
//			if(obj!=null) {
//				try {
//					obj.close();
//				} catch (Exception e) {
//
//				}
//			}	
//		}
//		
//	}
}
