package adventofcode2023.day2

import java.io.File
import java.io.InputStream

class Day2(private val filePath: String = "src/main/kotlin/day2/input.txt") {
    // 12 red cubes, 13 green cubes, and 14 blue cubes
    fun task1(): Int {
        val inputStream: InputStream = File(filePath).inputStream()
        val gameIdRegex = "Game (\\d+)".toRegex();
        val gameSetsRegex = "Game \\d+: (.*)".toRegex()

        val validGameSetIds = mutableListOf<Int>()

        inputStream.bufferedReader().forEachLine { line ->
            val gameId = gameIdRegex.find(line)?.groupValues?.get(1)
            val gameMaps: List<Map<String, Int>> =
                gameSetsRegex.find(line)?.groupValues?.get(1)?.split(";")?.map { set ->
                    val map = mutableMapOf<String, Int>("red" to 0, "green" to 0, "blue" to 0)

                    set.split(",").forEach { colorSet ->
                        val (count, color) = colorSet.trim().split(" ")
                        map[color] = map[color]!! + count.toInt()
                    }

                    map
                } ?: emptyList()

            val isValidGame = gameMaps.all { gameMap ->
                gameMap["red"]!! <= 12 && gameMap["green"]!! <= 13 && gameMap["blue"]!! <= 14
            }

            if (isValidGame) {
                validGameSetIds.add(gameId!!.toInt())
            }
        }

        return validGameSetIds.sum()
    }

    fun task2(): Int {
        val inputStream: InputStream = File(filePath).inputStream()
        val gameIdRegex = "Game (\\d+)".toRegex();
        val gameSetsRegex = "Game \\d+: (.*)".toRegex()

        val minGamePowerSets = mutableListOf<Int>()

        inputStream.bufferedReader().forEachLine { line ->
            val gameId = gameIdRegex.find(line)?.groupValues?.get(1)

            val gameMaps: List<Map<String, Int>> =
                gameSetsRegex.find(line)?.groupValues?.get(1)?.split(";")?.map { set ->
                    val map = mutableMapOf<String, Int>("red" to 0, "green" to 0, "blue" to 0)

                    set.split(",").forEach { colorSet ->
                        val (count, color) = colorSet.trim().split(" ")
                        map[color] = map[color]!! + count.toInt()
                    }

                    map
                } ?: emptyList()

            val minGamePowerMap = mutableMapOf<String, Int>("red" to 0, "green" to 0, "blue" to 0)

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
    val day2 = Day2()

    println("task1: ${day2.task1()}") // 2545
    println("task2: ${day2.task2()}") // 78111
}