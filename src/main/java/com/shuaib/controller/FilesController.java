package com.shuaib.controller;

import com.alibaba.fastjson.JSONObject;
import com.shuaib.common.Result;
import com.shuaib.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FilesController {
    @Autowired
    private FilesService filesService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file, String dir) {
        return Result.success(filesService.uploadFile(file, dir));
    }

    @PostMapping("/delete")
    public Result deleteFile(String fileId, String dir) {
        return filesService.deleteFile(fileId, dir) ? Result.success("文件删除成功") : Result.error("文件删除失败");
    }

    @PostMapping("/uploads")
    public Result uploadFiles(MultipartFile[] files, String dir) {
        return Result.success(filesService.uploadFileList(files, dir));
    }

    @PostMapping("/deletes")
    public Result deleteFiles(@RequestBody JSONObject obj) {
        List<String> fileIds = obj.getJSONArray("fileIds").toJavaList(String.class);
        String dir = obj.getString("dir");
        return filesService.deleteFiles(fileIds, dir) ? Result.success("批量删除文件成功") : Result.error("批量删除文件失败");
    }
}
