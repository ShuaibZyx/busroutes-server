package com.shuaib.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.shuaib.bean.Files;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtils {

    //当前系统所用分隔符
    private static final String systemSeparator = System.getProperty("file.separator");
    //当前项目目录
    private static final String absolutePath = System.getProperty("user.dir") + systemSeparator + "src" + systemSeparator + "main" + systemSeparator + "resources" + systemSeparator + "static" + systemSeparator;

    /**
     * @param file 上传的文件
     * @param dir  文件将要存入的目录
     * @return Files文件实体
     */
    @NotNull
    public static Files uploadFile(MultipartFile file, String dir) {
        try {
            if (file.isEmpty()) throw new RuntimeException("上传文件内容不能为空");
            if (Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") <= 0)
                throw new RuntimeException("不支持该文件类型");
            //获取文件上传时的名字
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀名
            String fileSuffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //获取当前日期字符串(年月日作为文件夹)
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //拼接文件绝对路径
            String filePath = absolutePath + dir + systemSeparator + currentDate + systemSeparator;
            //尝试创建存放上传文件的所有父级文件夹
            File pathDirs = new File(filePath);
            if (!(pathDirs.exists())) {
                if (!pathDirs.mkdirs()) throw new RuntimeException("创建父级目录失败");
            }
            String newFileName = getNewFileName();
            file.transferTo(new File(filePath + newFileName + fileSuffixName));
            //创建一个对象用于返回
            Files newFile = new Files();
            newFile.setFileId(newFileName);
            newFile.setOriginalName(file.getOriginalFilename());
            newFile.setFileType(fileSuffixName);
            newFile.setFileSize((int) file.getSize());
            newFile.setFileUrl((getWebProUrl() + systemSeparator + dir + systemSeparator + currentDate + systemSeparator + newFileName + fileSuffixName).replace("\\", "/"));
            newFile.setFolder(dir);
            newFile.setCreateTime(new Date());
            return newFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param files 上传的文件列表
     * @param dir 上传的文件列表存入的文件夹
     * @return 上传的文件实体类列表
     */
    @NotNull
    public static List<Files> uploadFileList(MultipartFile[] files, String dir) {
        if (files.length == 0) throw new RuntimeException("文件列表不可为空");
        List<Files> filesList = new ArrayList<>();
        for (MultipartFile file : files) {
            Files newFile = uploadFile(file, dir);
            filesList.add(newFile);
        }
        return filesList;
    }

    /**
     * 删除文件
     * @param relativePath 文件static之后的相对路径
     * @return 是否删除成功
     */
    @NotNull
    public static Boolean deleteFile(String relativePath) {
        return new File(absolutePath + relativePath).delete();
    }



    /**
     * 获取新的文件名
     *
     * @return UUID新的文件名
     */
    @NotNull
    public static String getNewFileName() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid.toLowerCase();
    }

    /**
     * 获取当前WebUrl路径
     *
     * @return 当前WebUrl
     */
    @NotNull
    public static String getWebProUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}

