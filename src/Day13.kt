fun main() {
    fun fold(grid: Set<Point2d>, folds: List<Pair<String, Int>>): Set<Point2d> {
        return folds.fold(grid) { pt, i ->
            val (axis, pos) = i
            when (axis) {
                "y" ->
                    pt
                        .filter { it.y > pos }
                        .map { Point2d(it.x, 2 * pos - it.y) }
                        .toSet() + pt.filter { it.y < pos }
                else -> pt
                    .filter { it.x > pos }
                    .map { Point2d(2 * pos - it.x, it.y) }
                    .toSet() + pt.filter { it.x < pos }
            }
        }
    }

    fun draw(input: Set<Point2d>) {
        for (y in 0..input.maxOf { it.y }) {
            for (x in 0..input.maxOf { it.x }) {
                when (input.contains(Point2d(x, y))) {
                    true -> print("#")
                    false -> print(" ")
                }
            }
            println()
        }
    }

    measureTimeMillisPrint {
        val input = readInput("Day13").map { it.trim() }
        val input1 =
            input.asSequence().filterNot { s -> s.contains("=") }.filter { it.isNotEmpty() }.map { it.split(",") }
                .map { p -> Point2d(p.first().toInt(), p.last().toInt()) }.toSet()
        val input2 = input.filter { s -> s.contains("=") }.map { it.split("=") }
            .map { p -> p.first().split(" ").last() to p.last().toInt() }
        println("Part 1 : " + fold(input1, input2.take(1)).size)
        draw(fold(input1, input2))
    }

}
