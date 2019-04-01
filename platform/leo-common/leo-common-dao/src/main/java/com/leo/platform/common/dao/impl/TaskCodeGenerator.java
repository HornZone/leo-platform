package com.leo.platform.common.dao.impl;

import com.leo.platform.common.dao.util.CodeUtil;
import com.leo.platform.common.dao.util.EntityCodePrefixMapping.CodeConfig;
import com.leo.platform.common.sysmanage.task.Task;

/**
 * 任务跟踪代号生成器
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Caven_Zhou
 * @date 2016年10月20日 上午9:21:31
 * 
 */
public class TaskCodeGenerator implements CodeGenerator<Task> {

    @Override
    public String generate(Task task, Long seq) {
        return CodeUtil.generateCode(new CodeConfig("JT", "yyMMdd", 6), seq);
    }

}
