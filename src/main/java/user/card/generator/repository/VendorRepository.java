package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.card.generator.domain.vendor.Vendor;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

    public Vendor findByVendorCode(String vendorCode);

    public List<Vendor> findAllByCountryName(String name);


}
