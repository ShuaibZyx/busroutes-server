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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 公交信息
 *
 * @TableName buses
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("buses")
public class Buses extends Model<Buses> implements Serializable {
    //公交信息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long busId;

    //公交名称
    @Length(min = 1, max = 15, message = "公交车名称应在1~15位")
    private String busName;

    //公交所属城市
    private String cityCode;

    //公交类型
    private Integer type;

    //公交信息创建时间
    private Date createTime;

    //公交信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}