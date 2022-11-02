package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件实体
 * @TableName files
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("files")
public class Files extends Model<Files> implements Serializable {
    //文件信息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_UUID)
    private String fileId;

    //文件原名
    private String originalName;

    //文件类型(后缀)
    private String fileType;

    //文件大小(单位:字节)
    private Integer fileSize;

    //文件访问的webUrl
    private String fileUrl;

    //文件所属文件夹
    private String folder;

    //文件信息创建时间
    private Date createTime;

    //文件信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}