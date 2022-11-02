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
 * 管理员
 * @TableName admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("admin")
public class Admin extends Model<Admin> implements Serializable {
    //管理员编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long adminId;

    //管理员登录账号
    private String account;

    //管理员登录密码
    private String password;

    //管理员电话号码
    private String telephone;

    //管理员权限(数字越大权限越高)
    private Integer power;

    //管理员信息创建时间
    private Date createTime;

    //管理员信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}