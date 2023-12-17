package adventofcode2023.day5

import java.io.File

class Task1(private val filePath: String = "src/main/kotlin/day5/input.txt") {
    private data class Block(val source: Long, val target: Long, val range: Long)

    private data class SeedsMap(val from: String, val to: String, val blocks: List<Block>)

    private data class Seed(
        val seed: Long,
        val soil: Long,
        val fertilizer: Long,
        val water: Long,
        val light: Long,
        val temperature: Long,
        val humidity: Long,
        val location: Long
    )

    fun solve(): Long {
        val input = File(filePath).readText()

        val seeds = input.substringAfter("seeds: ").substringBefore("\n\n").split(" ").map { it.toLong() }

        fun blockMapper(input: String): Block {
            val str = input.split(" ")
            val target = str[0].toLong()
            val source = str[1].toLong()
            val range = str[2].toLong()

            return Block(source, target, range)
        }

        fun createSeedsMap(input: String, mapName: String, source: String, target: String): SeedsMap {
            val mapData = input.substringAfter("$mapName map:\n").substringBefore("\n\n").split("\n").map(::blockMapper)
            return SeedsMap(source, target, mapData)
        }

        val seedToSoilMap = createSeedsMap(input, "seed-to-soil", "seed", "soil")
        val soilToFertilizerMap = createSeedsMap(input, "soil-to-fertilizer", "soil", "fertilizer")
        val fertilizerToWaterMap = createSeedsMap(input, "fertilizer-to-water", "fertilizer", "water")
        val waterToLightMap = createSeedsMap(input, "water-to-light", "water", "light")
        val lightToTemperatureMap = createSeedsMap(input, "light-to-temperature", "light", "temperature")
        val temperatureToHumidityMap = createSeedsMap(input, "temperature-to-humidity", "temperature", "humidity")
        val humidityToLocationMap = createSeedsMap(input, "humidity-to-location", "humidity", "location")

        val combinedSeedsMap = mutableListOf<Seed>()

        fun transform(src: Long, map: SeedsMap): Long {
            val block = map.blocks.find { block -> src in block.source..block.source + block.range }
            return if (block != null) block.target + (src - block.source) else src
        }

        for (seed in seeds) {
            val soil = transform(seed, seedToSoilMap)
            val fertilizer = transform(soil, soilToFertilizerMap)
            val water = transform(fertilizer, fertilizerToWaterMap)
            val light = transform(water, waterToLightMap)
            val temperature = transform(light, lightToTemperatureMap)
            val humidity = transform(temperature, temperatureToHumidityMap)
            val location = transform(humidity, humidityToLocationMap)

            combinedSeedsMap.add(Seed(seed, soil, fertilizer, water, light, temperature, humidity, location))
        }

        return combinedSeedsMap.minBy { it.location }!!.location
    }

}

fun main() {
    val test = Task1("src/main/kotlin/day5/input2.txt")
    println("task1 test: ${test.solve()}") // 35

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 457535844
}
