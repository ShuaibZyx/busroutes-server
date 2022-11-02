package com.shuaib.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Files;
import com.shuaib.common.UploadFileUtils;
import com.shuaib.mapper.FilesMapper;
import com.shuaib.service.FilesService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {
    @Autowired
    private FilesMapper filesMapper;

    /**
     * 上传单个文件
     * @param file 上传的文件
     * @param dir 上传的文件存入的目录
     * @return 上传文件信息
     */
    @Transactional
    @Override
    public Files uploadFile(MultipartFile file, String dir) {
        Files fileInfo = UploadFileUtils.uploadFile(file, dir);
        filesMapper.insert(fileInfo);
        return fileInfo;
    }

    /**
     * 上传多个文件
     * @param files 上传的文件列表
     * @param dir 上传的文件列表存入的目录
     * @return 上传文件列表息
     */
    @Transactional
    @Override
    public List<Files> uploadFileList(MultipartFile[] files, String dir) {
        List<Files> filesList = UploadFileUtils.uploadFileList(files, dir);
        for (Files fileInfo : filesList) {
            filesMapper.insert(fileInfo);
        }
        return filesList;
    }

    /**
     * 删除单个文件
     * @param fileId 文件编号
     * @param dir 文件所在文件夹
     * @return 是否删除成功
     */
    @Transactional
    @Override
    public Boolean deleteFile(String fileId, String dir) {
        //创建查询对象,填入查询条件
        QueryWrapper<Files> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.select("fileUrl").eq("fileId", fileId);
        //获取查询出来的fileUrl,截取后半截字符串
        Files file = filesMapper.selectOne(fileQueryWrapper);
        String fileUrl = file.getFileUrl();
        //截取后半截字符串
        String fileRelativePath = fileUrl.substring(fileUrl.indexOf(dir));
        //删除文件
        Boolean realFileDeleteResult = UploadFileUtils.deleteFile(fileRelativePath);
        //删除数据库字段
        Boolean recordFileDeleteResult = filesMapper.deleteById(fileId) == 1;
        //返回结果
        return realFileDeleteResult && recordFileDeleteResult;
    }

    /**
     * 删除多个文件
     * @param fileIds 文件编号列表
     * @param dir 文件所在文件夹
     * @return 是否全部删除
     */
    @Transactional
    @Override
    public Boolean deleteFiles(@NotNull List<String> fileIds, String dir) {
        List<Boolean> deleteFileResult = new ArrayList<>();
        for (String fileId : fileIds) {
            deleteFileResult.add(deleteFile(fileId, dir));
        }
        for (Boolean result : deleteFileResult) {
            if (!result) throw new RuntimeException("文件删除出错");
        }
        return true;
    }
}
