fun main() {
    fun fold(grid: Set<Point2d>, folds: List<Pair<String, Int>>): Set<Point2d> {
        return folds.fold(grid) { pt, i ->
            val (axis, pos) = i
            when (axis) {
                "y" -> pt.map { if (it.y > pos) Point2d(it.x, 2 * pos - it.y) else it }.toSet()
                else -> pt.map { if (it.x > pos) Point2d(2 * pos - it.x, it.y) else it }.toSet()
            }
        }
    }

    fun draw(input: Set<Point2d>) {
        println((0..input.maxOf { it.y }).map{ y ->
            (0..input.maxOf { it.x }).map{ x ->
                when(input.contains(Point2d(x,y))){
                    true -> "█"
                    false -> " "
                }
            }.joinToString("","","\n")
        }.joinToString(""))
    }

    measureTimeMillisPrint {
        val input = readInput("Day13").map { it.trim() }
        val grid = input.asSequence().filterNot { s -> s.contains("=") }.filter { it.isNotEmpty() }.map { it.split(",") }
                .map { p -> Point2d(p.first().toInt(), p.last().toInt()) }.toSet()
        val folds = input.filter { s -> s.contains("=") }.map { it.split("=") }
            .map { p -> p.first().split(" ").last() to p.last().toInt() }
        println("Part 1 : " + fold(grid, folds.take(1)).size)
        draw(fold(grid, folds))
    }
}
