package adventofcode2023.day2

import java.io.File
import java.io.InputStream

class Task2(private val filePath: String = "src/main/kotlin/day2/input.txt") {
    fun solve(): Int {
        val inputStream: InputStream = File(filePath).inputStream()
        val gameSetsRegex = "Game \\d+: (.*)".toRegex()

        val minGamePowerSets = mutableListOf<Int>()

        inputStream.bufferedReader().forEachLine { line ->
            val gameMaps: List<Map<String, Int>> =
                gameSetsRegex.find(line)?.groupValues?.get(1)?.split(";")?.map { set ->
                    val map = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

                    set.split(",").forEach { colorSet ->
                        val (count, color) = colorSet.trim().split(" ")
                        map[color] = map[color]!! + count.toInt()
                    }

                    map
                } ?: emptyList()

            val minGamePowerMap = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            gameMaps.forEach { gameMap ->
                gameMap.forEach { (color, count) ->
                    if (minGamePowerMap[color]!! < count) {
                        minGamePowerMap[color] = count
                    }
                }
            }

            minGamePowerSets.add(minGamePowerMap.values.fold(1) { acc, i -> acc * i })
        }

        return minGamePowerSets.sum()
    }
}

fun main() {
    val task2 = Task2()
    println("task2: ${task2.solve()}") // 78111
}
