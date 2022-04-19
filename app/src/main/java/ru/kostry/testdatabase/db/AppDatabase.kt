package ru.kostry.testdatabase.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kostry.testdatabase.App
import ru.kostry.testdatabase.db.converter.FirstTypeConverter
import ru.kostry.testdatabase.db.converter.SecondTypeConverter
import ru.kostry.testdatabase.db.converter.ThirdTypeConverter
import ru.kostry.testdatabase.db.dao.FirstDao
import ru.kostry.testdatabase.db.dao.SecondDao
import ru.kostry.testdatabase.db.dao.ThirdDao
import ru.kostry.testdatabase.db.model.FirstEntity
import ru.kostry.testdatabase.db.model.SecondEntity
import ru.kostry.testdatabase.db.model.ThirdEntity

@Database(
    entities = [FirstEntity::class, SecondEntity::class, ThirdEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FirstTypeConverter::class, SecondTypeConverter::class, ThirdTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val firstDao: FirstDao
    abstract val secondDao: SecondDao
    abstract val thirdDao: ThirdDao

    companion object {
        private const val DB_NAME = "database.db"

        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}