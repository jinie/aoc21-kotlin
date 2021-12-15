import java.math.BigInteger

fun main() {
    fun part1(ndays: Int, input: List<Int>): BigInteger {
        val fish = generateSequence { 0 }.take(9).map { it.toBigInteger() }.toMutableList()

        for (i in input.groupingBy { it }.eachCount().entries) fish[i.key] = i.value.toBigInteger()

        for (i in 0 until ndays) {
            val tfish = fish.removeFirst()
            fish.add(tfish)
            fish[6] += tfish
        }

        return fish.sumOf { it }
    }

    val testInput = "3,4,3,1,2".split(",").map { it.toInt() }
    check(part1(80, testInput) == 5934.toBigInteger())

    measureTimeMillisPrint {
        val input = readInputToIntList("Day06")
        println("Part 1 : " + part1(80, input))
        println("Part 2 : " + part1(256, input))
    }
}
