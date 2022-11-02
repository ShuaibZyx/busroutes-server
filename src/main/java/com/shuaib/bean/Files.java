package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName files
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("files")
public class Files extends Model<Files> implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String fileId;

    /**
     * 
     */
    private String originalName;

    /**
     * 
     */
    private String fileType;

    /**
     * 
     */
    private Integer fileSize;

    /**
     * 
     */
    private String fileUrl;

    /**
     * 
     */
    private String folder;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;


}