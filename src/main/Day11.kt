typealias OctopusCave = Map<Point2d, Int>

fun OctopusCave.steps(): Sequence<Int> = sequence {
    val cave = this@steps.toMutableMap()

    while (true) {

        cave.forEach { (point, energy) -> cave[point] = energy + 1 }
        val flashersThisStep = mutableSetOf<Point2d>()

        do {
            val flashersThisRound = cave.filter { it.value > 9 && it.key !in flashersThisStep }.keys
            flashersThisStep.addAll(flashersThisRound)

            val neighborsOfFlashers = flashersThisRound
                .flatMap { it.allNeighbors() }
                .filter { it in cave && it !in flashersThisStep }

            neighborsOfFlashers.forEach { cave[it] = cave.getValue(it) + 1 }
        } while (flashersThisRound.isNotEmpty())

        flashersThisStep.forEach { cave[it] = 0 }

        yield(flashersThisStep.size)
    }
}

class Day11(input: List<String>) {
    private val startingCave: OctopusCave = parseInput(input)

    private fun parseInput(input: List<String>): OctopusCave =
        input.flatMapIndexed { y, row ->
            row.mapIndexed { x, energy -> Point2d(x, y) to energy.digitToInt() }
        }.toMap()

    fun solvePart1(): Int =
        startingCave.steps().take(100).sum()

    fun solvePart2(): Int =
        startingCave.steps().takeWhile { it != startingCave.size }.count() + 1
}

fun main() {
    measureTimeMillisPrint {
        val solver = Day11(readInput("Day11"))
        println("Part 1 : " + solver.solvePart1())
        println("Part 2 : " + solver.solvePart2())
    }

}


