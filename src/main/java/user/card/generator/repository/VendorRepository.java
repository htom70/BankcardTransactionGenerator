package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import user.card.generator.domain.country.Vendor;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

    public Vendor findByVendorCode(String vendorCode);

    public List<Vendor> findAllByCountryName(String name);


}
