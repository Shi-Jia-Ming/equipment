package com.medical.equipment.service.impl;

import com.medical.equipment.constant.FileConstant;
import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.service.FileUploadService;
import com.medical.equipment.utils.VerifyParamsUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import static com.medical.equipment.constant.FileConstant.UPLOAD_PATH;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public String uploadFile(MultipartFile file) {

        try {
            return saveFileServer(file, FileConstant.IMG, "equipment/file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "文件上传失败！";
    }


    /**
     * 通用方法
     * <p>
     * 保存文件至服务器
     *
     * @param file     文件
     * @param fileType 文件类型
     * @param source   保存的文件夹名
     * @return 相对路径
     * @throws Exception
     */
    private String saveFileServer(MultipartFile file, Integer fileType, String source) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        switch (fileType) {
            case FileConstant.IMG:
                if (!VerifyParamsUtils.isImg(suffix)) {
                    throw new ErrorException("文件格式不支持");
                }
                break;
            case FileConstant.TEXT:
                if (!VerifyParamsUtils.isText(suffix)) {
                    throw new ErrorException("文件格式不支持");
                }
                break;
            case FileConstant.VIDEO:
                if (!VerifyParamsUtils.isVideo(suffix)) {
                    throw new ErrorException("文件格式不支持");
                }
                break;
        }
        String fileUrl = VerifyParamsUtils.createFileUrl2(source);
        String filePath = FileConstant.FORWARD_PATH;
        UUID uuid = UUID.randomUUID();
        String fileGuid = uuid.toString();
        //上传文件至服务器
        uploadFile(file.getBytes(), UPLOAD_PATH + fileUrl, fileGuid + "." + suffix);
        //返回文件相对路径至前端
        return filePath + fileUrl + "/" + fileGuid + "." + suffix;
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
        out.write(file);
        out.flush();
        out.close();

    }

}
