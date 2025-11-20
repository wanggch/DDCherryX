package cn.ddcherry.framework.mybatis.handler;

import cn.ddcherry.common.mybatis.core.AuditContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 字段填充处理器，统一维护基础审计字段。
 */
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, "updateTime", () -> now, LocalDateTime.class);
        strictInsertFill(metaObject, "createBy", AuditContextHolder::getUserId, Long.class);
        strictInsertFill(metaObject, "updateBy", AuditContextHolder::getUserId, Long.class);
        strictInsertFill(metaObject, "createDept", AuditContextHolder::getDeptId, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        strictUpdateFill(metaObject, "updateBy", AuditContextHolder::getUserId, Long.class);
    }
}
