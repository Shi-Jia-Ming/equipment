package com.medical.equipment.fileUpload;

import com.medical.equipment.exception.ErrorException;
import com.medical.equipment.service.FileUploadService;
import com.medical.equipment.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("file")
public class FileUploadController {


    @Resource
    private FileUploadService fileUploadService;

    /**
     * 上传图片或者视频
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadHeadImg")
    public R uploadHeadImg(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            String fileUrl = fileUploadService.uploadFile(file);
            return R.ok().put("文件地址",fileUrl);
        } catch (ErrorException e) {
            return R.error( "上传失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("上传文件失败");
    }
}
