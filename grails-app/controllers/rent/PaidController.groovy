package rent

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PaidController {

    PaidService paidService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond paidService.list(params), model:[paidCount: paidService.count()]
    }

    def show(Long id) {
        respond paidService.get(id)
    }

    def create() {
        respond new Paid(params)
    }

    def save(Paid paid) {
        if (paid == null) {
            notFound()
            return
        }

        try {
            paid.rent = paid.place.rent
            paid.electricity.previous = paid.place.previousElectricity
            paid.water.previous = paid.place.previousWater
            paid.balance = paid.place.balance + paid.paid - paid.rent - (paid.electricity.rate * (paid.electricity.current-paid.electricity.previous))-(paid.water.rate * (paid.water.current-paid.water.previous))
            paid.place.balance = paid.balance
            paid.place.previousElectricity = paid.electricity.current
            paid.place.previousWater = paid.water.current
            paidService.save(paid)
        } catch (ValidationException e) {
            respond paid.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'paid.label', default: 'Paid'), paid.id])
                redirect paid
            }
            '*' { respond paid, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond paidService.get(id)
    }

    def update(Paid paid) {
        if (paid == null) {
            notFound()
            return
        }

        try {
            // if looking at previous receipts, then don't update place's balance, previousElectricity and previousWater
            if (paid.place.previousElectricity > paid.electricity.current) paid.balance = paid.paid - paid.rent - (paid.electricity.rate * (paid.electricity.current-paid.electricity.previous))-(paid.water.rate * (paid.water.current-paid.water.previous))
            else {
                paid.rent = paid.place.rent
                paid.balance = paid.place.balance + paid.paid - paid.rent - (paid.electricity.rate * (paid.electricity.current-paid.electricity.previous))-(paid.water.rate * (paid.water.current-paid.water.previous))
                paid.place.balance = paid.balance
                paid.place.previousElectricity = paid.electricity.current
                paid.place.previousWater = paid.water.current
            }
            paidService.save(paid)
        } catch (ValidationException e) {
            respond paid.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'paid.label', default: 'Paid'), paid.id])
                redirect paid
            }
            '*'{ respond paid, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        paidService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'paid.label', default: 'Paid'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'paid.label', default: 'Paid'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
