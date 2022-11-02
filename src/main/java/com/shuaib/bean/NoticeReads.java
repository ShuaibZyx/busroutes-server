package com.shuaib.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName notice_reads
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("notice_reads")
public class NoticeReads implements Serializable {
    /**
     * 
     */
    private Long noticeReadId;

    /**
     * 
     */
    private Long noticeId;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Integer state;

    /**
     * 
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}