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
import org.hibernate.validator.constraints.Length;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * 用户账户信息实体
 * @TableName user_account
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_account")
public class UserAccount extends Model<UserAccount> implements Serializable {
    //用户编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    //用户登录账号(电话号码)
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "手机号码格式有误")
    @Length(min = 11, max = 11, message = "手机号码必须为11位")
    private String account;

    //用户登录密码
    @Length(min = 6, max = 17, message = "登录密码在6~17位")
    private String password;

    //用户账号是否可用
    private Boolean available;

    //用户账户信息创建时间
    private Date createTime;

    //用户账户信息更新时间
    private Date updateTime;

    //用户附加信息对象(数据库中不存在)
    @TableField(exist = false)
    @Valid
    private UserInfo userInfo;

    private static final long serialVersionUID = 1L;


}