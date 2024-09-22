package com.rc.utils;

public class RedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 1440L;

    public static final Long CACHE_NULL_TTL = 2L;

    public static final Long CACHE_TTL =  720L;
    public static final String HIDDEN_TROUBLE_KEY = "cache:hiddenTrouble:";

    public static final String USER_KEY = "cache:user:";

    public static final String PATROL_LIST_KEY = "cache:patrol:list:";

    public static final String PATROL_POINT_KEY = "cache:patrol:point:";

    public static final String RISK_KEY = "cache:risk:";

    public static final String SNAPSHOTS_KEY = "cache:snapshots:";
    public static final String TASK_KEY = "cache:task:";

    public static final String UNVERIFIED_RISK_KEY = "cache:unverifiedRisk:";


    public static final String LOCK_SHOP_KEY = "lock:shop:";

    public static final String LIST_CHECKED_KEY = "patrol:list:checked:";
    public static final String HIDDEN_CHECKED_KEY= "patrol:hidden:checked:";

}
