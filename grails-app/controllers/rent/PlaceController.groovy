package rent

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PlaceController {

    PlaceService placeService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond placeService.list(params), model:[placeCount: placeService.count()]
    }

    def show(Long id) {
        respond placeService.get(id)
    }

    def create() {
        respond new Place(params)
    }

    def save(Place place) {
        if (place == null) {
            notFound()
            return
        }

        try {
            placeService.save(place)
        } catch (ValidationException e) {
            respond place.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'place.label', default: 'Place'), place.id])
                redirect place
            }
            '*' { respond place, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond placeService.get(id)
    }

    def update(Place place) {
        if (place == null) {
            notFound()
            return
        }

        try {
            placeService.save(place)
        } catch (ValidationException e) {
            respond place.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'place.label', default: 'Place'), place.id])
                redirect place
            }
            '*'{ respond place, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        placeService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'place.label', default: 'Place'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'place.label', default: 'Place'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
