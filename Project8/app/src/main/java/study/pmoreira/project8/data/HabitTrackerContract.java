package study.pmoreira.project8.data;

import android.provider.BaseColumns;

public final class HabitTrackerContract {

    private HabitTrackerContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_HABIT_NAME = "habit_name";
        public static final String COLUMN_HABIT_COUNT = "habit_count";
    }

}
