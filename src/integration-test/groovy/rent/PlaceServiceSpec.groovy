package rent

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PlaceServiceSpec extends Specification {

    PlaceService placeService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Place(...).save(flush: true, failOnError: true)
        //new Place(...).save(flush: true, failOnError: true)
        //Place place = new Place(...).save(flush: true, failOnError: true)
        //new Place(...).save(flush: true, failOnError: true)
        //new Place(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //place.id
    }

    void "test get"() {
        setupData()

        expect:
        placeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Place> placeList = placeService.list(max: 2, offset: 2)

        then:
        placeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        placeService.count() == 5
    }

    void "test delete"() {
        Long placeId = setupData()

        expect:
        placeService.count() == 5

        when:
        placeService.delete(placeId)
        sessionFactory.currentSession.flush()

        then:
        placeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Place place = new Place()
        placeService.save(place)

        then:
        place.id != null
    }
}
