package cn.mmooo.config;

/**
 * 动态数据源容器
 */
public final class DynamicDataSourceHolder {
    public static final String MASTER_DATA_SOURCE = "Master";
    public static final String SLAVE_DATA_SOURCE = "Slave";

    private static final ThreadLocal<String> CONTAINER = new ThreadLocal<>();

    private static void set(String dataSource) {
        CONTAINER.set(dataSource);
    }

    public static void routeMaster() {
        set(MASTER_DATA_SOURCE);
    }

    public static void routeSlave() {
        set(SLAVE_DATA_SOURCE);
    }

    public static String get() {
        return CONTAINER.get();
    }

    public static void clear() {
        CONTAINER.remove();
    }
}