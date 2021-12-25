class Day24(input: List<String>) {

    private val magicParameters: List<Parameters> = parseMagicParameters(input)

    private class Parameters(val a: Int, val b: Int, val c: Int)

    private fun parseMagicParameters(input: List<String>): List<Parameters> =
        input.chunked(18).map {
            Parameters(
                it[4].substringAfterLast(" ").toInt(),
                it[5].substringAfterLast(" ").toInt(),
                it[15].substringAfterLast(" ").toInt()
            )
        }

    private fun magicFunction(parameters: Parameters, z: Long, w: Long): Long =
        if (z % 26 + parameters.b != w) ((z / parameters.a) * 26) + w + parameters.c
        else z / parameters.a

    private fun solve(): Pair<Long, Long> {
        var zValues = mutableMapOf(0L to (0L to 0L))
        magicParameters.forEach { parameters ->
            val zValuesThisRound = mutableMapOf<Long, Pair<Long, Long>>()
            zValues.forEach { (z, minMax) ->
                (1..9).forEach { digit ->
                    val newValueForZ = magicFunction(parameters, z, digit.toLong())
                    if (parameters.a == 1 || (parameters.a == 26 && newValueForZ < z)) {
                        zValuesThisRound[newValueForZ] =
                            minOf(zValuesThisRound[newValueForZ]?.first ?: Long.MAX_VALUE, minMax.first * 10 + digit) to
                                    maxOf(
                                        zValuesThisRound[newValueForZ]?.second ?: Long.MIN_VALUE,
                                        minMax.second * 10 + digit
                                    )
                    }
                }
            }
            zValues = zValuesThisRound
        }
        return zValues.getValue(0)
    }

    fun solvePart1(): Long = solve().second

    fun solvePart2(): Long = solve().first

}

fun main() {
    measureTimeMillisPrint {
        val d = Day24(readInput("Day24"))
        println("Part 1 : " + d.solvePart1())
        println("Part 2 : " + d.solvePart2())
    }
}