package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName messages
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("messages")
public class Messages extends Model<Messages> implements Serializable {
    /**
     * 
     */
    private Long messageId;

    /**
     * 
     */
    private Long issueId;

    /**
     * 
     */
    private String senderRole;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}