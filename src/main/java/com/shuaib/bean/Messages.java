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

import javax.validation.constraints.NotNull;


/**
 * 留言消息实体
 *
 * @TableName messages
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("messages")
public class Messages extends Model<Messages> implements Serializable {
    //消息编号(雪花算法)
    @TableId(type = IdType.ASSIGN_ID)
    private Long messageId;

    //留言编号(外键-->(issues:issue_id))
    @NotNull
    private Long issueId;

    //发送者的角色字符串(user/admin)
    private String senderRole;

    //消息内容
    private String content;

    //消息创建时间
    private Date createTime;

    private static final long serialVersionUID = 1L;
}