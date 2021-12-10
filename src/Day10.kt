fun main() {
    var openingBraces = listOf('(', '[', '{', '<')
    var bracePairs = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    val illegalChars = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val multipliers = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

    fun checkLine(input: String): Long {
        var stack = mutableListOf<Char>()
        return input.map { c ->
            if (c in openingBraces) {
                stack.add(c)
                0L
            } else {
                val cl = stack.removeLast()
                if (bracePairs[c] != cl) {
                    illegalChars[c]!!.toLong()
                }else {
                    0L
                }
            }
        }.reduce{acc, i -> acc+i}
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { checkLine(it) }
    }

    fun part2(input: List<String>): Long {
        val scores = input.filterNot { checkLine(it) > 0 }.map { l ->
            var stack = mutableListOf<Char>()
            l.forEach { c ->
                if (c in openingBraces) stack.add(c)
                else if (stack.last() == bracePairs[c]) stack.removeLast()
            }
            stack.reversed().map { multipliers[it]!!.toLong() }.reduce { acc, i -> ((acc * 5) + i) }
        }.sorted()
        return scores[scores.size / 2]
    }

    val testInput = readInput("Day10_test").map { it.trim() }
    check(part1(testInput) == 26397L)

    measureTimeMillisPrint {
        val input = readInput("Day10").map { it.trim() }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }
}
