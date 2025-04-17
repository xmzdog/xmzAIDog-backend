package com.xmz.xmzaidogbiz.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @TableName ai_answer
 */
@TableName(value ="ai_answer")
@Data
public class AiAnswer implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 问题id
     */
    private Integer question_id;

    /**
     * ai的回答
     */
    private String answer;

    /**
     * 创建时间（ai返回的生成时间）
     */
    private Date create_date;

    /**
     * 停止的原因
     */
    private String stop;

    /**
     * 生成回答的模型名
     */
    private String model;

    /**
     * 模型生成产生的token数
     */
    private String completion_tokens;

    /**
     * prompt的token数
     */
    private String prompt_tokens;

    /**
     * 该次请求中所有的token数
     */
    private String total_tokens;

    /**
     * 生成状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AiAnswer other = (AiAnswer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQuestion_id() == null ? other.getQuestion_id() == null : this.getQuestion_id().equals(other.getQuestion_id()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getCreate_date() == null ? other.getCreate_date() == null : this.getCreate_date().equals(other.getCreate_date()))
            && (this.getStop() == null ? other.getStop() == null : this.getStop().equals(other.getStop()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getCompletion_tokens() == null ? other.getCompletion_tokens() == null : this.getCompletion_tokens().equals(other.getCompletion_tokens()))
            && (this.getPrompt_tokens() == null ? other.getPrompt_tokens() == null : this.getPrompt_tokens().equals(other.getPrompt_tokens()))
            && (this.getTotal_tokens() == null ? other.getTotal_tokens() == null : this.getTotal_tokens().equals(other.getTotal_tokens()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuestion_id() == null) ? 0 : getQuestion_id().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getCreate_date() == null) ? 0 : getCreate_date().hashCode());
        result = prime * result + ((getStop() == null) ? 0 : getStop().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getCompletion_tokens() == null) ? 0 : getCompletion_tokens().hashCode());
        result = prime * result + ((getPrompt_tokens() == null) ? 0 : getPrompt_tokens().hashCode());
        result = prime * result + ((getTotal_tokens() == null) ? 0 : getTotal_tokens().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", question_id=").append(question_id);
        sb.append(", answer=").append(answer);
        sb.append(", create_date=").append(create_date);
        sb.append(", stop=").append(stop);
        sb.append(", model=").append(model);
        sb.append(", completion_tokens=").append(completion_tokens);
        sb.append(", prompt_tokens=").append(prompt_tokens);
        sb.append(", total_tokens=").append(total_tokens);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}