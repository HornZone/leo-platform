package com.leo.platform.common.dao.impl;

import com.leo.platform.common.dao.util.CodeUtil;

public class DefaultCodeGenerator<T> implements CodeGenerator<T> {

    @Override
    public String generate(T entity, Long seq) {
        return CodeUtil.generateCode(entity.getClass(), seq);
    }

}
