package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName issues
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("issues")
public class Issues implements Serializable {
    /**
     * 
     */
    private Long issueId;

    /**
     * 
     */
    private Long createrId;

    /**
     * 
     */
    private Long adminId;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Integer state;

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