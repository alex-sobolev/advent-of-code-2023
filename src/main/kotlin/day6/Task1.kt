package adventofcode2023.day6

import java.io.File

typealias Race = Pair<Int, Int> // time, distance

class Task1(private val filePath: String = "src/main/kotlin/day6/input.txt") {
    fun solve(): Int {
        val lines = File(filePath).readLines()
        val times = lines[0].substringAfter("Time: ").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val distances = lines[1].substringAfter("Distance: ").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val races = times.zip(distances)

        val result = races.fold(1) { acc, pair ->
            val numberOfWays = getNumberOfWayToWinRace(pair)
            acc * numberOfWays
        }

        return result
    }

    private fun getDistanceTraveled(raceTime: Int, holdTime: Int): Int = (raceTime - holdTime) * holdTime

    private fun getNumberOfWayToWinRace(race: Race): Int {
        val raceTime = race.first
        val raceDistanceRecord = race.second
        val numberOfWays = mutableListOf<Int>()

        for (i in 1..<raceTime) {
            val dist = getDistanceTraveled(raceTime, i)

            if (dist > raceDistanceRecord) {
                numberOfWays.add(i)
            }
        }

        return numberOfWays.size
    }
}

fun main() {
    val test = Task1("src/main/kotlin/day6/input2.txt")
    println("task1 test: ${test.solve()}") // 288

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 1660968
}
