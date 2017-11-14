package rent

import grails.gorm.services.Service

@Service(Paid)
interface PaidService {

    Paid get(Serializable id)

    List<Paid> list(Map args)

    Long count()

    void delete(Serializable id)

    Paid save(Paid paid)

}