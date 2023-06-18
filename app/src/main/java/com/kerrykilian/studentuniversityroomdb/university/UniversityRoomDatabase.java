package com.kerrykilian.studentuniversityroomdb.university;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kerrykilian.studentuniversityroomdb.student.Student;
import com.kerrykilian.studentuniversityroomdb.student.StudentDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {University.class, Student.class}, version = 1, exportSchema = false)
public abstract class UniversityRoomDatabase extends RoomDatabase {

    public abstract UniversityDao universityDao();

    public abstract StudentDao studentDao();

    private static volatile UniversityRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UniversityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UniversityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UniversityRoomDatabase.class, "university_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UniversityDao dao = INSTANCE.universityDao();
                dao.deleteAll();

//                University university = new University("Hello");
//                dao.insert(university);
//                university = new University("World");
//                dao.insert(university);
            });
        }
    };
}