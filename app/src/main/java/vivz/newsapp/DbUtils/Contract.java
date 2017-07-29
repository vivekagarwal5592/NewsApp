package vivz.newsapp.DbUtils;

import android.provider.BaseColumns;

/**
 * Created by user on 22-07-2017.
 */

public class Contract {

    public static class TABLE_NewsApp implements BaseColumns {
        public static final String TABLE_NAME = "NewsApp";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGE= "image";
        public static final String COLUMN_NAME_URL= "url";
        public static final String COLUMN_NAME_AUTHOR= "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

    }
}
