package com.test.approval.flow.repository.entity;

import java.util.Date;
import javax.persistence.*;

public class User {
    /**
     * 后台管理用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 后台管理用户姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 后台管理用户账号
     */
    @Column(name = "user_account")
    private String userAccount;

    /**
     * 后台管理用户密码
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * 后台管理用户邮箱
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 后台管理用户电话
     */
    @Column(name = "user_telphone")
    private String userTelphone;

    /**
     * 后台管理用户状态默认11表示正常0表示停用
     */
    @Column(name = "user_status")
    private Byte userStatus;

    /**
     * 用户部门
     */
    @Column(name = "user_department")
    private Long userDepartment;

    /**
     * 用户职位
     */
    @Column(name = "user_position")
    private Long userPosition;

    /**
     * 密码加密
     */
    private String salt;

    /**
     * 创建时间
     */
    @Column(name = "creation_time")
    private Date creationTime;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private Long createdBy;

    /**
     * 最后更新时间
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    /**
     * 最后更新人
     */
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    /**
     * 可用状态，默认为1，可用；0为不可用
     */
    @Column(name = "enabled_flag")
    private Byte enabledFlag;

    /**
     * 获取后台管理用户ID
     *
     * @return id - 后台管理用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置后台管理用户ID
     *
     * @param id 后台管理用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取后台管理用户姓名
     *
     * @return user_name - 后台管理用户姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置后台管理用户姓名
     *
     * @param userName 后台管理用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取后台管理用户账号
     *
     * @return user_account - 后台管理用户账号
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 设置后台管理用户账号
     *
     * @param userAccount 后台管理用户账号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    /**
     * 获取后台管理用户密码
     *
     * @return user_password - 后台管理用户密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置后台管理用户密码
     *
     * @param userPassword 后台管理用户密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * 获取后台管理用户邮箱
     *
     * @return user_email - 后台管理用户邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置后台管理用户邮箱
     *
     * @param userEmail 后台管理用户邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * 获取后台管理用户电话
     *
     * @return user_telphone - 后台管理用户电话
     */
    public String getUserTelphone() {
        return userTelphone;
    }

    /**
     * 设置后台管理用户电话
     *
     * @param userTelphone 后台管理用户电话
     */
    public void setUserTelphone(String userTelphone) {
        this.userTelphone = userTelphone == null ? null : userTelphone.trim();
    }

    /**
     * 获取后台管理用户状态默认11表示正常0表示停用
     *
     * @return user_status - 后台管理用户状态默认11表示正常0表示停用
     */
    public Byte getUserStatus() {
        return userStatus;
    }

    /**
     * 设置后台管理用户状态默认11表示正常0表示停用
     *
     * @param userStatus 后台管理用户状态默认11表示正常0表示停用
     */
    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取用户部门
     *
     * @return user_department - 用户部门
     */
    public Long getUserDepartment() {
        return userDepartment;
    }

    /**
     * 设置用户部门
     *
     * @param userDepartment 用户部门
     */
    public void setUserDepartment(Long userDepartment) {
        this.userDepartment = userDepartment;
    }

    /**
     * 获取用户职位
     *
     * @return user_position - 用户职位
     */
    public Long getUserPosition() {
        return userPosition;
    }

    /**
     * 设置用户职位
     *
     * @param userPosition 用户职位
     */
    public void setUserPosition(Long userPosition) {
        this.userPosition = userPosition;
    }

    /**
     * 获取密码加密
     *
     * @return salt - 密码加密
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置密码加密
     *
     * @param salt 密码加密
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取创建时间
     *
     * @return creation_time - 创建时间
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建人
     *
     * @return created_by - 创建人
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取最后更新时间
     *
     * @return last_update_time - 最后更新时间
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 设置最后更新时间
     *
     * @param lastUpdateTime 最后更新时间
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 获取最后更新人
     *
     * @return last_updated_by - 最后更新人
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * 设置最后更新人
     *
     * @param lastUpdatedBy 最后更新人
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * 获取可用状态，默认为1，可用；0为不可用
     *
     * @return enabled_flag - 可用状态，默认为1，可用；0为不可用
     */
    public Byte getEnabledFlag() {
        return enabledFlag;
    }

    /**
     * 设置可用状态，默认为1，可用；0为不可用
     *
     * @param enabledFlag 可用状态，默认为1，可用；0为不可用
     */
    public void setEnabledFlag(Byte enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}