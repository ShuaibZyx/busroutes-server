package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;

/**
 * 线路节点信息实体
 * @TableName route_nodes
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("route_nodes")
public class RouteNodes extends Model<RouteNodes> implements Serializable {
    //线路节点信息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long routeNodeId;

    //节点所属线路编号(外键-->(routes:route_id))
    private Long routeId;

    //节点当前站点信息编号
    private Long stationId;

    //节点当前站点信息对象
    @TableField(exist = false)
    private Stations currentStation;

    //节点下一站的信息编号
    private Long nextStationId;

    //节点下一站的信息对象
    @TableField(exist = false)
    private Stations nextStation;

    //两个节点之间的距离(单位:米)
    private Double distance;

    //节点位于线路的相对次序
    @Column(columnDefinition = "smallint not null default'0' UNIQUE auto_increment")
    private Integer sequence;

    //节点创建时间
    private Date createTime;

    //节点更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}