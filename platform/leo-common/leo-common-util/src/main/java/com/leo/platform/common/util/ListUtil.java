package com.leo.platform.common.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ListUtil {

    /**
     * 获取list的第一个元素.
     * 
     * @param list
     * @return
     */
    public static <T> T getFirst(List<T> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 如果list为null，返回一个空list
     * 
     * @param list
     * @return
     */
    public static <T> List<T> nullToEmptyList(List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list;

    }

    public static <T> void addIgnoreNull(List<T> list, T t) {
        if (t == null) {
            return;
        }
        list.add(t);
    }

    public static <T> boolean isEmptyOrNull(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmptyAndNull(List<T> list) {
        return list != null && !list.isEmpty();
    }

    public static <T> T getLast(List<T> list) {
        if (isNotEmptyAndNull(list)) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    public static <T> Optional<T> getFirstItem(List<T> list) {
        return Optional.fromNullable(getFirst(list));
    }

    public static <T> Optional<T> getLastItem(List<T> list) {
        return Optional.fromNullable(getLast(list));
    }

    public static <T, R> void consume(List<T> list, Consumer<T> comsumer) {
        for (T t : list) {
            comsumer.apply(t);
        }
    }

    /**
     * list消费函数接口
     * 
     * @param <T>
     */
    public static interface Consumer<T> {
        void apply(T t);
    }

    public static int size(List list) {
        return ListUtil.nullToEmptyList(list).size();
    }

    public static <T> List<List<T>> splitBySize(List<T> list, int size) {
        List<List<T>> listlist = Lists.newArrayList();
        int num = new Double(Math.ceil(list.size() / new Double(size))).intValue();
        for (int i = 0; i < num; i++) {
            List<T> tmp = Lists.newArrayList();
            if (i + 1 == num) {
                tmp.addAll(list.subList(i * size, list.size()));
            } else {
                tmp.addAll(list.subList(i * size, (i + 1) * size));
            }
            listlist.add(tmp);
        }
        return listlist;
    }

    /**
     * list转set，如果list为空或者没有数据，返回一个空set
     * 
     * @Title: listToSet
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param list
     * @param @return 设定文件
     * @return Set<T> 返回类型
     * @throws
     */
    public static <T> Set<T> listToSet(List list) {
        Set<T> set = Sets.newHashSet();
        if (list != null && list.size() > 0) {
            set.addAll(list);
        }
        return set;
    }

    /**
     * set转list，如果set为空或者没有数据，就返回一个空list
     * 
     * @Title: setToList
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param set
     * @param @return 设定文件
     * @return List<T> 返回类型
     * @throws
     */
    public static <T> List<T> setToList(Set set) {
        List<T> list = Lists.newArrayList();
        if (set != null && set.size() > 0) {
            list.addAll(set);
        }
        return list;
    }
}
