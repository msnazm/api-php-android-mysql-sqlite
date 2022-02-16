package com.portal.full.appmozoou.db;

import android.provider.BaseColumns;

public class Bookmark {
    public static final String DB_NAME = "com.portal.full.appmozoou.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_ID = "id";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_CONTENT = "content";
    }
}
