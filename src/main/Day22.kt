class Day22(input: List<String>) {

    private val cubes: List<Cuboid> = input.map { Cuboid.of(it) }
    private val part1Cube = Cuboid(true, -50..50, -50..50, -50..50)

    private class Cuboid(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange) {
        companion object {
            private val pattern =
                """^(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)$""".toRegex()

            fun of(input: String): Cuboid {
                val (s, x1, x2, y1, y2, z1, z2) = pattern.matchEntire(input)?.destructured
                    ?: error("Cannot parse input: $input")
                return Cuboid(
                    s == "on",
                    x1.toInt()..x2.toInt(),
                    y1.toInt()..y2.toInt(),
                    z1.toInt()..z2.toInt(),
                )
            }

        }
        fun volume(): Long =
            (x.size().toLong() * y.size().toLong() * z.size().toLong()) * if (on) 1 else -1

        fun intersects(other: Cuboid): Boolean =
            x.intersects(other.x) && y.intersects(other.y) && z.intersects(other.z)

        fun intersect(other: Cuboid): Cuboid? =
            if (!intersects(other)) null
            else Cuboid(!on, x intersect other.x, y intersect other.y, z intersect other.z)

    }

    private fun solve(cubesToUse: List<Cuboid> = cubes): Long {
        val volumes = mutableListOf<Cuboid>()

        cubesToUse.forEach { cube ->
            volumes.addAll(volumes.mapNotNull { it.intersect(cube) })
            if (cube.on) volumes.add(cube)
        }

        return volumes.sumOf { it.volume() }
    }

    fun solvePart1(): Long =
        solve(cubes.filter { it.intersects(part1Cube) })

    fun solvePart2(): Long =
        solve()
}

fun main() {
    measureTimeMillisPrint {
        val d = Day22(readInput("Day22"))
        println("Part 1 : " + d.solvePart1())
        println("Part 2 : " + d.solvePart2())
    }
}