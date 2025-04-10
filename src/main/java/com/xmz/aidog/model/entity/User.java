package com.xmz.aidog.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
public class User implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String useraccount;

    /**
     * 密码
     */
    private String userpassword;

    /**
     * 微信开放平台id
     */
    private String unionid;

    /**
     * 公众号openId
     */
    private String mpopenid;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String useravatar;

    /**
     * 用户简介
     */
    private String userprofile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userrole;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 账号
     */
    public String getUseraccount() {
        return useraccount;
    }

    /**
     * 账号
     */
    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    /**
     * 密码
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * 密码
     */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    /**
     * 微信开放平台id
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * 微信开放平台id
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /**
     * 公众号openId
     */
    public String getMpopenid() {
        return mpopenid;
    }

    /**
     * 公众号openId
     */
    public void setMpopenid(String mpopenid) {
        this.mpopenid = mpopenid;
    }

    /**
     * 用户昵称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户昵称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 用户头像
     */
    public String getUseravatar() {
        return useravatar;
    }

    /**
     * 用户头像
     */
    public void setUseravatar(String useravatar) {
        this.useravatar = useravatar;
    }

    /**
     * 用户简介
     */
    public String getUserprofile() {
        return userprofile;
    }

    /**
     * 用户简介
     */
    public void setUserprofile(String userprofile) {
        this.userprofile = userprofile;
    }

    /**
     * 用户角色：user/admin/ban
     */
    public String getUserrole() {
        return userrole;
    }

    /**
     * 用户角色：user/admin/ban
     */
    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    /**
     * 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 是否删除
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 是否删除
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUseraccount() == null ? other.getUseraccount() == null : this.getUseraccount().equals(other.getUseraccount()))
            && (this.getUserpassword() == null ? other.getUserpassword() == null : this.getUserpassword().equals(other.getUserpassword()))
            && (this.getUnionid() == null ? other.getUnionid() == null : this.getUnionid().equals(other.getUnionid()))
            && (this.getMpopenid() == null ? other.getMpopenid() == null : this.getMpopenid().equals(other.getMpopenid()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getUseravatar() == null ? other.getUseravatar() == null : this.getUseravatar().equals(other.getUseravatar()))
            && (this.getUserprofile() == null ? other.getUserprofile() == null : this.getUserprofile().equals(other.getUserprofile()))
            && (this.getUserrole() == null ? other.getUserrole() == null : this.getUserrole().equals(other.getUserrole()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUseraccount() == null) ? 0 : getUseraccount().hashCode());
        result = prime * result + ((getUserpassword() == null) ? 0 : getUserpassword().hashCode());
        result = prime * result + ((getUnionid() == null) ? 0 : getUnionid().hashCode());
        result = prime * result + ((getMpopenid() == null) ? 0 : getMpopenid().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUseravatar() == null) ? 0 : getUseravatar().hashCode());
        result = prime * result + ((getUserprofile() == null) ? 0 : getUserprofile().hashCode());
        result = prime * result + ((getUserrole() == null) ? 0 : getUserrole().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", useraccount=").append(useraccount);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", unionid=").append(unionid);
        sb.append(", mpopenid=").append(mpopenid);
        sb.append(", username=").append(username);
        sb.append(", useravatar=").append(useravatar);
        sb.append(", userprofile=").append(userprofile);
        sb.append(", userrole=").append(userrole);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}