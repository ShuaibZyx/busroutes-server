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
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 管理员
 *
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
    @Length(min = 5, max = 11, message = "管理员账号应在5~11位")
    private String account;

    //管理员登录密码
    @Length(min = 6, max = 17, message = "管理员密码应在6~17位")
    private String password;

    //管理员电话号码
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "手机号码格式有误")
    @Length(min = 11, max = 11, message = "手机号码必须为11位")
    private String telephone;

    //管理员权限(数字越大权限越高)
    @Range(min = 0, max = 3, message = "管理员权限数字应在0~3之间")
    private Integer power;

    //管理员信息创建时间
    private Date createTime;

    //管理员信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}