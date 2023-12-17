package adventofcode2023.day5

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day5/input.txt") {
    private data class Block(val source: Long, val target: Long, val range: Long)

    private data class SeedsMap(val from: String, val to: String, val blocks: List<Block>)

    fun solve(): Long {
        val input = File(filePath).readText()

        fun blockMapper(input: String): Block {
            val str = input.split(" ")
            val target = str[0].toLong()
            val source = str[1].toLong()
            val range = str[2].toLong()

            return Block(source, target, range)
        }

        fun createSeedsMap(input: String, mapName: String, source: String, target: String): SeedsMap {
            val mapData = input
                .substringAfter("$mapName map:\n")
                .substringBefore("\n\n")
                .split("\n")
                .map(::blockMapper)

            return SeedsMap(source, target, mapData)
        }

        val seeds = input.substringAfter("seeds: ").substringBefore("\n\n").split(" ").map { it.toLong() }

        val seedToSoilMap = createSeedsMap(input, "seed-to-soil", "seed", "soil")
        val soilToFertilizerMap = createSeedsMap(input, "soil-to-fertilizer", "soil", "fertilizer")
        val fertilizerToWaterMap = createSeedsMap(input, "fertilizer-to-water", "fertilizer", "water")
        val waterToLightMap = createSeedsMap(input, "water-to-light", "water", "light")
        val lightToTemperatureMap = createSeedsMap(input, "light-to-temperature", "light", "temperature")
        val temperatureToHumidityMap = createSeedsMap(input, "temperature-to-humidity", "temperature", "humidity")
        val humidityToLocationMap = createSeedsMap(input, "humidity-to-location", "humidity", "location")

        fun transform(src: Long, map: SeedsMap): Long {
            val block = map.blocks.find { block -> src in block.source..block.source + block.range }
            return if (block != null) block.target + (src - block.source) else src
        }

        var min: Long = Long.MAX_VALUE

        for (index in seeds.indices step 2) {
            for (seed in seeds[index]..seeds[index] + seeds[index + 1]) {
                val soil = transform(seed, seedToSoilMap)
                val fertilizer = transform(soil, soilToFertilizerMap)
                val water = transform(fertilizer, fertilizerToWaterMap)
                val light = transform(water, waterToLightMap)
                val temperature = transform(light, lightToTemperatureMap)
                val humidity = transform(temperature, temperatureToHumidityMap)
                val location = transform(humidity, humidityToLocationMap)

                if (location < min) {
                    min = location
                }
            }
        }

        return min
    }
}

fun main() {
    val test = Task2("src/main/kotlin/day5/input2.txt")
    println("task2 test: ${test.solve()}") // 46

    val task2 = Task2()
    // pretty long ranges, so it takes a while to compute (approx. 2 min on my machine)
    println("task 2: ${task2.solve()}") // 41222968
}
