package adventofcode2023.day12

import java.io.File

class Task1(val filePath: String = "src/main/kotlin/day12/input.txt") {
    fun solve(): Int {
        val lines = File(filePath).readLines()

        val records = lines.map { line ->
            val (springs, numStr) = line.split(" ")
            val nums = numStr.split(",").map { it.toInt() }

            Pair(springs, nums)
        }

        val permutations = records.map { count(it.first, it.second) }

        return permutations.sum()
    }

    private fun count(str: String, nums: List<Int>): Int {
        if (str == "") {
            return when {
                nums.isEmpty() -> 1
                else -> 0
            }
        }

        if (nums.isEmpty()) {
            return when {
                str.contains("#") -> 0
                else -> 1
            }
        }

        var result = 0

        if (str[0] in setOf('.', '?')) {
            result += count(str.drop(1), nums)
        }

        if (str[0] in setOf('#', '?')) {
            if (nums[0] <= str.length && !str.take(nums[0])
                    .contains(".") && (nums[0] == str.length || (nums[0] < str.length && str[nums[0]] != '#'))
            ) {
                result += count(str.drop(nums[0] + 1), nums.drop(1))
            }
        }

        return result
    }
}

fun main() {
    val test1 = Task1("src/main/kotlin/day12/input2.txt")
    println("task1 test: ${test1.solve()}") // 21

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 6852
}
