package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.VendorRepository;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    public void saveAllVendors(List<Vendor> vendors) {
        vendorRepository.saveAll(vendors);
    }

    public void generateVendorsInHungary() {

    }
}
