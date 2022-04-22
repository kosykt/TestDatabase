package ru.kostry.testdatabase.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kostry.testdatabase.App
import ru.kostry.testdatabase.db.persons.PersonDao
import ru.kostry.testdatabase.db.persons.PersonEntity
import ru.kostry.testdatabase.db.trains.TrainRouteDao
import ru.kostry.testdatabase.db.trains.TrainRouteEntity

@Database(
    entities = [
        PersonEntity::class,
        TrainRouteEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DatabaseTypeConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val personDao: PersonDao
    abstract val trainRouteDao: TrainRouteDao

    companion object {
        private const val DB_NAME = "database.db"

        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}