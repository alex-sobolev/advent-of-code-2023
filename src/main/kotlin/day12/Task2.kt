package adventofcode2023.day12

import java.io.File

class Task2(val filePath: String = "src/main/kotlin/day12/input.txt") {
    fun solve(): Long {
        val lines = File(filePath).readLines()

        val records = lines.map { line ->
            val (springs, numStr) = line.split(" ")
            val unfoldedSprings = repeatString(springs, 5, separator = "?")
            val unfoldedNums = repeatString(numStr, 5, separator = ",")
            val nums = unfoldedNums.split(",").map { it.toInt() }

            Pair(unfoldedSprings, nums)
        }

        val memo = mutableMapOf<String, Long>()
        var result = 0.toLong()

        records.forEach {
            result += count(it.first, it.second, memo)
        }

        return result
    }

    private fun repeatString(s: String, n: Int, separator: String = ","): String {
        return List(n) { s }.joinToString(separator)
    }

    private fun count(str: String, nums: List<Int>, memo: MutableMap<String, Long>): Long {
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

        val key = "$str-$nums"

        if (memo.containsKey(key)) {
            return memo[key]!!
        }

        var result = 0.toLong()

        if (str[0] in setOf('.', '?')) {
            result += count(str.drop(1), nums, memo)
        }

        if (str[0] in setOf('#', '?')) {
            if (nums[0] <= str.length && !str.take(nums[0])
                    .contains(".") && (nums[0] == str.length || (nums[0] < str.length && str[nums[0]] != '#'))
            ) {
                result += count(str.drop(nums[0] + 1), nums.drop(1), memo)
            }
        }

        memo[key] = result

        return result
    }
}

fun main() {
    val test2 = Task2("src/main/kotlin/day12/input2.txt")
    println("task2 test: ${test2.solve()}") // 525152

    val task2 = Task2()
    println("task 2: ${task2.solve()}") // 8475948826693
}
