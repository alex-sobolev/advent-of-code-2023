package adventofcode2023.day3

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day3/input.txt") {
    fun solution(): Int {
        val input = File(filePath).readText()
        val validNums = mutableListOf<Int>()
        val lines = input.split("\n")

        for ((index1, line) in lines.withIndex()) {
            var index2 = 0

            while (index2 < line.length) {
                val char = line[index2]

                if (char == '*') {
                    val leftNum = if (index2 > 0) {
                        var start = index2 - 1
                        var strNum = "" // build string from right to left

                        while (start >= 0 && line[start].isDigit()) {
                            strNum = line[start] + strNum
                            start--
                        }

                        if (strNum.isNotEmpty()) strNum.toInt() else null
                    } else null

                    val rightNum = if (index2 < line.length - 1) {
                        var end = index2 + 1
                        var strNum = "" // build string from left to right

                        while (end < line.length && line[end].isDigit()) {
                            strNum += line[end]
                            end++
                        }

                        if (strNum.isNotEmpty()) strNum.toInt() else null
                    } else null

                    val topNum = if (index1 > 0 && lines[index1 - 1][index2].isDigit()) {
                        var start = index2
                        var end = index2 + 1
                        var strNum = ""

                        // go up->left
                        while (start >= 0 && lines[index1 - 1][start].isDigit()) {
                            strNum = lines[index1 - 1][start] + strNum
                            start--
                        }

                        // go up->right
                        while (end < line.length && lines[index1 - 1][end].isDigit()) {
                            strNum += lines[index1 - 1][end]
                            end++
                        }

                        if (strNum.isNotEmpty()) strNum.toInt() else null
                    } else null

                    val bottomNum = if (index1 < lines.size - 1 && lines[index1 + 1][index2].isDigit()) {
                        var start = index2
                        var end = index2 + 1
                        var strNum = ""

                        // go down->left
                        while (start >= 0 && lines[index1 + 1][start].isDigit()) {
                            strNum = lines[index1 + 1][start] + strNum
                            start--
                        }

                        // go down->right
                        while (end < line.length && lines[index1 + 1][end].isDigit()) {
                            strNum += lines[index1 + 1][end]
                            end++
                        }

                        if (strNum.isNotEmpty()) strNum.toInt() else null
                    } else null

                    val topDiagonalLeftNum = if (index1 > 0 && index2 > 0 && !lines[index1 - 1][index2].isDigit()) {
                        var start = index2 - 1
                        var strNum = ""

                        // go up->left
                        while (start >= 0 && lines[index1 - 1][start].isDigit()) {
                            strNum = lines[index1 - 1][start] + strNum
                            start--
                        }

                        if (strNum.isNotEmpty()) strNum.toInt() else null
                    } else null

                    val topDiagonalRightNum =
                        if (index1 > 0 && index2 < line.length - 1 && !lines[index1 - 1][index2].isDigit()) {
                            var end = index2 + 1
                            var strNum = ""

                            // go up->right
                            while (end < line.length && lines[index1 - 1][end].isDigit()) {
                                strNum += lines[index1 - 1][end]
                                end++
                            }

                            if (strNum.isNotEmpty()) strNum.toInt() else null
                        } else null

                    val bottomDiagonalLeftNum =
                        if (index1 < lines.size - 1 && index2 > 0 && !lines[index1 + 1][index2].isDigit()) {
                            var start = index2 - 1
                            var strNum = ""

                            // go down->left
                            while (start >= 0 && lines[index1 + 1][start].isDigit()) {
                                strNum = lines[index1 + 1][start] + strNum
                                start--
                            }

                            if (strNum.isNotEmpty()) strNum.toInt() else null
                        } else null

                    val bottomDiagonalRightNum =
                        if (index1 < lines.size - 1 && index2 < line.length - 1 && !lines[index1 + 1][index2].isDigit()) {
                            var end = index2 + 1
                            var strNum = ""

                            // go down->right
                            while (end < line.length && lines[index1 + 1][end].isDigit()) {
                                strNum += lines[index1 + 1][end]
                                end++
                            }

                            if (strNum.isNotEmpty()) strNum.toInt() else null
                        } else null

                    val nums = listOfNotNull(
                        leftNum,
                        rightNum,
                        topNum,
                        bottomNum,
                        topDiagonalLeftNum,
                        topDiagonalRightNum,
                        bottomDiagonalLeftNum,
                        bottomDiagonalRightNum
                    ).map { it.toInt() }

                    if (nums.size == 2) {
                        validNums.add(nums[0] * nums[1])
                    }
                }
                index2++
            }
        }

        return validNums.sum()
    }
}

fun main() {
    val test = Task2("src/main/kotlin/day3/input2.txt")
    println("test task1: ${test.solution()}") // 467835

    val task2 = Task2()
    println("day3 task1: ${task2.solution()}") // 76314915
}
