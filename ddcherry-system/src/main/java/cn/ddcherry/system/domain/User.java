package cn.ddcherry.system.domain;

import cn.ddcherry.common.mybatis.annotation.Sensitive;
import cn.ddcherry.common.mybatis.core.domain.BaseEntity;
import cn.ddcherry.common.mybatis.enums.SensitiveType;
import cn.ddcherry.common.mybatis.handler.SensitiveTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Placeholder entity.
 */
@Data
@TableName("sys_user")
public class User extends BaseEntity {
    @TableId
    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    @Sensitive(type = SensitiveType.EMAIL)
    @TableField(typeHandler = SensitiveTypeHandler.class)
    private String email;
}
