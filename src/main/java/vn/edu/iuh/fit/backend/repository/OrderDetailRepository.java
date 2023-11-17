package vn.edu.iuh.fit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.pks.OrderDetailPK;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK>, JpaSpecificationExecutor<OrderDetail> {

    @Query("select od from  OrderDetail  od where  od.order.order_id = :id")
    List<OrderDetail> findByOrder_Order_id(@Param("id") Long id);
}