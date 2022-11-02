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
 * @TableName stations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("station")
public class Stations extends Model<Stations> implements Serializable {
    /**
     * 
     */
    private Long stationId;

    /**
     * 
     */
    private String stationName;

    /**
     * 
     */
    private String cityCode;

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