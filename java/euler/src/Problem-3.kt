/**
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * What is the largest prime factor of the number 600851475143 ?
 */

fun largePrimeFactor(number: Long): Long {

    val lowPrimeFactor = LongRange(2, number).first { i -> number % i == 0L }

    return if (number != lowPrimeFactor)
        largePrimeFactor(number / lowPrimeFactor)
    else lowPrimeFactor
}

fun main(args: Array<String>) {
    val number = 600851475143
    println(largePrimeFactor(number))
}