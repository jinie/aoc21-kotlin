import java.awt.Point
import kotlin.math.abs
import kotlin.math.max

fun main() {
    /* Return list of all points on line specified by x,y*/
    fun findPointsForLine(x: Point, y: Point): List<Point> {
        return (0..max(abs(x.x - y.x), abs(x.y - y.y))).map { step ->
            val xx = if (y.x > x.x) x.x + step
            else if (y.x < x.x) x.x - step
            else x.x
            val yy = if (y.y > x.y) x.y + step
            else if (y.y < x.y) x.y - step
            else x.y
            Point(xx, yy)
        }
    }

    fun parseInput(fname: String): List<List<Point>> {
        val input = readInput(fname)
        return input.map {
            it.split(" -> ")
                .map {
                    it.split(",")
                        .let { (x, y) ->
                            Point(x.toInt(), y.toInt())
                        }
                }
        }
    }

    fun part1(fname: String): Int {
        return parseInput(fname)
            .filter { (x, y) -> x.x == y.x || x.y == y.y } // For now, only consider horizontal and vertical lines: lines where either x1 = x2 or y1 = y2.
            .flatMap { (x, y) -> findPointsForLine(x, y) }
            .groupBy { it }
            .count { it.value.size > 1 }
    }

    fun part2(fname: String): Int {
        return parseInput(fname)
            .flatMap { (x, y) -> findPointsForLine(x, y) }
            .groupBy { it }
            .count { it.value.size > 1 }
    }


    // test if implementation meets criteria from the description, like:
    check(part1("Day05_test") == 5)
    measureTimeMillis({ println(" Time Taken $it ms")}) {
        println("Part 1 : " + part1("Day05"))
        println("Part 2 : " + part2("Day05"))
    }
}
