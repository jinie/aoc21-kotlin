fun main() {
    val bracePairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val illegalChars = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val multipliers = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            with(ArrayDeque<Char>()) {
                line.sumOf { c ->
                    when {
                        c in bracePairs.keys -> (0).also { addLast(c) }
                        bracePairs[removeLast()] != c -> illegalChars.getValue(c)
                        else -> 0
                    }
                }
            }
        }
    }

    fun part2(input: List<String>): Long {
        return input.map { l ->
            with(ArrayDeque<Char>()) {
                l.forEach { c ->
                    when {
                        (c in bracePairs.keys) -> (0).also { addLast(c) }
                        bracePairs[removeLast()] != c -> return@map null
                    }
                }
                reversed().map { bracePairs.getValue(it) }.fold(0L) { acc, i -> (acc * 5) + multipliers.getValue(i) }
            }
        }.filterNotNull().sorted().let { it[it.size / 2] }
    }

    val testInput = readInput("Day10_test").map { it.trim() }
    check(part1(testInput) == 26397)

    measureTimeMillisPrint {
        val input = readInput("Day10").map { it.trim() }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }
}
