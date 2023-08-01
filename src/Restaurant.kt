/***
 * Nishant Rengasamy
 * vmnishant21@gmail.com
 */

import java.time.Duration
import java.util.*

class Restaurant {
    private val currTableCustomer: HashMap<Tables, Customer?> = HashMap()
    private val waitList: LinkedList<Customer> = LinkedList()

    fun displayTableStatus() {
        println("--- Table Status ---")
        for (table in currTableCustomer.keys) {
            val customer = currTableCustomer[table]
            println(
                "Table of ${table.capacity} has $customer with - %02d:%02d remaining".format(
                    table.timeLeft.toMinutes(),
                    table.timeLeft.seconds % 60
                )
            )
        }
        println("--------------------")
    }

    fun displayWaitlist() {
        println("--- Waitlist ---")
        for (customer in waitList) {
            println(customer)
        }
        println("----------------")
    }

    fun addTable(table: Tables) {
        currTableCustomer[table] = null
    }

    fun queueCustomer(customer: Customer) {
        if (findAvailableTable(customer) != null) {
            seatCustomer(customer)
            return
        }
        waitList.addLast(customer)

    }

    private fun findAvailableTable(customer: Customer): Tables? {
        val availableTables = currTableCustomer.keys.filter { table ->
            currTableCustomer[table] == null && table.capacity >= customer.numCustomers
        }
        return availableTables.minByOrNull { it.capacity }
    }

    private fun seatCustomer(customer: Customer) {
        val table = findAvailableTable(customer)
        if (table == null) {
            println("No table available currently")
            return
        }
        currTableCustomer[table] = customer
        startTimerForTable(table)
    }

    private fun startTimerForTable(table: Tables) {
        var timer: Timer? = table.getTimer()
        timer?.cancel()
        table.resetTimer()
        val run: TimerTask = object : TimerTask() {
            override fun run() {
                table.decreaseTimer(Duration.ofSeconds(1))
                if (table.timeLeft.isZero()) {
                    println("Time is up for table of " + table.capacity)
                    table.cancelTimer()
                    releaseTable(table)
                    displayTableStatus()
                    displayWaitlist()
                }
            }
        }
        timer = Timer()
        table.setTimer(timer)
        timer.schedule(run, 0, 1000)
    }

    private fun releaseTable(table: Tables) {
        currTableCustomer[table] = null
        val availableCustomer = waitList.firstOrNull { findAvailableTable(it) != null }
        if (availableCustomer != null) {
            seatCustomer(availableCustomer)
            waitList.remove(availableCustomer)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val restaurant = RestaurantV2()

            // Adding tables to the restaurant
            val table1 = Tables(4, 0, 45)
            val table2 = Tables(6, 1, 20)
            val table3 = Tables(8, 1, 50)
            restaurant.addTable(table1)
            restaurant.addTable(table2)
            restaurant.addTable(table3)

            // Queueing customers
            val customer1 = Customer("John", 3)
            val customer2 = Customer("Emma", 3)
            val customer3 = Customer("Michael", 5)
            val customer4 = Customer("Sophia", 6)
            val customer5 = Customer("Nishant", 1)
            restaurant.queueCustomer(customer1)
            restaurant.queueCustomer(customer2)
            restaurant.queueCustomer(customer3)
            restaurant.queueCustomer(customer4)
            restaurant.queueCustomer(customer5)

            // Displaying table status and waitlist
            restaurant.displayTableStatus()
            restaurant.displayWaitlist()
        }
    }
}