package myapp

import grails.gorm.annotation.Entity
import grails.gorm.transactions.Transactional
import org.grails.orm.hibernate.HibernateDatastore
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class NullAssociationWhereQuerySpec extends Specification {

	@AutoCleanup
	@Shared
	HibernateDatastore hibernateDatastore

	void setupSpec() {
		hibernateDatastore = new HibernateDatastore(Foo, Bar)
	}

	@Transactional
	void "test query by association is null"() {
		new Foo(bar: new Bar()).save()

		when:
		Long count = Foo.where { bar == null }.count()

		then:
		count == 0

		and:
		Foo.where { bar == null }.count() == 0
	}

}

@Entity
class Foo {

	Bar bar

	static constraints = {
		bar nullable: true
	}

}

@Entity
class Bar {

	static belongsTo = [foo: Foo]

	static constraints = {
		foo nullable: true
	}

}