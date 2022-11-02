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

/**
 * 留言实体
 * @TableName issues
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("issues")
public class Issues extends Model<Issues> implements Serializable {
    //留言信息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long issueId;

    //留言创建者编号(外键-->(user_account:user_id))
    private Long createrId;

    //留言者(用户)信息
    @TableField(exist = false)
    private UserInfo userInfo;

    //处理者(管理员)编号(外键-->(admin:admin_id))
    private Long adminId;

    //留言标题
    private String title;

    //留言具体描述
    private String description;

    //留言解决状态
    private Integer state;

    //留言信息创建时间
    private Date createTime;

    //留言信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}