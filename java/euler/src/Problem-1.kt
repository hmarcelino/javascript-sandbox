fun main(args: Array<String>) {

    var total = 0

    for (i in 1..999) {

        if ((i % 3 == 0) || (i % 5 == 0)) {
            total += i
        }
    }

    println(total)

}