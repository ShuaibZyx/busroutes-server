package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName routes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("routes")
public class Routes extends Model<Routes> implements Serializable {
    /**
     * 
     */
    private Long routeId;

    /**
     * 
     */
    private Long busId;

    /**
     * 
     */
    private Integer startSequence;

    /**
     * 0
     */
    private Integer endSequence;

    /**
     * 
     */
    private Double cost;

    /**
     * 
     */
    private String timeRange;

    /**
     * 
     */
    private Integer interval;

    /**
     * 
     */
    private Integer isNight;

    /**
     * 
     */
    private Integer isCircle;

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