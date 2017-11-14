package rent

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PaidServiceSpec extends Specification {

    PaidService paidService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Paid(...).save(flush: true, failOnError: true)
        //new Paid(...).save(flush: true, failOnError: true)
        //Paid paid = new Paid(...).save(flush: true, failOnError: true)
        //new Paid(...).save(flush: true, failOnError: true)
        //new Paid(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //paid.id
    }

    void "test get"() {
        setupData()

        expect:
        paidService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Paid> paidList = paidService.list(max: 2, offset: 2)

        then:
        paidList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        paidService.count() == 5
    }

    void "test delete"() {
        Long paidId = setupData()

        expect:
        paidService.count() == 5

        when:
        paidService.delete(paidId)
        sessionFactory.currentSession.flush()

        then:
        paidService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Paid paid = new Paid()
        paidService.save(paid)

        then:
        paid.id != null
    }
}
