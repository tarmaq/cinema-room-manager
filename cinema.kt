package cinema

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()
    val cinema = MutableList(rows) { MutableList(seats) { 'S' } }
    displayMenu(cinema)
}

fun displayMenu(cinema: MutableList<MutableList<Char>>) {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    when (readLine()!!.toInt()) {
        1 -> printCinema(cinema)
        2 -> buyATicket(cinema)
        3 -> printStatistics(cinema)
        0 -> return
    }
    displayMenu(cinema)
}
fun printStatistics(cinema: MutableList<MutableList<Char>>) {
    var totalTickets = 0
    var income = 0
    var totalIncome = 0
    for (rowNo in cinema.indices) {
        val currentPrice = price(cinema, rowNo)
        for (seatNo in cinema[rowNo].indices) {
            if (cinema[rowNo][seatNo] == 'B') {
                totalTickets++
                income += currentPrice
            }
        }
        totalIncome += currentPrice * cinema[rowNo].size
    }
    val percentage = totalTickets.toDouble() / cinema.size / cinema[0].size * 100
    val percentageFormatted = "%.2f".format(percentage)
    println()
    println("Number of purchased tickets: $totalTickets")
    println("Percentage: $percentageFormatted%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
}

fun price(cinema: MutableList<MutableList<Char>>, row: Int): Int {
    val rows = cinema.size
    val seats = cinema[0].size
    return if (rows * seats <= 60) {
        10
    } else if (row <= rows / 2 - 1) {
        10
    } else 8
}

fun buyATicket(cinema: MutableList<MutableList<Char>>) {
    println()
    println("Enter a row number:")
    val row = readLine()!!.toInt()
    println("Enter a seat number in that row:")
    val seat = readLine()!!.toInt()
    try {
        if (row > cinema.size || seat > cinema[0].size) {
            throw Exception("Wrong input!")
        }
        if (cinema[row - 1][seat - 1] == 'B') {
            throw Exception("That ticket has already been purchased!")
        }
        cinema[row - 1][seat - 1] = 'B'
        println("Ticket price: $${price(cinema, row - 1)}")
    } catch (e: Exception) {
        println()
        println(e.message)
        buyATicket(cinema)
    }
}

fun printCinema(cinema: MutableList<MutableList<Char>>) {
    println()
    println("Cinema:")
    for (rowNo in cinema.indices) {
        if (rowNo == 0) {
            print("  ")
            print((cinema[0].indices).map { it + 1 }.joinToString(" "))
            println()
        }
        print("${rowNo + 1} ")
        for (seatNo in cinema[rowNo].indices) {
            print(cinema[rowNo][seatNo])
            if (seatNo != cinema[rowNo].lastIndex) print(" ")
        }
        println()
    }
}
