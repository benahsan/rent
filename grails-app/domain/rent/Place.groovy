package rent

class Place {
    String name
    Integer rent
    Integer balance
    Integer previousElectricity
    Integer previousWater

    static hasMany = [paid:Paid]

    static constraints = {
        name()
        rent()
        balance()
        previousElectricity()
        previousWater()
        paid()
    }

    String toString() { "$name" }
}
