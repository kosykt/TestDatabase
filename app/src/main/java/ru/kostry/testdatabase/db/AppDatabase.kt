package ru.kostry.testdatabase.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kostry.testdatabase.App
import ru.kostry.testdatabase.db.exaples.converter.FirstTypeConverter
import ru.kostry.testdatabase.db.exaples.converter.SecondTypeConverter
import ru.kostry.testdatabase.db.exaples.converter.ThirdTypeConverter
import ru.kostry.testdatabase.db.exaples.dao.FirstDao
import ru.kostry.testdatabase.db.exaples.dao.SecondDao
import ru.kostry.testdatabase.db.exaples.dao.ThirdDao
import ru.kostry.testdatabase.db.exaples.model.FirstEntity
import ru.kostry.testdatabase.db.exaples.model.SecondEntity
import ru.kostry.testdatabase.db.exaples.model.ThirdEntity

@Database(
    entities = [
        FirstEntity::class,
        SecondEntity::class,
        ThirdEntity::class,
        PersonEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    FirstTypeConverter::class,
    SecondTypeConverter::class,
    ThirdTypeConverter::class,
    PersonTypeConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val firstDao: FirstDao
    abstract val secondDao: SecondDao
    abstract val thirdDao: ThirdDao
    abstract val personDao: PersonDao

    companion object {
        private const val DB_NAME = "database.db"

        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}