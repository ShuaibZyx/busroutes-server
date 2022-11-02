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
 * 用户信息实体(附加信息)
 * @TableName user_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class UserInfo extends Model<UserInfo> implements Serializable {
    //用户编号(外键-->(user_account:user_id))
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    //用户昵称
    private String nickname;

    //用户电话号码
    private String telephone;

    //用户性别
    private Integer gender;

    //用户年龄
    private Integer age;

    //用户生日日期
    private Date birthday;

    //用户所属城市数字编码字符串
    private String cityCode;

    //用户邮箱
    private String email;

    //用户头像文件url字符串
    private String avatarFileId;

    //用户信息创建时间
    private Date createTime;

    //用户信息更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}