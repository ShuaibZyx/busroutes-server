package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName bus
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("bus")
public class Bus implements Serializable {
    /**
     * 
     */
    private Long busId;

    /**
     * 
     */
    private String busName;

    /**
     * 
     */
    private String cityCode;

    /**
     * 
     */
    private Integer type;

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