package com.huma.adventofcode

import kotlin.test.assertEquals

fun getCoordinates(number: Int): Pair<Int, Int> {

    //println("")
    //println("Calculating coordinates for ${number}")

    fun maxRingValue(ringIndex: Int): Int = Math.pow((2 * ringIndex - 1).toDouble(), 2.toDouble()).toInt()

    val ring = IntProgression.fromClosedRange(1, 1000, 1)
            .first { index -> maxRingValue(index) >= number }

    //println("Ring is ${ring}")

    val range = Pair(maxRingValue(ring - 1) + 1, maxRingValue(ring))
    //println("Ring range is ${range}")

    val faceSize = ((range.second - range.first) + 1) / 4

    // 1 - right
    // 2 - top
    // 3 - left
    // 4 - bottom
    val faceOrientation = IntProgression.fromClosedRange(range.first, range.second, faceSize)
            .map { s -> Pair(s, (s + faceSize) - 1) }
            .mapIndexed { direction, pair -> Triple(direction + 1, pair.first, pair.second) }
            //.map { println("Face range ${it.first} is (${it.second}, ${it.third})"); it }
            .first { triple -> number >= triple.second && number <= triple.third }

    val offset = IntProgression.fromClosedRange(-1 * (faceSize / 2), (faceSize / 2) - 1, 1)
            .mapIndexed { index, i -> Pair(faceOrientation.second + index, i) }
            //.map { println("Points in face ${it}"); it }
            .first { it.first == number }
            .second + 1

    val coordinates = when (faceOrientation.first) {
        1 -> Pair(offset * -1, ring - 1)
        2 -> Pair((ring - 1) * -1, offset * -1)
        3 -> Pair(offset, (ring - 1) * -1)
        4 -> Pair(ring - 1, offset)
        else -> Pair(offset, ring - 1)
    }

    // println("coordinates for ${number} are ${coordinates}")

    return coordinates
}

fun main(args: Array<String>) {

    val input = 312051

    /**
     * --- Part One ---
     *
     *  You come across an experimental new kind of memory stored on an infinite two-dimensional grid.
     *
     *  Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up
     *  while spiraling outward. For example, the first few squares are allocated like this:
     *
     *  17  16  15  14  13
     *  18   5   4   3  12
     *  19   6   1   2  11
     *  20   7   8   9  10
     *  21  22  23---> ...
     *
     *  While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the
     *  location of the only access port for this memory system) by programs that can only move up, down, left, or right.
     *  They always take the shortest path: the Manhattan Distance between the location of the data and square 1.
     *
     *  For example:
     *
     *  Data from square 1 is carried 0 steps, since it's at the access port.
     *  Data from square 12 is carried 3 steps, such as: down, left, left.
     *  Data from square 23 is carried only 2 steps: up twice.
     *  Data from square 1024 must be carried 31 steps.
     *  How many steps are required to carry the data from the square identified in your puzzle input all the way to the
     *  access port?
     */

    fun calculateDistance(coordinate: Pair<Int, Int>): Int = (if (coordinate.first < 0) coordinate.first * -1 else coordinate.first) + (if (coordinate.second < 0) coordinate.second * -1 else coordinate.second)

    assertEquals(3, calculateDistance(getCoordinates(12)))
    assertEquals(3, calculateDistance(getCoordinates(20)))
    assertEquals(4, calculateDistance(getCoordinates(21)))
    assertEquals(2, calculateDistance(getCoordinates(23)))
    assertEquals(4, calculateDistance(getCoordinates(25)))
    assertEquals(6, calculateDistance(getCoordinates(49)))

    println("Distance is ${calculateDistance(getCoordinates(input))}")

    /**
     * --- Part Two ---
     *
     * As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in
     * the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.
     *
     * So, the first few squares' values are chosen as follows:
     *
     * Square 1 starts with the value 1.
     * Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
     * Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
     * Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
     * Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
     * Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:
     *
     * 147  142  133  122   59
     * 304    5    4    2   57
     * 330   10    1    1   54
     * 351   11   23   25   26
     * 362  747  806--->   ...
     *
     * What is the first value written that is larger than your puzzle input?
     */

    fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }

    fun getSumOfSurroudingValues(matrix: Array<IntArray>, coordinate: Pair<Int, Int>): Int {
        return IntRange(-1, 1)
                .flatMap { i -> IntRange(-1, 1).map { Pair(i, it) } }
                .map { p -> matrix[coordinate.first + p.first][coordinate.second + p.second] }
                .sum()
    }

    val size = 281

    val matrix = array2dOfInt(size, size)
    val center = Math.ceil(size / 2.toDouble()).toInt() - 1

    matrix[center][center] = 1

    IntRange(2, size - 2).first { i ->
        val coordinate = getCoordinates(i)
        val movedCoordinate = Pair(coordinate.first + center, coordinate.second + center)

        // println("${coordinate} -> ${movedCoordinate}")

        val sum = getSumOfSurroudingValues(matrix, movedCoordinate)
        matrix[movedCoordinate.first][movedCoordinate.second] = sum

        if (sum > input) {
            println("First value written that is larger than your puzzle input is ${sum}")
        }

        sum > input
    }

//    println(Arrays.deepToString(matrix)
//            .replace("[[", "[")
//            .replace("]]", "]")
//            .replace("], ", "]\n"))
}