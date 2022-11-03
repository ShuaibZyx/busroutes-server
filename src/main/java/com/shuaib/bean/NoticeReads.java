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
 * 公告阅读记录实体
 * @TableName notice_reads
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("notice_reads")
public class NoticeReads extends Model<NoticeReads> implements Serializable {

    //公告记录编号(外键-->(notices:notice_id))
    @TableId(type = IdType.ASSIGN_ID)
    private Long noticeId;

    //用户编号(外键-->(user_account:user_id))
    private Long userId;

    //阅读状态
    private Boolean state;

    //公告阅读记录创建时间
    private Date createTime;

    private static final long serialVersionUID = 1L;
}