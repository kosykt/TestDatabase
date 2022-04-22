package ru.kostry.testdatabase

import android.util.Log
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit

class Test {
    fun start() {
        fillTrainRunListWithDrivers(trainRunList, driverList)
        println(trainRunList.joinToString("\n"))
        println(driverList.map { it.workedHours })
        Log.d("testSort_trainRunList", trainRunList.joinToString("\n"))
        Log.d("testSort_driverList", driverList.map { it.workedHours }.toString())
    }
}

/**
 * Класс Train определяет сущность поезда, необходимую для идентификации направления и вывода информации о нём.
 *
 * @param number номер поезда
 * @param direction название направления
 */
data class Train(
    val number: Int,
    val direction: String,
)

/**
 * Класс TrainRun определяет сущность поездки(рейса), необходима для составления сетки выездов и
 * заполнения данными о времени, машинистах, и других данных.
 *
 * @param id идентификатор поезда в сетке выездов
 * @param trainNumber номер поезда
 * @param driverId Id машиниста
 * @param startTime время отправления
 * @param endTime время прибытия
 */
data class TrainRun(
    val id: Int,
    val trainNumber: Int,
    var driverId: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)

/**
 * Класс Driver определяет сущность машиниста.
 *
 * @param id идентификатор машиниста
 * @param name имя машиниста
 * @param surname фамилия машиниста
 * @param patronymic отчество машиниста
 * @param workedHours отработано часов
 * @param totalHours всего рабочих часов
 * @param accessTrainsId список доступных поездов (направлений)
 */
data class Driver(
    val id: Int,
    val name: String,
    val surname: String,
    val patronymic: String,
    var workedHours: Int,
    var totalHours: Int,
    var accessTrainsId: List<Int>
)

val trainList = listOf(
    Train(120, "Москва"),
    Train(92, "Санкт-Петербург"),
    Train(32, "Краснодар"),
    Train(14, "Ростов-на-Дону"),
    Train(51, "Воронеж"),
    Train(96, "Туапсе"),
    Train(72, "Ставрополь"),
    Train(80, "Тюмень"),
    Train(99, "Нижний Новгород"),
    Train(103, "Новороссийск"),
    Train(11, "Казань"),
    Train(125, "Киров")
)

val trainRunList = listOf(
    TrainRun(
        1, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 2, 1, 10),
    ),
    TrainRun(
        2, 14, 0,
        LocalDateTime.of(2022, Month.APRIL, 1, 12, 30),
        LocalDateTime.of(2022, Month.APRIL, 2, 5, 10),
    ),
    TrainRun(
        3, 92, 0,
        LocalDateTime.of(2022, Month.APRIL, 1, 16, 30),
        LocalDateTime.of(2022, Month.APRIL, 1, 22, 10),
    ),
    TrainRun(
        4, 32, 0,
        LocalDateTime.of(2022, Month.APRIL, 2, 3, 30),
        LocalDateTime.of(2022, Month.APRIL, 2, 12, 10),
    ),
    TrainRun(
        5, 51, 0,
        LocalDateTime.of(2022, Month.APRIL, 2, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 2, 11, 12),
    ),
    TrainRun(
        6, 96, 0,
        LocalDateTime.of(2022, Month.APRIL, 2, 22, 30),
        LocalDateTime.of(2022, Month.APRIL, 3, 2, 12),
    ),
    TrainRun(
        7, 72, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 2, 30),
        LocalDateTime.of(2022, Month.APRIL, 3, 5, 12),
    ),
    TrainRun(
        8, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 4, 3, 12),
    ),
    TrainRun(
        9, 99, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 8, 30),
        LocalDateTime.of(2022, Month.APRIL, 3, 23, 12),
    ),
    TrainRun(
        10, 80, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 20, 30),
        LocalDateTime.of(2022, Month.APRIL, 4, 15, 12),
    ),
    TrainRun(
        11, 103, 0,
        LocalDateTime.of(2022, Month.APRIL, 4, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 5, 12, 12),
    ),
    TrainRun(
        12, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 5, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 6, 1, 12),
    ),
    TrainRun(
        13, 11, 0,
        LocalDateTime.of(2022, Month.APRIL, 5, 3, 30),
        LocalDateTime.of(2022, Month.APRIL, 7, 11, 12),
    ),
    TrainRun(
        14, 125, 0,
        LocalDateTime.of(2022, Month.APRIL, 6, 13, 30),
        LocalDateTime.of(2022, Month.APRIL, 7, 1, 12),
    ),
    TrainRun(
        15, 51, 0,
        LocalDateTime.of(2022, Month.APRIL, 7, 12, 30),
        LocalDateTime.of(2022, Month.APRIL, 8, 5, 12),
    ),
    TrainRun(
        16, 14, 0,
        LocalDateTime.of(2022, Month.APRIL, 8, 14, 30),
        LocalDateTime.of(2022, Month.APRIL, 9, 18, 12),
    ),
    TrainRun(
        17, 72, 0,
        LocalDateTime.of(2022, Month.APRIL, 8, 10, 30),
        LocalDateTime.of(2022, Month.APRIL, 9, 22, 12),
    ),
    TrainRun(
        18, 99, 0,
        LocalDateTime.of(2022, Month.APRIL, 8, 16, 30),
        LocalDateTime.of(2022, Month.APRIL, 10, 1, 12),
    ),
    TrainRun(
        19, 32, 0,
        LocalDateTime.of(2022, Month.APRIL, 9, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 10, 1, 12),
    ),
    TrainRun(
        20, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 9, 6, 30),
        LocalDateTime.of(2022, Month.APRIL, 10, 1, 12),
    )
)

val driverList = listOf(
    Driver(1, "Иван", "", "", 0, 0, listOf(120, 92, 14)),
    Driver(2, "Олег", "", "", 0, 0, listOf(32, 14, 51)),
    Driver(3, "Василий", "", "", 0, 0, listOf(51, 72, 80)),
    Driver(4, "Николай", "", "", 0, 0, listOf(96, 72)),
    Driver(5, "Семен", "", "", 0, 0, listOf(90, 99)),
    Driver(6, "Иннокентий", "", "", 0, 0, listOf(103, 11, 125, 96)),
    Driver(7, "Дмитрий", "", "", 0, 0, listOf(72, 51, 92)),
    Driver(8, "Вячеслав", "", "", 0, 0, listOf(92, 96)),
    Driver(9, "Александр", "", "", 0, 0, listOf(120, 92)),
    Driver(10, "Андрей", "", "", 0, 0, listOf(72, 32, 11)),
    Driver(11, "Владимир", "", "", 0, 0, listOf(103, 14, 99)),
    Driver(12, "Павел", "", "", 0, 0, listOf(125, 14)),
//    Driver(13,"Леонид", "", "", 0, 0, listOf(11, 99, 32)),
//    Driver(14,"Алексей", "", "", 0, 0, listOf(14, 80, 11)),
//    Driver(15,"Юрий", "", "", 0, 0, listOf(99, 92, 96)),
//    Driver(16,"Виталий", "", "", 0, 0, listOf(32, 96, 11)),
//    Driver(17,"Всеволод", "", "", 0, 0, listOf(120, 92)),
//    Driver(18,"Никита", "", "", 0, 0, listOf(103, 14)),
//    Driver(19,"Виктор", "", "", 0, 0, listOf(72, 99)),
//    Driver(20,"Евлампий", "", "", 0, 0, listOf(32, 92, 72))
)

const val restHours = 16.toLong()

fun getBusyOrRestDriversIdsOnTime(trainRunList: List<TrainRun>, time: LocalDateTime): List<Int> {
    return trainRunList
        .filter { it.driverId > 0 }
        .filter { time >= it.startTime && time <= it.endTime.plusHours(restHours) }
        .map { it.driverId }
}

fun fillTrainRunListWithDrivers(trainRunList: List<TrainRun>, drivers: List<Driver>) {
    (trainRunList).forEach { trainRun ->
        if (trainRun.driverId == 0) {
            trainRun.driverId = drivers
                .filter { it.id !in getBusyOrRestDriversIdsOnTime(trainRunList, trainRun.startTime) }
                .filter { it.accessTrainsId.contains(trainRun.trainNumber) }
                .minByOrNull { it.workedHours }?.id ?: 0
            drivers.find { it.id == trainRun.driverId }?.workedHours =
                ChronoUnit.HOURS.between(trainRun.startTime, trainRun.endTime).toInt()
        }
    }
}