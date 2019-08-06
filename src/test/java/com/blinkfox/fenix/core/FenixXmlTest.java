package com.blinkfox.fenix.core;

import static org.junit.Assert.assertEquals;

import com.blinkfox.fenix.bean.SqlInfo;
import com.blinkfox.fenix.config.FenixConfig;
import com.blinkfox.fenix.config.FenixConfigManager;
import com.blinkfox.fenix.entity.User;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 用于测试 Fenix XML 标签生成 SQL 和参数的测试类.
 *
 * @author blinkfox on 2019-08-06.
 */
public class FenixXmlTest {

    private static final String NAME = "ZhangSan";

    /**
     * 基础查询的 SQL 语句.
     */
    private static final String BASE_QUERY = "SELECT u FROM User WHERE";

    /**
     * 上下文参数 Map.
     */
    private static Map<String, Object> context;

    /**
     * 初始化上下文参数.
     */
    @BeforeClass
    public static void init() {
        FenixConfigManager.getInstance().initLoad(new FenixConfig());
        context = new HashMap<>(4);
        context.put("entityName", User.class.getSimpleName());
        context.put("user", new User().setId("123").setName(NAME));
        context.put("email", "zhangsan@163.com");
    }

    /**
     * 测试 equal 标签的情况.
     */
    @Test
    public void testEqual() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.equal", context);
        assertEquals(BASE_QUERY + " u.id = :user_id AND u.name = :user_name OR u.email = :email",
                sqlInfo.getSql());

        Map<String, Object> params = sqlInfo.getParams();
        assertEquals(3, params.size());
        assertEquals(NAME, params.get("user_name"));
        assertEquals("zhangsan@163.com", params.get("email"));
    }

    /**
     * 测试 notEqual 标签的情况.
     */
    @Test
    public void notEqual() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.notEqual", context);
        assertEquals(BASE_QUERY + " u.id <> :user_id AND u.name <> :user_name OR u.email <> :email",
                sqlInfo.getSql());
        assertEquals(3, sqlInfo.getParams().size());
    }

    /**
     * 测试 greaterThan 标签的情况.
     */
    @Test
    public void greaterThan() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.greaterThan", context);
        assertEquals(BASE_QUERY + " u.id > :user_id AND u.name > :user_name OR u.email > :email",
                sqlInfo.getSql());
        assertEquals(3, sqlInfo.getParams().size());
    }

    /**
     * 测试 lessThan 标签的情况.
     */
    @Test
    public void lessThan() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.lessThan", context);
        assertEquals(BASE_QUERY + " u.id < :user_id AND u.name < :user_name OR u.email < :email",
                sqlInfo.getSql());
        assertEquals(3, sqlInfo.getParams().size());
    }

    /**
     * 测试 greaterThanEqual 标签的情况.
     */
    @Test
    public void greaterThanEqual() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.greaterThanEqual", context);
        assertEquals(BASE_QUERY + " u.id >= :user_id AND u.name >= :user_name OR u.email >= :email",
                sqlInfo.getSql());
        assertEquals(3, sqlInfo.getParams().size());
    }

    /**
     * 测试 greaterThanEqual 标签的情况.
     */
    @Test
    public void lessThanEqual() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.lessThanEqual", context);
        assertEquals(BASE_QUERY + " u.id <= :user_id AND u.name <= :user_name OR u.email <= :email",
                sqlInfo.getSql());
        assertEquals(3, sqlInfo.getParams().size());
    }

    /**
     * 测试 like 标签的情况.
     */
    @Test
    public void like() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.like", context);
        assertEquals(BASE_QUERY + " u.id LIKE :user_id AND u.name LIKE :user_name OR u.email LIKE '%@163.com'",
                sqlInfo.getSql());
        assertEquals(2, sqlInfo.getParams().size());
    }

    /**
     * 测试 notLike 标签的情况.
     */
    @Test
    public void notLike() {
        SqlInfo sqlInfo = Fenix.getSqlInfo("fenix.notLike", context);
        assertEquals(BASE_QUERY + " u.id NOT LIKE :user_id AND u.name NOT LIKE :user_name "
                        + "OR u.email NOT LIKE '%@163.com'",
                sqlInfo.getSql());
        assertEquals(2, sqlInfo.getParams().size());
    }

}