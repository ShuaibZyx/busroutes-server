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
 * 站点信息实体
 * @TableName stations
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("stations")
public class Stations extends Model<Stations> implements Serializable {
    //站点信息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long stationId;

    //站点名称
    private String stationName;

    //站点所属城市数字编码字符串
    private String cityCode;

    //站点状态
    private Boolean state;

    //站点信息创建时间
    private Date createTime;

    //站点信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
