package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 公交线路信息实体
 * @TableName routes
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("routes")
public class Routes extends Model<Routes> implements Serializable {
    //线路编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long routeId;

    //公交信息编号(外键-->(buses:bus_id))
    private Long busId;

    //公交信息实体(数据库中不存在)
    @TableField(exist = false)
    private Buses bus;

    //线路结束节点次序
    private Integer startSequence;

    //线路结束节点次序
    private Integer endSequence;

    //线路包含的节点集合
    @TableField(exist = false)
    private List<RouteNodes> routeNodeList;

    //线路价格
    private Double cost;

    //线路运行时间范围
    private String timeRange;

    //线路发车间隔
    private Integer interval;

    //是否为夜间行车线路
    private Boolean isNight;

    //是否为环形线路
    private Boolean isCircle;

    //线路创建时间
    private Date createTime;

    ///线路更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}