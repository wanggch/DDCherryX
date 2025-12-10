<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${className}Mapper">
    <!--
        ${functionName} 基础映射
    -->
    <resultMap id="BaseResultMap" type="${entityPackage}.${className}">
${fields}    </resultMap>
</mapper>
