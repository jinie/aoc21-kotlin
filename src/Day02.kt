import kotlin.math.abs

fun main() {
    fun part1(input: List<List<String>>): List<Int> {
        var pos = 0
        var depth=0
        var aim = 0
        var depth2 = 0
        for (p in input.iterator()){
            when(p[0]){
                "forward" -> {
                    pos += p[1].toInt()
                    depth2 += p[1].toInt() * aim
                }
                "down" -> {
                    depth -= p[1].toInt()
                    aim += p[1].toInt()
                }
                "up" -> {
                    depth += p[1].toInt()
                    aim -= p[1].toInt()
                }
            }
        }
        return listOf( abs(pos * depth), abs(pos*depth2))
    }
    
    val input = readInput("Day02").map{ it.trim().split(" ") }
    println(part1(input))
}
