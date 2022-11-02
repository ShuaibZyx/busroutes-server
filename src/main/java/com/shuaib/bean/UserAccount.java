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
import lombok.NoArgsConstructor;

/**
 * @TableName user_account
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_account")
public class UserAccount extends Model<UserAccount> implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     *
     */
    private String account;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private Integer available;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Date createTime;

    @TableField(exist = false)
    private UserInfo userInfo;

    private static final long serialVersionUID = 1L;


}