/***
 * Nishant Rengasamy
 * vmnishant21@gmail.com
 */

class Customer {
    val name: String
    var numCustomers: Int

    constructor(numCustomers: Int) {
        this.name = "John Doe"
        this.numCustomers = numCustomers
    }

    constructor(name: String, numCustomers: Int) {
        this.name = name
        this.numCustomers = numCustomers
    }

    override fun toString(): String {
        return "$numCustomers people, under $name"
    }
}