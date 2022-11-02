package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName user_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class UserInfo extends Model<UserInfo> implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private Date birthday;

    /**
     * 
     */
    private String cityCode;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String avatarFileId;

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