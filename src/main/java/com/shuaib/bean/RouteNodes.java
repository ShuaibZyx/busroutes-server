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
 * @TableName route_nodes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("route_nodes")
public class RouteNodes extends Model<RouteNodes> implements Serializable {
    /**
     * 
     */
    private Long routeNodeId;

    /**
     * 
     */
    private Long routeId;

    /**
     * 
     */
    private Long stationId;

    /**
     * 
     */
    private Long nextStationId;

    /**
     * 
     */
    private Double distance;

    /**
     * 
     */
    private Integer sequence;

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