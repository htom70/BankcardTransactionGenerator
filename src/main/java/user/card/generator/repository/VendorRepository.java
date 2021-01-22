package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.vendor.AbstractVendor;
import user.card.generator.domain.vendor.Vendor;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {

    public Vendor findByVendorCode(String vendorCode);

    public List<Vendor> findAllByCity(City city);

    public List<Vendor> findAllByCityIsNot(City city);

//    public List<AbstractVendor> findAllByCountryName(String name);


}
