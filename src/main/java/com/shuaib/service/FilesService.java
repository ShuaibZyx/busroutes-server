package com.shuaib.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaib.bean.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FilesService extends IService<Files> {
    /**
     * 上传单个文件接口
     * @param file 上传的文件
     * @param dir 上传的文件存入的目录
     * @return 上传的文件信息
     */
    Files uploadFile(MultipartFile file, String dir);

    /**
     * 上传多个文件接口
     * @param files 上传的文件列表
     * @param dir 上传的文件列表存入的目录
     * @return 上传的文件列表信息
     */
    List<Files> uploadFileList(MultipartFile[] files, String dir);

    /**
     * 删除单个文件
     * @param fileId 文件编号
     * @param dir 文件所在文件夹
     * @return 是否删除成功
     */
    Boolean deleteFile(String fileId, String dir);

    /**
     * 删除多个文件
     * @param fileIds 文件编号列表
     * @param dir 文件所在文件夹
     * @return 是否全部删除成功
     */
    Boolean deleteFiles(List<String> fileIds, String dir);


}
