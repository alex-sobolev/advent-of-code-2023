package adventofcode2023.day6

import java.io.File

typealias LongRace = Pair<Long, Long> // time, distance

class Task2(private val filePath: String = "src/main/kotlin/day6/input.txt") {
    private fun parseInput(name: String, line: String): Long = line
        .substringAfter("$name")
        .split(" ")
        .filter { it.isNotBlank() }
        .fold("") { acc, s -> acc + s }
        .toLong()

    private fun getDistanceTraveled(raceTime: Long, holdTime: Long): Long = (raceTime - holdTime) * holdTime

    private fun getNumberOfWayToWinRace(race: LongRace): Long {
        val raceTime = race.first
        val raceDistanceRecord = race.second
        var numberOfWays: Long = 0

        for (i in 1..<raceTime) {
            val dist = getDistanceTraveled(raceTime, i)

            if (dist > raceDistanceRecord) {
                numberOfWays += 1
            }
        }

        return numberOfWays
    }

    fun solve(): Long {
        val lines = File(filePath).readLines()
        val time = parseInput("Time:", lines[0])
        val distance = parseInput("Distance:", lines[1])

        return getNumberOfWayToWinRace(Pair(time, distance))
    }
}

fun main() {
    val test = Task2("src/main/kotlin/day6/input2.txt")
    println("task 2 test: ${test.solve()}") // 71503

    val task2 = Task2()
    println("task 2: ${task2.solve()}") // 26499773
}