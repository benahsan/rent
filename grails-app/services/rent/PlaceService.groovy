package rent

import grails.gorm.services.Service

@Service(Place)
interface PlaceService {

    Place get(Serializable id)

    List<Place> list(Map args)

    Long count()

    void delete(Serializable id)

    Place save(Place place)

}