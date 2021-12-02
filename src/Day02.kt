import kotlin.math.abs

fun main() {
    data class ins(val key: String, val inc: Int)

    fun part1(input: List<ins>): List<Int> {
        /* Part 1 */
        var pos = 0
        var depth=0
        /* Part 2 */
        var aim = 0
        var depth2 = 0
        for (p in input.iterator()){
            when(p.key){
                "forward" -> {
                    pos += p.inc
                    depth2 += p.inc * aim //Part 2
                }
                "down" -> {
                    depth -= p.inc
                    aim += p.inc //Part 2
                }
                "up" -> {
                    depth += p.inc
                    aim -= p.inc // Part 2
                }
                else -> throw IllegalStateException("$p.key is not valid direction")
            }
        }
        return listOf( abs(pos * depth), abs(pos*depth2))
    }

    val input = readInput("Day02").map{
        val (key, inc) = it.trim().split(" ")
        ins(key,inc.toInt())
    }
    println(part1(input))
}
