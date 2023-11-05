package vn.edu.iuh.fit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.pks.ProductPricePK;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPricePK>, JpaSpecificationExecutor<ProductPrice> {
}