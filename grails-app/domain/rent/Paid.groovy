package rent

class Paid {
    Integer paid
    Date receipt
    Date month
    Integer rent
    Bill electricity
    Bill water
    Integer balance
    
    static belongsTo = [place:Place]
    static embedded = ['electricity', 'water']
    
    static constraints = {
        paid()
        place()
        receipt()
        month()
        rent(blank:true, nullable:true)
        electricity(blank:true, nullable:true)
        water(blank:true, nullable:true)
        balance(blank:true, nullable:true)
    }
    String toString() { "$month $balance" }
}

class Bill {
    Integer previous
    Integer current
    Integer rate
    static constraints = {
        previous(blank:true, nullable:true)
        current(blank:true, nullable:true)
        rate(blank:true, nullable:true)
    }
    String toString() { 
        def bill = rate * (current-previous)
        "$bill" 
    }
}