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
 * 公告信息实体
 *
 * @TableName notices
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("notices")
public class Notices extends Model<Notices> implements Serializable {
    //公告信息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long noticeId;

    //公告标题
    @Length(min = 2, max = 100, message = "公告标题长度应在2~100位")
    private String title;

    //公告具体内容
    private String content;

    //公告发布者(管理员编号,外键-->(admin:admin_id))
    @NotNull
    private Long publisherId;

    //公告创建时间
    private Date createTime;

    //公告更新时间
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}