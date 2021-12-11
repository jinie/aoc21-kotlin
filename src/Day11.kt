fun main() {

    class Octopi(val values: Array<Array<Int>>) {
        fun step() {
            values.forEachIndexed { y, row ->
                row.forEachIndexed { x, _ ->
                    values[y][x]++
                }
            }
        }

        fun flash(): Int {
            val visited = mutableSetOf<Pair<Int, Int>>()
            values.forEachIndexed { y, row ->
                row.forEachIndexed { x, energy ->
                    if (energy > 9 && (y to x) !in visited) {
                        val toVisit = ArrayDeque(listOf(y to x))
                        visited.add(y to x)
                        while (toVisit.isNotEmpty()) {
                            val (nextY, nextX) = toVisit.removeLast()
                            neighbours(nextY, nextX)
                                .filter { it !in visited }
                                .filter { (y, x) -> ++values[y][x] > 9 }
                                .forEach { (y, x) ->
                                    toVisit.addLast(y to x)
                                    visited.add(y to x)
                                }
                        }
                    }
                }
            }
            return visited.size
        }

        fun reset() {
            values.forEachIndexed { y, row ->
                row.forEachIndexed { x, _ ->
                    values[y][x] = if (values[y][x] > 9) 0 else values[y][x]
                }
            }
        }

        fun notSynchronized() = values.any { row -> row.any { it != 0 } }

        private fun neighbours(y: Int, x: Int): List<Pair<Int, Int>> {
            return (-1..1).flatMap { dy -> (-1..1).map { dx -> dy to dx } }
                .filterNot { (dy, dx) -> dy == 0 && dx == 0 }
                .map { (dy, dx) -> y + dy to x + dx }
                .filter { (y, x) -> y in values.indices && x in values.first().indices }
        }
    }

    fun sequenceOfFlashes(octopi: Octopi) = generateSequence {
        octopi.step()
        octopi.flash().also { octopi.reset() }
    }


    fun part1(octopi: Octopi): Int {
        return sequenceOfFlashes(octopi)
            .take(100)
            .sum()
    }

    fun part2(octopi: Octopi): Int {
        return sequenceOfFlashes(octopi)
            .takeWhile { octopi.notSynchronized() }
            .count() + 1
    }


    val testInput = readInput("Day11_test").map { row -> row.toCharArray().map { it.digitToInt() }.toTypedArray() }.toTypedArray().let { Octopi(it) }
    //check(part1(testInput) == 1656)

    measureTimeMillisPrint {
        val octopi = readInput("Day11").map { row -> row.toCharArray().map { it.digitToInt() }.toTypedArray() }.toTypedArray().let { Octopi(it) }
        println("Part 1 : " + part1(octopi))
        println("Part 2 : " + part2(octopi))
    }

}
